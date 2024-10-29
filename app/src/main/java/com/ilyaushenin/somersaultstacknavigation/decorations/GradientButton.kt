package com.ilyaushenin.somersaultstacknavigation.decorations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GradientButton(
    onClick: () -> Unit,
    text: String
) {
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFD000FF),
            Color(0xFFAB22D4),
            Color(0xFF7024FF),
        )
    )

    Box(
        modifier = Modifier
            .padding(30.dp)
            .size(300.dp, 60.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(gradientBrush)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 20.sp
        )
    }
}