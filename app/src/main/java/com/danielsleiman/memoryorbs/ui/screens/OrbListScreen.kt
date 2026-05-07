package com.danielsleiman.memoryorbs.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.danielsleiman.memoryorbs.GlassesProjectionActivity
import com.danielsleiman.memoryorbs.data.model.MemoryOrb
import com.danielsleiman.memoryorbs.ui.viewmodel.MemoryOrbViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrbListScreen(
    viewModel: MemoryOrbViewModel,
    onOrbClick: (Int) -> Unit,
    onRecallClick: () -> Unit,
    onAddOrbClick: () -> Unit
) {
    val orbs by viewModel.allOrbs.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Memory Orbs") },
                actions = {
                    TextButton(
                        onClick = {
                            GlassesProjectionActivity.launchProjected(
                                context = context,
                                orbId = orbs.firstOrNull()?.id
                            )
                        }
                    ) {
                        Text("XR")
                    }
                    IconButton(onClick = onRecallClick) {
                        Icon(Icons.Default.Search, contentDescription = "Recall")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddOrbClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Orb")
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(orbs) { orb ->
                OrbItem(orb = orb, onClick = { onOrbClick(orb.id) })
            }
        }
    }
}

@Composable
fun OrbItem(orb: MemoryOrb, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = orb.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = orb.bodyText,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
