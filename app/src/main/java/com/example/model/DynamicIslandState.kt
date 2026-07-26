package com.example.model

sealed class DynamicIslandMode {
    object Idle : DynamicIslandMode()
    data class Music(
        val trackName: String = "Starboy (iOS 27 Spatial)",
        val artist: String = "The Weeknd",
        val isPlaying: Boolean = true,
        val progress: Float = 0.42f
    ) : DynamicIslandMode()

    data class Timer(
        val remainingSeconds: Int = 185,
        val title: String = "Focus Timer"
    ) : DynamicIslandMode()

    data class Charging(
        val percentage: Int = 88,
        val isFastCharging: Boolean = true
    ) : DynamicIslandMode()

    data class Navigation(
        val distance: String = "300m",
        val instruction: String = "Turn Right on Tech Ave",
        val iconName: String = "turn_right"
    ) : DynamicIslandMode()

    data class Call(
        val callerName: String = "Poco Support",
        val callDuration: String = "00:12"
    ) : DynamicIslandMode()
}
