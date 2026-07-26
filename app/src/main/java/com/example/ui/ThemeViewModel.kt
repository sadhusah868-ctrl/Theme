package com.example.ui

import android.app.Application
import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.ThemePreset
import com.example.data.ThemeRepository
import com.example.model.ControlCenterState
import com.example.model.DynamicIslandMode
import com.example.model.IosIcon
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ThemeRepository

    val allThemes: StateFlow<List<ThemePreset>>
    val activeTheme: StateFlow<ThemePreset?>

    private val _dynamicIslandMode = MutableStateFlow<DynamicIslandMode>(DynamicIslandMode.Music())
    val dynamicIslandMode: StateFlow<DynamicIslandMode> = _dynamicIslandMode.asStateFlow()

    private val _controlCenterState = MutableStateFlow(ControlCenterState())
    val controlCenterState: StateFlow<ControlCenterState> = _controlCenterState.asStateFlow()

    private val _iconRadiusDp = MutableStateFlow(18)
    val iconRadiusDp: StateFlow<Int> = _iconRadiusDp.asStateFlow()

    private val _iconList = MutableStateFlow<List<IosIcon>>(emptyList())
    val iconList: StateFlow<List<IosIcon>> = _iconList.asStateFlow()

    private val _isApplyingTheme = MutableStateFlow(false)
    val isApplyingTheme: StateFlow<Boolean> = _isApplyingTheme.asStateFlow()

    private val _showPocoGuideDialog = MutableStateFlow(false)
    val showPocoGuideDialog: StateFlow<Boolean> = _showPocoGuideDialog.asStateFlow()

    private val _previewMode = MutableStateFlow("HOME") // "HOME" or "LOCK"
    val previewMode: StateFlow<String> = _previewMode.asStateFlow()

    private val _showControlCenterOverlay = MutableStateFlow(false)
    val showControlCenterOverlay: StateFlow<Boolean> = _showControlCenterOverlay.asStateFlow()

    init {
        val themeDao = AppDatabase.getDatabase(application).themeDao()
        repository = ThemeRepository(themeDao)

        val tempThemes = MutableStateFlow<List<ThemePreset>>(emptyList())
        allThemes = tempThemes.asStateFlow()

        val tempActive = MutableStateFlow<ThemePreset?>(null)
        activeTheme = tempActive.asStateFlow()

        viewModelScope.launch {
            repository.insertDefaultThemesIfEmpty()
            
            launch {
                repository.allThemes.collectLatest { list ->
                    tempThemes.value = list
                }
            }
            launch {
                repository.activeTheme.collectLatest { active ->
                    tempActive.value = active
                    active?.let {
                        _iconRadiusDp.value = it.iconShapeRadiusDp
                    }
                }
            }
        }

        setupDefaultIcons()
    }

    private fun setupDefaultIcons() {
        _iconList.value = listOf(
            IosIcon("1", "Phone", "phone", Color(0xFF34C759), Color(0xFF30D158), badgeCount = 1),
            IosIcon("2", "Messages", "message", Color(0xFF34C759), Color(0xFF24B046), badgeCount = 3),
            IosIcon("3", "Safari", "explore", Color(0xFF007AFF), Color(0xFF58A6FF)),
            IosIcon("4", "Music", "music_note", Color(0xFFFA2D48), Color(0xFFFF5268), badgeCount = 0),
            IosIcon("5", "Photos", "photo_library", Color(0xFFFF9500), Color(0xFFFFCC00)),
            IosIcon("6", "Camera", "photo_camera", Color(0xFF8E8E93), Color(0xFF636366)),
            IosIcon("7", "Settings", "settings", Color(0xFF8E8E93), Color(0xFF3A3A3C)),
            IosIcon("8", "Clock", "access_time", Color(0xFF1C1C1E), Color(0xFF2C2C2E)),
            IosIcon("9", "Weather", "wb_sunny", Color(0xFF59ADFF), Color(0xFF007AFF)),
            IosIcon("10", "Notes", "edit_note", Color(0xFFFFCC02), Color(0xFFE5B800)),
            IosIcon("11", "Files", "folder", Color(0xFF007AFF), Color(0xFF34AADC)),
            IosIcon("12", "Poco Launcher", "rocket_launch", Color(0xFFFF3B30), Color(0xFFFF6961)),
            IosIcon("13", "Security", "security", Color(0xFF30D158), Color(0xFF1B8735)),
            IosIcon("14", "Calculator", "calculate", Color(0xFFFF9500), Color(0xFFFFB340)),
            IosIcon("15", "Health", "favorite", Color(0xFFFF2D55), Color(0xFFFF6980)),
            IosIcon("16", "Fitness", "directions_run", Color(0xFF30D158), Color(0xFF00E676)),
            IosIcon("17", "YouTube", "play_circle_filled", Color(0xFFFF0000), Color(0xFFD32F2F)),
            IosIcon("18", "WhatsApp", "chat", Color(0xFF25D366), Color(0xFF128C7E), badgeCount = 5),
            IosIcon("19", "Instagram", "camera_alt", Color(0xFFE1306C), Color(0xFFF77737)),
            IosIcon("20", "Themes", "palette", Color(0xFFAF52DE), Color(0xFF5856D6))
        )
    }

    fun applyTheme(themeId: String) {
        viewModelScope.launch {
            _isApplyingTheme.value = true
            triggerVibration()
            delay(1200) // Realistic liquid glass dynamic apply transition
            repository.applyTheme(themeId)
            _isApplyingTheme.value = false
            
            Toast.makeText(
                getApplication(),
                "iOS 27 Theme successfully applied to Poco!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun setPreviewMode(mode: String) {
        _previewMode.value = mode
        triggerVibration()
    }

    fun setDynamicIslandMode(mode: DynamicIslandMode) {
        _dynamicIslandMode.value = mode
        triggerVibration()
    }

    fun toggleDynamicIslandEnabled(enabled: Boolean) {
        viewModelScope.launch {
            repository.setDynamicIslandEnabled(enabled)
            triggerVibration()
        }
    }

    fun setIconShapeRadius(radiusDp: Int) {
        _iconRadiusDp.value = radiusDp
        viewModelScope.launch {
            repository.setIconShapeRadius(radiusDp)
        }
    }

    fun toggleWifi() {
        val current = _controlCenterState.value
        _controlCenterState.value = current.copy(wifiEnabled = !current.wifiEnabled)
        triggerVibration()
    }

    fun toggleBluetooth() {
        val current = _controlCenterState.value
        _controlCenterState.value = current.copy(bluetoothEnabled = !current.bluetoothEnabled)
        triggerVibration()
    }

    fun toggleFlashlight() {
        val current = _controlCenterState.value
        val newState = !current.flashlightOn
        _controlCenterState.value = current.copy(flashlightOn = newState)
        triggerVibration()

        try {
            val cameraManager = getApplication<Application>().getSystemService(Context.CAMERA_SERVICE) as? CameraManager
            val cameraId = cameraManager?.cameraIdList?.firstOrNull()
            if (cameraId != null) {
                cameraManager.setTorchMode(cameraId, newState)
            }
        } catch (e: Exception) {
            // Flashlight hardware fallback for emulator/devices without torch
        }
    }

    fun toggleDarkMode() {
        val current = _controlCenterState.value
        _controlCenterState.value = current.copy(darkModeOn = !current.darkModeOn)
        triggerVibration()
    }

    fun toggleLowPowerMode() {
        val current = _controlCenterState.value
        _controlCenterState.value = current.copy(lowPowerMode = !current.lowPowerMode)
        triggerVibration()
    }

    fun toggleFocusMode() {
        val current = _controlCenterState.value
        _controlCenterState.value = current.copy(focusModeOn = !current.focusModeOn)
        triggerVibration()
    }

    fun updateVolume(vol: Float) {
        _controlCenterState.value = _controlCenterState.value.copy(volume = vol)
    }

    fun updateBrightness(bri: Float) {
        _controlCenterState.value = _controlCenterState.value.copy(brightness = bri)
    }

    fun toggleMusicPlayback() {
        val current = _controlCenterState.value
        val playing = !current.isPlayingMusic
        _controlCenterState.value = current.copy(isPlayingMusic = playing)
        
        // Also update Dynamic Island if in music mode
        val currentIsland = _dynamicIslandMode.value
        if (currentIsland is DynamicIslandMode.Music) {
            _dynamicIslandMode.value = currentIsland.copy(isPlaying = playing)
        }
        triggerVibration()
    }

    fun toggleControlCenterOverlay(show: Boolean) {
        _showControlCenterOverlay.value = show
        triggerVibration()
    }

    fun setPocoGuideDialog(show: Boolean) {
        _showPocoGuideDialog.value = show
        triggerVibration()
    }

    private fun triggerVibration() {
        try {
            val vibrator = getApplication<Application>().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(25, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(25)
            }
        } catch (e: Exception) {
            // Ignore if vibration not available
        }
    }
}
