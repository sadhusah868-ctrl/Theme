package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ControlCamera
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.RoundedCorner
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ThemePreset
import com.example.model.DynamicIslandMode
import com.example.ui.ThemeViewModel
import com.example.ui.components.ControlCenterSheet
import com.example.ui.components.HomeScreenView
import com.example.ui.components.IconStudioView
import com.example.ui.components.LockscreenView
import com.example.ui.components.PocoGuideDialog

@Composable
fun MainScreen(
    viewModel: ThemeViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val allThemes by viewModel.allThemes.collectAsState()
    val activeTheme by viewModel.activeTheme.collectAsState()
    val dynamicIslandMode by viewModel.dynamicIslandMode.collectAsState()
    val controlCenterState by viewModel.controlCenterState.collectAsState()
    val iconRadiusDp by viewModel.iconRadiusDp.collectAsState()
    val iconList by viewModel.iconList.collectAsState()
    val isApplyingTheme by viewModel.isApplyingTheme.collectAsState()
    val showPocoGuide by viewModel.showPocoGuideDialog.collectAsState()
    val previewMode by viewModel.previewMode.collectAsState()
    val showControlCenterOverlay by viewModel.showControlCenterOverlay.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF0F1322),
                contentColor = Color.White,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Palette, contentDescription = "Theme Hub") },
                    label = { Text("Theme Hub", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        indicatorColor = Color(0xFF007AFF),
                        unselectedIconColor = Color.White.copy(alpha = 0.5f),
                        unselectedTextColor = Color.White.copy(alpha = 0.5f)
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Lock, contentDescription = "Lockscreen") },
                    label = { Text("Lockscreen", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        indicatorColor = Color(0xFF007AFF),
                        unselectedIconColor = Color.White.copy(alpha = 0.5f),
                        unselectedTextColor = Color.White.copy(alpha = 0.5f)
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.ControlCamera, contentDescription = "Control Center") },
                    label = { Text("Control Center", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        indicatorColor = Color(0xFF007AFF),
                        unselectedIconColor = Color.White.copy(alpha = 0.5f),
                        unselectedTextColor = Color.White.copy(alpha = 0.5f)
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = { Icon(Icons.Default.RoundedCorner, contentDescription = "Icon Studio") },
                    label = { Text("Icon Studio", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        indicatorColor = Color(0xFF007AFF),
                        unselectedIconColor = Color.White.copy(alpha = 0.5f),
                        unselectedTextColor = Color.White.copy(alpha = 0.5f)
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 4,
                    onClick = { selectedTab = 4 },
                    icon = { Icon(Icons.Default.PhoneAndroid, contentDescription = "Poco Setup") },
                    label = { Text("Poco Guide", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        indicatorColor = Color(0xFFFFC800),
                        unselectedIconColor = Color.White.copy(alpha = 0.5f),
                        unselectedTextColor = Color.White.copy(alpha = 0.5f)
                    )
                )
            }
        },
        containerColor = Color(0xFF090B13)
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (selectedTab) {
                0 -> ThemeHubScreen(
                    allThemes = allThemes,
                    activeTheme = activeTheme,
                    previewMode = previewMode,
                    dynamicIslandMode = dynamicIslandMode,
                    iconRadiusDp = iconRadiusDp,
                    iconList = iconList,
                    isApplyingTheme = isApplyingTheme,
                    onSelectPreviewMode = { viewModel.setPreviewMode(it) },
                    onSelectDynamicIslandMode = { viewModel.setDynamicIslandMode(it) },
                    onApplyTheme = { viewModel.applyTheme(it) },
                    onOpenControlCenter = { viewModel.toggleControlCenterOverlay(true) },
                    onOpenPocoGuide = { viewModel.setPocoGuideDialog(true) }
                )

                1 -> LockscreenTabScreen(
                    activeTheme = activeTheme,
                    onToggleFlashlight = { viewModel.toggleFlashlight() }
                )

                2 -> ControlCenterTabScreen(
                    state = controlCenterState,
                    onToggleWifi = { viewModel.toggleWifi() },
                    onToggleBluetooth = { viewModel.toggleBluetooth() },
                    onToggleFlashlight = { viewModel.toggleFlashlight() },
                    onToggleDarkMode = { viewModel.toggleDarkMode() },
                    onToggleLowPower = { viewModel.toggleLowPowerMode() },
                    onToggleFocus = { viewModel.toggleFocusMode() },
                    onToggleMusic = { viewModel.toggleMusicPlayback() },
                    onVolumeChange = { viewModel.updateVolume(it) },
                    onBrightnessChange = { viewModel.updateBrightness(it) },
                    onLaunchOverlay = { viewModel.toggleControlCenterOverlay(true) }
                )

                3 -> IconStudioView(
                    iconRadiusDp = iconRadiusDp,
                    iconList = iconList,
                    onRadiusChange = { viewModel.setIconShapeRadius(it) },
                    onExportIconPack = {
                        Toast.makeText(context, "iOS 27 Squircle Icon Pack exported for Poco Launcher!", Toast.LENGTH_LONG).show()
                    }
                )

                4 -> PocoGuideTabScreen(onOpenGuide = { viewModel.setPocoGuideDialog(true) })
            }

            // Floating Control Center Overlay Modal
            AnimatedVisibility(
                visible = showControlCenterOverlay,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                ControlCenterSheet(
                    state = controlCenterState,
                    onToggleWifi = { viewModel.toggleWifi() },
                    onToggleBluetooth = { viewModel.toggleBluetooth() },
                    onToggleFlashlight = { viewModel.toggleFlashlight() },
                    onToggleDarkMode = { viewModel.toggleDarkMode() },
                    onToggleLowPower = { viewModel.toggleLowPowerMode() },
                    onToggleFocus = { viewModel.toggleFocusMode() },
                    onToggleMusic = { viewModel.toggleMusicPlayback() },
                    onVolumeChange = { viewModel.updateVolume(it) },
                    onBrightnessChange = { viewModel.updateBrightness(it) },
                    onClose = { viewModel.toggleControlCenterOverlay(false) }
                )
            }

            // Poco Setup Guide Modal
            AnimatedVisibility(
                visible = showPocoGuide,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                PocoGuideDialog(
                    onDismiss = { viewModel.setPocoGuideDialog(false) }
                )
            }
        }
    }
}

@Composable
fun ThemeHubScreen(
    allThemes: List<ThemePreset>,
    activeTheme: ThemePreset?,
    previewMode: String,
    dynamicIslandMode: DynamicIslandMode,
    iconRadiusDp: Int,
    iconList: List<com.example.model.IosIcon>,
    isApplyingTheme: Boolean,
    onSelectPreviewMode: (String) -> Unit,
    onSelectDynamicIslandMode: (DynamicIslandMode) -> Unit,
    onApplyTheme: (String) -> Unit,
    onOpenControlCenter: () -> Unit,
    onOpenPocoGuide: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // App Top Bar
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF007AFF)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text("iOS 27 Theme", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("Poco Edition 2026", color = Color(0xFFFFC800), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            Row {
                IconButton(
                    onClick = onOpenControlCenter,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.15f))
                ) {
                    Icon(Icons.Default.ControlCamera, contentDescription = "Control Center", tint = Color.White, modifier = Modifier.size(18.dp))
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = onOpenPocoGuide,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFC800))
                ) {
                    Icon(Icons.Default.HelpOutline, contentDescription = "Poco Setup Guide", tint = Color.Black, modifier = Modifier.size(18.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Live Phone Canvas Container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
                .clip(RoundedCornerShape(32.dp))
                .border(2.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(32.dp))
        ) {
            if (previewMode == "HOME") {
                HomeScreenView(
                    activeTheme = activeTheme,
                    iconRadiusDp = iconRadiusDp,
                    iconList = iconList,
                    dynamicIslandMode = dynamicIslandMode,
                    onToggleControlCenter = onOpenControlCenter
                )
            } else {
                LockscreenView(
                    activeTheme = activeTheme
                )
            }

            // Top Left Preview Switch Pill
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopStart)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Black.copy(alpha = 0.65f))
                    .padding(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (previewMode == "HOME") Color(0xFF007AFF) else Color.Transparent)
                        .clickable { onSelectPreviewMode("HOME") }
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text("Home", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (previewMode == "LOCK") Color(0xFF007AFF) else Color.Transparent)
                        .clickable { onSelectPreviewMode("LOCK") }
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text("Lockscreen", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Dynamic Island State Tester Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Dynamic Island Test", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                item {
                    TesterChip("Music", Icons.Default.MusicNote, dynamicIslandMode is DynamicIslandMode.Music) {
                        onSelectDynamicIslandMode(DynamicIslandMode.Music())
                    }
                }
                item {
                    TesterChip("Timer", Icons.Default.Timer, dynamicIslandMode is DynamicIslandMode.Timer) {
                        onSelectDynamicIslandMode(DynamicIslandMode.Timer())
                    }
                }
                item {
                    TesterChip("Charging", Icons.Default.FlashOn, dynamicIslandMode is DynamicIslandMode.Charging) {
                        onSelectDynamicIslandMode(DynamicIslandMode.Charging())
                    }
                }
                item {
                    TesterChip("Call", Icons.Default.Phone, dynamicIslandMode is DynamicIslandMode.Call) {
                        onSelectDynamicIslandMode(DynamicIslandMode.Call())
                    }
                }
                item {
                    TesterChip("GPS", Icons.Default.Navigation, dynamicIslandMode is DynamicIslandMode.Navigation) {
                        onSelectDynamicIslandMode(DynamicIslandMode.Navigation())
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Theme Presets Header
        Text("iOS 27 Presets for Poco", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        // Theme Cards Horizontal List
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(allThemes) { theme ->
                ThemeCardItem(
                    theme = theme,
                    isApplied = theme.id == activeTheme?.id,
                    isApplying = isApplyingTheme,
                    onApply = { onApplyTheme(theme.id) }
                )
            }
        }
    }
}

@Composable
fun TesterChip(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(if (selected) Color(0xFF007AFF) else Color.White.copy(alpha = 0.12f))
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(label, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun ThemeCardItem(
    theme: ThemePreset,
    isApplied: Boolean,
    isApplying: Boolean,
    onApply: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF131826)),
        modifier = Modifier
            .width(240.dp)
            .border(
                1.dp,
                if (isApplied) Color(0xFF007AFF) else Color.White.copy(alpha = 0.15f),
                RoundedCornerShape(20.dp)
            )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF007AFF).copy(alpha = 0.2f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(theme.tag, color = Color(0xFF007AFF), fontSize = 9.sp, fontWeight = FontWeight.Bold)
                }

                if (isApplied) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF30D158), modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Active", color = Color(0xFF30D158), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(theme.name, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 1)
            Spacer(modifier = Modifier.height(4.dp))
            Text(theme.description, color = Color.White.copy(alpha = 0.6f), fontSize = 11.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onApply,
                enabled = !isApplied && !isApplying,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF007AFF),
                    disabledContainerColor = Color.White.copy(alpha = 0.15f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp)
            ) {
                if (isApplying) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(16.dp))
                } else if (isApplied) {
                    Text("Applied on Poco", color = Color.White.copy(alpha = 0.7f), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                } else {
                    Text("One-Tap Apply Theme", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun LockscreenTabScreen(
    activeTheme: ThemePreset?,
    onToggleFlashlight: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LockscreenView(
            activeTheme = activeTheme,
            onToggleFlashlight = onToggleFlashlight
        )
    }
}

@Composable
fun ControlCenterTabScreen(
    state: com.example.model.ControlCenterState,
    onToggleWifi: () -> Unit,
    onToggleBluetooth: () -> Unit,
    onToggleFlashlight: () -> Unit,
    onToggleDarkMode: () -> Unit,
    onToggleLowPower: () -> Unit,
    onToggleFocus: () -> Unit,
    onToggleMusic: () -> Unit,
    onVolumeChange: (Float) -> Unit,
    onBrightnessChange: (Float) -> Unit,
    onLaunchOverlay: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onLaunchOverlay,
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF)),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Icon(Icons.Default.ControlCamera, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Open Interactive Floating Control Center", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(24.dp))
                .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(24.dp))
        ) {
            ControlCenterSheet(
                state = state,
                onToggleWifi = onToggleWifi,
                onToggleBluetooth = onToggleBluetooth,
                onToggleFlashlight = onToggleFlashlight,
                onToggleDarkMode = onToggleDarkMode,
                onToggleLowPower = onToggleLowPower,
                onToggleFocus = onToggleFocus,
                onToggleMusic = onToggleMusic,
                onVolumeChange = onVolumeChange,
                onBrightnessChange = onBrightnessChange,
                onClose = {}
            )
        }
    }
}

@Composable
fun PocoGuideTabScreen(onOpenGuide: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(Icons.Default.PhoneAndroid, contentDescription = null, tint = Color(0xFFFFC800), modifier = Modifier.size(64.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text("Poco & Xiaomi HyperOS Integration", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Complete setup guide for Poco X, F, and M series phones", color = Color.White.copy(alpha = 0.7f), fontSize = 13.sp)
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onOpenGuide,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC800)),
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(50.dp)
            ) {
                Text("Launch Setup Guide", color = Color.Black, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
