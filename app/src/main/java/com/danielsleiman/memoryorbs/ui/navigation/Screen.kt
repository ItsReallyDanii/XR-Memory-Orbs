package com.danielsleiman.memoryorbs.ui.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    object OrbList : Screen("orb_list")
    object Recall : Screen("orb_recall")
    object OrbDetail : Screen("orb_detail/{orbId}") {
        fun createRoute(orbId: Int) = "orb_detail/$orbId"
    }
    object OrbEdit : Screen("orb_edit/{orbId}") {
        fun createRoute(orbId: Int? = null) = "orb_edit/${orbId ?: -1}"
    }

    object CameraEntry : Screen("camera_entry")

    object OrbEditWithPhoto : Screen("orb_edit_photo/{imageUri}") {
        fun createRoute(imageUri: String) = "orb_edit_photo/${Uri.encode(imageUri)}"
    }
}
