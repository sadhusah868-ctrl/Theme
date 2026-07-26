package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SignalCellular4Bar
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ThemePreset
import com.example.model.DynamicIslandMode
import com.example.model.IosIcon

@Composable
fun HomeScreenView(
    activeTheme: ThemePreset?,
    iconRadiusDp: Int,
    iconList: List<IosIcon>,
    dynamicIslandMode: DynamicIslandMode,
    onAppClick: (IosIcon) -> Unit = {},
    onToggleControlCenter: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Resolve wallpaper drawable ID dynamically or fallback
    val wallpaperResId = rememberWallpaperResId(context, activeTheme?.wallpaperResName)

    Box(
        modifier = modifier
            .testTag("home_screen_desktop")
            .fillMaxSize()
    ) {
        // Wallpaper Image
        if (wallpaperResId != 0) {
            Image(
                painter = painterResource(id = wallpaperResId),
                contentDescription = "Wallpaper",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF0F2027), Color(0xFF203A43), Color(0xFF2C5364))
                        )
                    )
            )
        }

        // Dark Glass Tint
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Top Status Bar
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp, vertical = 4.dp)
                    .clickable { onToggleControlCenter() }
            ) {
                Text(
                    text = "09:41",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text("Poco 5G", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp)
                    Icon(Icons.Default.SignalCellular4Bar, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                    Icon(Icons.Default.Wifi, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                    Icon(Icons.Default.BatteryFull, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Dynamic Island Pill
            if (activeTheme?.dynamicIslandEnabled != false) {
                DynamicIslandView(
                    mode = dynamicIslandMode,
                    modifier = Modifier.fillMaxWidth(0.9f).align(Alignment.CenterHorizontally)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // iOS 27 Widget Stack (Weather & Battery Stack)
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Weather Widget
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(130.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .background(Color.White.copy(alpha = 0.18f))
                        .border(1.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(22.dp))
                        .padding(14.dp)
                ) {
                    Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Column {
                                Text("New Delhi", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                Text("Mostly Sunny", color = Color.White.copy(alpha = 0.7f), fontSize = 11.sp)
                            }
                            Icon(Icons.Default.WbSunny, contentDescription = null, tint = Color(0xFFFFC800), modifier = Modifier.size(24.dp))
                        }
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text("28°", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Light)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("H:32° L:22°", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp, modifier = Modifier.padding(bottom = 6.dp))
                        }
                    }
                }

                // Poco x iOS 27 Battery & Performance Widget
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(130.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .background(Color.White.copy(alpha = 0.18f))
                        .border(1.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(22.dp))
                        .padding(14.dp)
                ) {
                    Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
                        Text("Poco Battery & FPS", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("88%", color = Color(0xFF30D158), fontSize = 22.sp, fontWeight = FontWeight.Bold)
                                Text("Battery", color = Color.White.copy(alpha = 0.7f), fontSize = 10.sp)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("120", color = Color(0xFFFF9500), fontSize = 22.sp, fontWeight = FontWeight.Bold)
                                Text("Hz FPS", color = Color.White.copy(alpha = 0.7f), fontSize = 10.sp)
                            }
                        }
                        Text("HyperOS Engine Running", color = Color.White.copy(alpha = 0.6f), fontSize = 10.sp, textAlign = TextAlign.Center)
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // App Icons Grid (4 columns)
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                contentPadding = PaddingValues(vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(iconList.take(12)) { iconItem ->
                    IosAppIconItem(
                        iconItem = iconItem,
                        shapeRadiusDp = iconRadiusDp,
                        onClick = { onAppClick(iconItem) }
                    )
                }
            }

            // Bottom Glass Dock (4 Pinned Apps)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.White.copy(alpha = 0.22f))
                    .border(1.dp, Color.White.copy(alpha = 0.35f), RoundedCornerShape(32.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    iconList.take(4).forEach { iconItem ->
                        IosAppIconItem(
                            iconItem = iconItem,
                            shapeRadiusDp = iconRadiusDp,
                            showLabel = false,
                            onClick = { onAppClick(iconItem) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun IosAppIconItem(
    iconItem: IosIcon,
    shapeRadiusDp: Int,
    showLabel: Boolean = true,
    onClick: () -> Unit
) {
    val vectorIcon = resolveIconVector(iconItem.systemIconName)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(contentAlignment = Alignment.TopEnd) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(shapeRadiusDp.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(iconItem.primaryColor, iconItem.secondaryColor)
                        )
                    )
                    .border(
                        1.dp,
                        Color.White.copy(alpha = 0.3f),
                        RoundedCornerShape(shapeRadiusDp.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = vectorIcon,
                    contentDescription = iconItem.name,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            // Badge Count
            if (iconItem.badgeCount > 0) {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFF3B30)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${iconItem.badgeCount}",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        if (showLabel) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = iconItem.name,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun rememberWallpaperResId(context: android.content.Context, name: String?): Int {
    if (name.isNull_or_empty()) return 0
    return context.resources.getIdentifier(name, "drawable", context.packageName)
}

fun String?.isNull_or_empty(): Boolean = this == null || this.isEmpty()

fun resolveIconVector(name: String): ImageVector {
    return when (name) {
        "phone" -> Icons.Default.Phone
        "message" -> Icons.Default.Message
        "explore" -> Icons.Default.Explore
        "music_note" -> Icons.Default.MusicNote
        "photo_library" -> Icons.Default.PhotoLibrary
        "photo_camera" -> Icons.Default.PhotoCamera
        "settings" -> Icons.Default.Settings
        "access_time" -> Icons.Default.AccessTime
        "wb_sunny" -> Icons.Default.WbSunny
        "edit_note" -> Icons.Default.EditNote
        "folder" -> Icons.Default.Folder
        "rocket_launch" -> Icons.Default.RocketLaunch
        "security" -> Icons.Default.Security
        "calculate" -> Icons.Default.Calculate
        "favorite" -> Icons.Default.Favorite
        "directions_run" -> Icons.Default.DirectionsRun
        "play_circle_filled" -> Icons.Default.PlayCircleFilled
        "chat" -> Icons.Default.Chat
        "camera_alt" -> Icons.Default.CameraAlt
        "palette" -> Icons.Default.Palette
        else -> Icons.Default.RocketLaunch
    }
}
