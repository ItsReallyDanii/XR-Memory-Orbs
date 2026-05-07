package com.danielsleiman.memoryorbs.ui.navigation

sealed class Screen(val route: String) {
    object OrbList : Screen("orb_list")
    object Recall : Screen("orb_recall")
    object OrbDetail : Screen("orb_detail/{orbId}") {
        fun createRoute(orbId: Int) = "orb_detail/$orbId"
    }
    object OrbEdit : Screen("orb_edit/{orbId}") {
        fun createRoute(orbId: Int? = null) = "orb_edit/${orbId ?: -1}"
    }
}
