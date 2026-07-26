package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ScreenRotation
import androidx.compose.material.icons.filled.SignalCellular4Bar
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ControlCenterState

@Composable
fun ControlCenterSheet(
    state: ControlCenterState,
    onToggleWifi: () -> Unit,
    onToggleBluetooth: () -> Unit,
    onToggleFlashlight: () -> Unit,
    onToggleDarkMode: () -> Unit,
    onToggleLowPower: () -> Unit,
    onToggleFocus: () -> Unit,
    onToggleMusic: () -> Unit,
    onVolumeChange: (Float) -> Unit,
    onBrightnessChange: (Float) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .testTag("control_center_sheet")
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.65f))
            .clickable { onClose() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .clip(RoundedCornerShape(32.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x73252A38),
                            Color(0x4D121622)
                        )
                    )
                )
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.35f),
                            Color.White.copy(alpha = 0.08f)
                        )
                    ),
                    shape = RoundedCornerShape(32.dp)
                )
                .clickable(enabled = false) {}
                .padding(20.dp)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "iOS 27 Control Center",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Poco Glass Engine",
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 12.sp
                    )
                }

                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f))
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Row 1: Connectivity Group & Music Group
            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Connectivity Card (4 Toggles)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(145.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.White.copy(alpha = 0.12f))
                        .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(24.dp))
                        .padding(12.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            GlassToggleButton(
                                icon = Icons.Default.AirplanemodeActive,
                                active = state.airplaneMode,
                                activeBg = Color(0xFFFF9500),
                                onClick = {}
                            )
                            GlassToggleButton(
                                icon = Icons.Default.SignalCellular4Bar,
                                active = state.cellularEnabled,
                                activeBg = Color(0xFF30D158),
                                onClick = {}
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            GlassToggleButton(
                                icon = Icons.Default.Wifi,
                                active = state.wifiEnabled,
                                activeBg = Color(0xFF007AFF),
                                onClick = onToggleWifi
                            )
                            GlassToggleButton(
                                icon = Icons.Default.Bluetooth,
                                active = state.bluetoothEnabled,
                                activeBg = Color(0xFF007AFF),
                                onClick = onToggleBluetooth
                            )
                        }
                    }
                }

                // Music Player Tile
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(145.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.White.copy(alpha = 0.12f))
                        .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(24.dp))
                        .padding(14.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFFFA2D48)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.MusicNote, contentDescription = "Music", tint = Color.White)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = state.currentSong,
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = state.currentArtist,
                                    color = Color.White.copy(alpha = 0.6f),
                                    fontSize = 10.sp,
                                    maxLines = 1
                                )
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IconButton(
                                onClick = onToggleMusic,
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.25f))
                            ) {
                                Icon(
                                    imageVector = if (state.isPlayingMusic) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = "Play",
                                    tint = Color.White
                                )
                            }
                            IconButton(
                                onClick = {},
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.15f))
                            ) {
                                Icon(Icons.Default.SkipNext, contentDescription = "Next", tint = Color.White)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Row 2: Sliders (Brightness & Volume)
            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Brightness Slider
                GlassVerticalSlider(
                    value = state.brightness,
                    onValueChange = onBrightnessChange,
                    icon = Icons.Default.Brightness7,
                    modifier = Modifier
                        .weight(1f)
                        .height(140.dp)
                )

                // Volume Slider
                GlassVerticalSlider(
                    value = state.volume,
                    onValueChange = onVolumeChange,
                    icon = Icons.Default.VolumeUp,
                    modifier = Modifier
                        .weight(1f)
                        .height(140.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Row 3: Quick Tools Grid
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                GlassQuickTool(
                    icon = Icons.Default.FlashOn,
                    label = "Torch",
                    active = state.flashlightOn,
                    activeColor = Color(0xFFFFC800),
                    onClick = onToggleFlashlight
                )
                GlassQuickTool(
                    icon = Icons.Default.NightlightRound,
                    label = "Focus",
                    active = state.focusModeOn,
                    activeColor = Color(0xFF5856D6),
                    onClick = onToggleFocus
                )
                GlassQuickTool(
                    icon = Icons.Default.DarkMode,
                    label = "Dark",
                    active = state.darkModeOn,
                    activeColor = Color(0xFFAF52DE),
                    onClick = onToggleDarkMode
                )
                GlassQuickTool(
                    icon = Icons.Default.ScreenRotation,
                    label = "Rotate",
                    active = state.autoRotate,
                    activeColor = Color(0xFF007AFF),
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun GlassToggleButton(
    icon: ImageVector,
    active: Boolean,
    activeBg: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(if (active) activeBg else Color.White.copy(alpha = 0.15f))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (active) Color.White else Color.White.copy(alpha = 0.7f),
            modifier = Modifier.size(22.dp)
        )
    }
}

@Composable
fun GlassVerticalSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White.copy(alpha = 0.12f))
            .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(24.dp))
            .pointerInput(Unit) {
                detectVerticalDragGestures { change, dragAmount ->
                    change.consume()
                    val delta = -dragAmount / 250f
                    val newVal = (value + delta).coerceIn(0f, 1f)
                    onValueChange(newVal)
                }
            },
        contentAlignment = Alignment.BottomCenter
    ) {
        // Filled level
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(value.coerceIn(0.05f, 1f))
                .background(Color.White.copy(alpha = 0.85f))
        )

        // Floating Icon
        Box(
            modifier = Modifier
                .padding(bottom = 12.dp)
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (value > 0.4f) Color.Black else Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun GlassQuickTool(
    icon: ImageVector,
    label: String,
    active: Boolean,
    activeColor: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(if (active) activeColor else Color.White.copy(alpha = 0.12f))
                .border(1.dp, Color.White.copy(alpha = 0.15f), CircleShape)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (active) Color.White else Color.White.copy(alpha = 0.8f),
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.75f),
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
