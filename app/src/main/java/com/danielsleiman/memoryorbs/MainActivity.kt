package com.danielsleiman.memoryorbs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.danielsleiman.memoryorbs.ui.navigation.OrbNavGraph
import com.danielsleiman.memoryorbs.ui.theme.MemoryOrbsTheme
import com.danielsleiman.memoryorbs.ui.viewmodel.MemoryOrbViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoryOrbsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel: MemoryOrbViewModel = viewModel()
                    OrbNavGraph(navController = navController, viewModel = viewModel)
                }
            }
        }
    }
}
