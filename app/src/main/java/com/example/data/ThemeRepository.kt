package com.example.data

import kotlinx.coroutines.flow.Flow

class ThemeRepository(private val themeDao: ThemeDao) {
    val allThemes: Flow<List<ThemePreset>> = themeDao.getAllThemes()
    val activeTheme: Flow<ThemePreset?> = themeDao.getActiveTheme()

    suspend fun applyTheme(themeId: String) {
        themeDao.applyTheme(themeId)
    }

    suspend fun insertDefaultThemesIfEmpty() {
        val defaultThemes = listOf(
            ThemePreset(
                id = "liquid_glass_pro",
                name = "iOS 27 Liquid Glass Pro",
                tag = "MOST POPULAR",
                description = "Translucent frosted glass UI with fluid depth colors and dynamic glass reflection.",
                isApplied = true,
                wallpaperResName = "img_wallpaper_liquid_1785052478835",
                clockStyle = "iOS27_Condensed",
                clockColorHex = "#FFFFFF",
                iconShapeRadiusDp = 18,
                dynamicIslandEnabled = true,
                controlCenterStyle = "LiquidGlass",
                pocoOptimized = true,
                accentColorHex = "#007AFF"
            ),
            ThemePreset(
                id = "depth_aurora",
                name = "iOS 27 Spatial Depth Aurora",
                tag = "DEPTH LOCKSCREEN",
                description = "Dynamic glowing aurora wallpaper with multi-layer spatial depth clock effect.",
                isApplied = false,
                wallpaperResName = "img_wallpaper_aurora_1785052494318",
                clockStyle = "Futuristic_Neon",
                clockColorHex = "#34C759",
                iconShapeRadiusDp = 20,
                dynamicIslandEnabled = true,
                controlCenterStyle = "DarkAero",
                pocoOptimized = true,
                accentColorHex = "#30D158"
            ),
            ThemePreset(
                id = "cyber_neon",
                name = "iOS 27 Cyber Neon Dark",
                tag = "OLED SPECIAL",
                description = "Futuristic dark cyber OLED aesthetic with neon glowing dynamic island and glass sliders.",
                isApplied = false,
                wallpaperResName = "img_wallpaper_cyber_1785052507521",
                clockStyle = "iOS27_Condensed",
                clockColorHex = "#BF5AF2",
                iconShapeRadiusDp = 16,
                dynamicIslandEnabled = true,
                controlCenterStyle = "CyberGlow",
                pocoOptimized = true,
                accentColorHex = "#AF52DE"
            ),
            ThemePreset(
                id = "poco_hyperos_hybrid",
                name = "Poco x iOS 27 HyperOS Edition",
                tag = "POCO OPTIMIZED",
                description = "Tailor-made for Poco devices with HyperOS icon theme integration and high FPS animations.",
                isApplied = false,
                wallpaperResName = "img_wallpaper_liquid_1785052478835",
                clockStyle = "Classic_Thin",
                clockColorHex = "#FF9500",
                iconShapeRadiusDp = 18,
                dynamicIslandEnabled = true,
                controlCenterStyle = "LiquidGlass",
                pocoOptimized = true,
                accentColorHex = "#FF9500"
            )
        )
        themeDao.insertThemes(defaultThemes)
    }

    suspend fun setDynamicIslandEnabled(enabled: Boolean) {
        themeDao.setDynamicIslandEnabled(enabled)
    }

    suspend fun setIconShapeRadius(radius: Int) {
        themeDao.setIconShapeRadius(radius)
    }

    suspend fun updateTheme(theme: ThemePreset) {
        themeDao.insertTheme(theme)
    }
}
