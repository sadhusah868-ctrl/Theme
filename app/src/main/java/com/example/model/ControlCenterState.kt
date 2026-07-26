package com.example.model

data class ControlCenterState(
    val wifiEnabled: Boolean = true,
    val wifiSSID: String = "Poco_5G_Home",
    val bluetoothEnabled: Boolean = true,
    val bluetoothDevice: String = "AirPods Pro 2",
    val cellularEnabled: Boolean = true,
    val airplaneMode: Boolean = false,
    val flashlightOn: Boolean = false,
    val focusModeOn: Boolean = false,
    val focusName: String = "Work Focus",
    val darkModeOn: Boolean = true,
    val lowPowerMode: Boolean = false,
    val autoRotate: Boolean = true,
    val screenRecording: Boolean = false,
    val volume: Float = 0.75f,
    val brightness: Float = 0.82f,
    val currentSong: String = "Feather (iOS 27 Spatial Mix)",
    val currentArtist: String = "Sabrina Carpenter",
    val isPlayingMusic: Boolean = true
)
