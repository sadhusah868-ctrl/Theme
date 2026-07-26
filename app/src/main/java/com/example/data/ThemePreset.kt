package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "theme_presets")
data class ThemePreset(
    @PrimaryKey val id: String,
    val name: String,
    val tag: String,
    val description: String,
    val isApplied: Boolean = false,
    val wallpaperResName: String,
    val clockStyle: String = "iOS27_Condensed", // iOS27_Condensed, Futuristic_Neon, Classic_Thin, Liquid_Script
    val clockColorHex: String = "#FFFFFF",
    val iconShapeRadiusDp: Int = 18,
    val dynamicIslandEnabled: Boolean = true,
    val controlCenterStyle: String = "LiquidGlass", // LiquidGlass, DarkAero, CyberGlow
    val pocoOptimized: Boolean = true,
    val accentColorHex: String = "#007AFF"
)
