package com.example.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ThemePreset

@Composable
fun LockscreenView(
    activeTheme: ThemePreset?,
    onToggleFlashlight: () -> Unit = {},
    onUnlock: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val wallpaperResId = rememberWallpaperResId(context, activeTheme?.wallpaperResName)

    var selectedClockColor by remember { mutableStateOf(Color.White) }
    var selectedClockStyle by remember { mutableStateOf(activeTheme?.clockStyle ?: "iOS27_Condensed") }

    Box(
        modifier = modifier
            .testTag("lock_screen_view")
            .fillMaxSize()
            .clickable { onUnlock() }
    ) {
        // Wallpaper Background
        if (wallpaperResId != 0) {
            Image(
                painter = painterResource(id = wallpaperResId),
                contentDescription = "Lockscreen Wallpaper",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF1F1C2C), Color(0xFF928DAB))
                        )
                    )
            )
        }

        // Dark Glass Gradient Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.35f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.5f)
                        )
                    )
                )
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            // Lock Symbol
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Lock",
                tint = Color.White.copy(alpha = 0.9f),
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Date Header
            Text(
                text = "Sunday, July 26",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(2.dp))

            // Spatial Depth Clock Display
            val fontFamily = when (selectedClockStyle) {
                "Futuristic_Neon" -> FontFamily.Monospace
                "Classic_Thin" -> FontFamily.SansSerif
                else -> FontFamily.Default
            }
            val fontWeight = when (selectedClockStyle) {
                "Classic_Thin" -> FontWeight.Light
                else -> FontWeight.Bold
            }

            Text(
                text = "09:41",
                color = selectedClockColor,
                fontSize = 84.sp,
                fontWeight = fontWeight,
                fontFamily = fontFamily,
                letterSpacing = (-2).sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Lockscreen Widgets Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LockWidgetPill(icon = Icons.Default.WbSunny, text = "28° Delhi")
                LockWidgetPill(icon = Icons.Default.BatteryChargingFull, text = "88%")
                LockWidgetPill(icon = Icons.Default.DirectionsRun, text = "6,420 Steps")
            }

            Spacer(modifier = Modifier.weight(1f))

            // Clock Style Customizer Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Black.copy(alpha = 0.45f))
                    .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
                    .padding(10.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ClockColorCircle(Color.White) { selectedClockColor = Color.White }
                    ClockColorCircle(Color(0xFF30D158)) { selectedClockColor = Color(0xFF30D158) }
                    ClockColorCircle(Color(0xFFAF52DE)) { selectedClockColor = Color(0xFFAF52DE) }
                    ClockColorCircle(Color(0xFFFFC800)) { selectedClockColor = Color(0xFFFFC800) }
                    ClockColorCircle(Color(0xFF32ADE6)) { selectedClockColor = Color(0xFF32ADE6) }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Bottom Quick Action Buttons (Flashlight & Camera)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                IconButton(
                    onClick = onToggleFlashlight,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.25f))
                        .border(1.dp, Color.White.copy(alpha = 0.4f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.FlashOn,
                        contentDescription = "Flashlight",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "Swipe or Tap to Unlock",
                    color = Color.White.copy(alpha = 0.75f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )

                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.25f))
                        .border(1.dp, Color.White.copy(alpha = 0.4f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Camera",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun LockWidgetPill(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(Color.Black.copy(alpha = 0.35f))
            .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(14.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = text, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun ClockColorCircle(color: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(28.dp)
            .clip(CircleShape)
            .background(color)
            .border(2.dp, Color.White.copy(alpha = 0.8f), CircleShape)
            .clickable { onClick() }
    )
}
