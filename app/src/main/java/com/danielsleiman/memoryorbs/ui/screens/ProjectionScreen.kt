package com.danielsleiman.memoryorbs.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.danielsleiman.memoryorbs.data.model.MemoryOrb

@Composable
fun ProjectionScreen(
    orb: MemoryOrb?,
    isLoaded: Boolean
) {
    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                !isLoaded -> CircularProgressIndicator()
                orb == null -> Text(
                    text = "No Memory Orb found for this projection request.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                else -> ProjectionCard(orb = orb)
            }
        }
    }
}

@Composable
private fun ProjectionCard(orb: MemoryOrb) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = orb.title,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = orb.bodyText,
                style = MaterialTheme.typography.bodyLarge
            )
            if (!orb.contextLabel.isNullOrBlank()) {
                Text(
                    text = "Context: ${orb.contextLabel}",
                    style = MaterialTheme.typography.labelLarge
                )
            }
            if (!orb.recallHint.isNullOrBlank()) {
                Text(
                    text = "Recall Hint: ${orb.recallHint}",
                    style = MaterialTheme.typography.labelLarge
                )
            }
            if (orb.tags.isNotBlank()) {
                Text(
                    text = "Tags: ${orb.tags}",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
