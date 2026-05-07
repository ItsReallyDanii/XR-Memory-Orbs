package com.danielsleiman.memoryorbs.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danielsleiman.memoryorbs.data.model.MemoryOrb
import com.danielsleiman.memoryorbs.ui.viewmodel.MemoryOrbViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrbDetailScreen(
    orbId: Int,
    viewModel: MemoryOrbViewModel,
    onBack: () -> Unit,
    onEdit: (Int) -> Unit,
    onDelete: () -> Unit
) {
    var orb by remember { mutableStateOf<MemoryOrb?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(orbId) {
        orb = viewModel.getOrbById(orbId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Orb Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    orb?.let { currentOrb ->
                        IconButton(onClick = { onEdit(currentOrb.id) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = {
                            viewModel.deleteOrb(currentOrb)
                            onDelete()
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            )
        }
    ) { padding ->
        orb?.let { currentOrb ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(text = currentOrb.title, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = currentOrb.bodyText, style = MaterialTheme.typography.bodyLarge)
                
                if (currentOrb.tags.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Tags: ${currentOrb.tags}", style = MaterialTheme.typography.labelLarge)
                }

                currentOrb.contextLabel?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Context: $it", style = MaterialTheme.typography.labelMedium)
                }

                currentOrb.recallHint?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Recall Hint: $it", style = MaterialTheme.typography.labelMedium)
                }
            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}
