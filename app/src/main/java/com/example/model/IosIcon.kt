package com.example.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class IosIcon(
    val id: String,
    val name: String,
    val systemIconName: String, // Material icon mapping
    val primaryColor: Color,
    val secondaryColor: Color,
    val badgeCount: Int = 0,
    val isSystemApp: Boolean = true,
    val customLabel: String = name
)
