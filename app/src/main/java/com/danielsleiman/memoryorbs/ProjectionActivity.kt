package com.danielsleiman.memoryorbs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.danielsleiman.memoryorbs.data.local.AppDatabase
import com.danielsleiman.memoryorbs.data.model.MemoryOrb
import com.danielsleiman.memoryorbs.data.repository.MemoryOrbRepository
import com.danielsleiman.memoryorbs.ui.screens.ProjectionScreen
import com.danielsleiman.memoryorbs.ui.theme.MemoryOrbsTheme
import kotlinx.coroutines.launch

class ProjectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = MemoryOrbRepository(AppDatabase.getDatabase(applicationContext).memoryOrbDao())
        val orbId = intent.getIntExtra(EXTRA_ORB_ID, INVALID_ORB_ID)
        val contextLabel = intent.getStringExtra(EXTRA_CONTEXT_LABEL)?.trim().orEmpty()

        var orb by mutableStateOf<MemoryOrb?>(null)
        var isLoaded by mutableStateOf(false)

        setContent {
            MemoryOrbsTheme {
                ProjectionScreen(
                    orb = orb,
                    isLoaded = isLoaded
                )
            }
        }

        lifecycleScope.launch {
            orb = when {
                orbId != INVALID_ORB_ID -> repository.getOrbById(orbId)
                contextLabel.isNotEmpty() -> repository.getFirstOrbByContextLabel(contextLabel)
                else -> null
            }
            isLoaded = true
        }
    }

    companion object {
        const val EXTRA_ORB_ID = "orbId"
        const val EXTRA_CONTEXT_LABEL = "contextLabel"
        private const val INVALID_ORB_ID = -1
    }
}
