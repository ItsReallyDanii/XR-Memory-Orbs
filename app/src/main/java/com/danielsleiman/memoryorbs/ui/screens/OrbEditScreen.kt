package com.danielsleiman.memoryorbs.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielsleiman.memoryorbs.data.model.MemoryOrb
import com.danielsleiman.memoryorbs.ui.viewmodel.MemoryOrbViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrbEditScreen(
    orbId: Int?,
    viewModel: MemoryOrbViewModel,
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var bodyText by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }
    var contextLabel by remember { mutableStateOf("") }
    var recallHint by remember { mutableStateOf("") }
    
    var existingOrb by remember { mutableStateOf<MemoryOrb?>(null) }

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
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (orbId == null) "New Memory Orb" else "Edit Orb") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
                                updatedAt = System.currentTimeMillis()
                            ) ?: MemoryOrb(
                                title = title,
                                bodyText = bodyText,
                                tags = tags,
                                contextLabel = contextLabel.takeIf { it.isNotBlank() },
                                recallHint = recallHint.takeIf { it.isNotBlank() },
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
