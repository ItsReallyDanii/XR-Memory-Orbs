package com.danielsleiman.memoryorbs.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.danielsleiman.memoryorbs.data.model.MemoryOrb
import com.danielsleiman.memoryorbs.ui.viewmodel.MemoryOrbViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrbEditScreen(
    orbId: Int?,
    viewModel: MemoryOrbViewModel,
    onBack: () -> Unit,
    onSave: () -> Unit,
    prefillMediaUri: String? = null
) {
    var title by remember { mutableStateOf("") }
    var bodyText by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }
    var contextLabel by remember { mutableStateOf("") }
    var recallHint by remember { mutableStateOf("") }
    
    var existingOrb by remember { mutableStateOf<MemoryOrb?>(null) }
    var capturedImageUri by remember { mutableStateOf(prefillMediaUri) }

    LaunchedEffect(orbId) {
        if (orbId != null) {
            val orb = viewModel.getOrbById(orbId)
            if (orb != null) {
                existingOrb = orb
                title = orb.title
                bodyText = orb.bodyText
                tags = orb.tags
                contextLabel = orb.contextLabel ?: ""
                recallHint = orb.recallHint ?: ""
                capturedImageUri = orb.mediaUri ?: prefillMediaUri
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (orbId == null) "New Memory Orb" else "Edit Orb") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val orb = existingOrb?.copy(
                                title = title,
                                bodyText = bodyText,
                                tags = tags,
                                contextLabel = contextLabel.takeIf { it.isNotBlank() },
                                recallHint = recallHint.takeIf { it.isNotBlank() },
                                mediaUri = capturedImageUri,
                                updatedAt = System.currentTimeMillis()
                            ) ?: MemoryOrb(
                                title = title,
                                bodyText = bodyText,
                                tags = tags,
                                contextLabel = contextLabel.takeIf { it.isNotBlank() },
                                recallHint = recallHint.takeIf { it.isNotBlank() },
                                mediaUri = capturedImageUri,
                            )
                            
                            if (orbId == null) {
                                viewModel.insertOrb(orb)
                            } else {
                                viewModel.updateOrb(orb)
                            }
                            onSave()
                        },
                        enabled = title.isNotBlank() && bodyText.isNotBlank()
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            // Simple photo thumbnail preview when a mediaUri is present (from camera flow or existing orb)
            val thumbnail = rememberThumbnailBitmap(capturedImageUri)
            if (thumbnail != null) {
                Image(
                    bitmap = thumbnail.asImageBitmap(),
                    contentDescription = "Captured photo preview",
                    modifier = Modifier
                        .size(180.dp)
                        .padding(bottom = 16.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = bodyText,
                onValueChange = { bodyText = it },
                label = { Text("Body Text") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = tags,
                onValueChange = { tags = it },
                label = { Text("Tags (comma separated)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = contextLabel,
                onValueChange = { contextLabel = it },
                label = { Text("Context Label (Optional)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = recallHint,
                onValueChange = { recallHint = it },
                label = { Text("Recall Hint (Optional)") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// Downsampled thumbnail loading (respects user's requirement for inSampleSize / setTargetSize, ~200px target)
@Composable
private fun rememberThumbnailBitmap(uriString: String?): Bitmap? {
    val context = LocalContext.current
    var bitmap by remember(uriString) { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(uriString) {
        bitmap = if (uriString != null) {
            loadDownsampledBitmap(context, Uri.parse(uriString))
        } else {
            null
        }
    }
    return bitmap
}

private fun loadDownsampledBitmap(context: Context, uri: Uri): Bitmap? {
    return try {
        val contentResolver = context.contentResolver
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(contentResolver, uri)
            ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                // Target approximately 200px on the long edge for preview
                decoder.setTargetSize(200, 200)
            }
        } else {
            // BitmapFactory + inSampleSize path for API 26–27 (minSdk = 26)
            contentResolver.openInputStream(uri)?.use { inputStream ->
                val options = BitmapFactory.Options().apply {
                    inJustDecodeBounds = true
                }
                BitmapFactory.decodeStream(inputStream, null, options)

                options.inSampleSize = calculateInSampleSize(options, 200, 200)
                options.inJustDecodeBounds = false

                // Re-open stream (previous one was consumed by bounds pass)
                contentResolver.openInputStream(uri)?.use { inputStream2 ->
                    BitmapFactory.decodeStream(inputStream2, null, options)
                }
            }
        }
    } catch (_: Exception) {
        null
    }
}

private fun calculateInSampleSize(
    options: BitmapFactory.Options,
    reqWidth: Int,
    reqHeight: Int
): Int {
    val (height: Int, width: Int) = options.outHeight to options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val halfHeight = height / 2
        val halfWidth = width / 2

        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}
