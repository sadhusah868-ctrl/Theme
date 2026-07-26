package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Timer
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DynamicIslandMode

@Composable
fun DynamicIslandView(
    mode: DynamicIslandMode,
    onTogglePlay: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    val shape = if (isExpanded) RoundedCornerShape(28.dp) else RoundedCornerShape(22.dp)

    Box(
        modifier = modifier
            .testTag("dynamic_island_container")
            .animateContentSize(animationSpec = spring(dampingRatio = 0.75f, stiffness = 400f))
            .clip(shape)
            .background(Color.Black)
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    listOf(
                        Color.White.copy(alpha = 0.25f),
                        Color.White.copy(alpha = 0.05f)
                    )
                ),
                shape = shape
            )
            .clickable { isExpanded = !isExpanded }
            .padding(horizontal = if (isExpanded) 16.dp else 12.dp, vertical = if (isExpanded) 14.dp else 8.dp),
        contentAlignment = Alignment.Center
    ) {
        if (!isExpanded) {
            // Compact Mode
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                when (mode) {
                    is DynamicIslandMode.Music -> {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(22.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFFFA2D48)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MusicNote,
                                    contentDescription = "Music",
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = mode.trackName,
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.width(110.dp)
                            )
                        }

                        // Waveform Animation
                        AnimatedWaveform(isPlaying = mode.isPlaying)
                    }

                    is DynamicIslandMode.Charging -> {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.FlashOn,
                                contentDescription = "Charging",
                                tint = Color(0xFF30D158),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Charging",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Text(
                            text = "${mode.percentage}%",
                            color = Color(0xFF30D158),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    is DynamicIslandMode.Timer -> {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = "Timer",
                                tint = Color(0xFFFF9500),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = mode.title,
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Text(
                            text = "${mode.remainingSeconds / 60}:${String.format("%02d", mode.remainingSeconds % 60)}",
                            color = Color(0xFFFF9500),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    is DynamicIslandMode.Call -> {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = "Call",
                                tint = Color(0xFF30D158),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = mode.callerName,
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Text(
                            text = mode.callDuration,
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 11.sp
                        )
                    }

                    is DynamicIslandMode.Navigation -> {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Navigation,
                                contentDescription = "GPS",
                                tint = Color(0xFF007AFF),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = mode.instruction,
                                color = Color.White,
                                fontSize = 11.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.width(120.dp)
                            )
                        }
                        Text(
                            text = mode.distance,
                            color = Color(0xFF007AFF),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    else -> {
                        Text("iOS 27 Dynamic Island", color = Color.White, fontSize = 12.sp)
                    }
                }
            }
        } else {
            // Expanded Rich Mode
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                when (mode) {
                    is DynamicIslandMode.Music -> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFFFA2D48)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MusicNote,
                                    contentDescription = "Artwork",
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = mode.trackName,
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = mode.artist,
                                    color = Color.White.copy(alpha = 0.7f),
                                    fontSize = 13.sp,
                                    maxLines = 1
                                )
                            }

                            AnimatedWaveform(isPlaying = mode.isPlaying, heightDp = 20)
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Player Controls
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.FastRewind, contentDescription = "Prev", tint = Color.White)
                            }
                            IconButton(
                                onClick = onTogglePlay,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color.White)
                            ) {
                                Icon(
                                    imageVector = if (mode.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = "PlayPause",
                                    tint = Color.Black
                                )
                            }
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.FastForward, contentDescription = "Next", tint = Color.White)
                            }
                        }
                    }

                    is DynamicIslandMode.Call -> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF007AFF)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Phone, contentDescription = "Caller", tint = Color.White)
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(mode.callerName, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                Text("Incoming Poco HD Call...", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                            }

                            Row {
                                IconButton(
                                    onClick = { isExpanded = false },
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFFF3B30))
                                ) {
                                    Icon(Icons.Default.CallEnd, contentDescription = "Decline", tint = Color.White)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                IconButton(
                                    onClick = { isExpanded = false },
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF30D158))
                                ) {
                                    Icon(Icons.Default.Phone, contentDescription = "Accept", tint = Color.White)
                                }
                            }
                        }
                    }

                    else -> {
                        Text("Tap to collapse iOS 27 Island", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun AnimatedWaveform(isPlaying: Boolean, heightDp: Int = 14) {
    val infiniteTransition = rememberInfiniteTransition(label = "waveform")
    val h1 by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "h1"
    )
    val h2 by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(550, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "h2"
    )
    val h3 by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(450, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "h3"
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val barColor = Color(0xFFFA2D48)
        Box(
            modifier = Modifier
                .width(3.dp)
                .height((if (isPlaying) heightDp * h1 else 4f).dp)
                .clip(CircleShape)
                .background(barColor)
        )
        Box(
            modifier = Modifier
                .width(3.dp)
                .height((if (isPlaying) heightDp * h2 else 8f).dp)
                .clip(CircleShape)
                .background(barColor)
        )
        Box(
            modifier = Modifier
                .width(3.dp)
                .height((if (isPlaying) heightDp * h3 else 5f).dp)
                .clip(CircleShape)
                .background(barColor)
        )
    }
}
