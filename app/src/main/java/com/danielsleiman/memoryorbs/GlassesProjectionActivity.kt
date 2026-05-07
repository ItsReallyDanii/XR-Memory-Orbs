package com.danielsleiman.memoryorbs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import androidx.xr.projected.ProjectedContext
import androidx.xr.projected.experimental.ExperimentalProjectedApi
import com.danielsleiman.memoryorbs.data.local.AppDatabase
import com.danielsleiman.memoryorbs.data.model.MemoryOrb
import com.danielsleiman.memoryorbs.data.repository.MemoryOrbRepository
import com.danielsleiman.memoryorbs.ui.screens.ProjectionScreen
import com.danielsleiman.memoryorbs.ui.theme.MemoryOrbsTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalProjectedApi::class)
class GlassesProjectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = MemoryOrbRepository(AppDatabase.getDatabase(this).memoryOrbDao())
        val orbId = intent.getIntExtra(ProjectionActivity.EXTRA_ORB_ID, INVALID_ORB_ID)
        val contextLabel = intent.getStringExtra(ProjectionActivity.EXTRA_CONTEXT_LABEL)?.trim().orEmpty()

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
        private const val INVALID_ORB_ID = -1

        fun launchProjected(
            context: Context,
            orbId: Int? = null,
            contextLabel: String? = null
        ) {
            val intent = Intent(context, GlassesProjectionActivity::class.java).apply {
                orbId?.let { putExtra(ProjectionActivity.EXTRA_ORB_ID, it) }
                contextLabel?.let { putExtra(ProjectionActivity.EXTRA_CONTEXT_LABEL, it) }
            }
            val options = ProjectedContext.createProjectedActivityOptions(context)
            context.startActivity(intent, options.toBundle())
        }
    }
}
