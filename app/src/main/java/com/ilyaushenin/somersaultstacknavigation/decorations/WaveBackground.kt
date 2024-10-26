package com.ilyaushenin.somersaultstacknavigation.decorations

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp

@Composable
fun WaveBackground(modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        val wavePath = Path().apply {
            moveTo(0f, size.height * 0.5f)
            quadraticBezierTo(
                size.width * 0.25f, size.height * 0.75f,
                size.width * 0.5f, size.height * 0.5f
            )
            quadraticBezierTo(
                size.width * 0.75f, size.height * 0.25f,
                size.width, size.height * 0.5f
            )
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }

        drawPath(
            path = wavePath,
            color = Color(0xFFAD00AD),
            style = Fill
        )
    }
}