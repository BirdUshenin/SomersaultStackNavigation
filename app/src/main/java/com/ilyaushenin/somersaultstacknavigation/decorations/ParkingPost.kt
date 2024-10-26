package com.ilyaushenin.somersaultstacknavigation.decorations

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp

@Composable
fun ParkingPost(
    title: String,
    colorUpStick: Color,
    colorProp: Color
) {
    Canvas(modifier = Modifier
        .size(100.dp, 300.dp)
        .padding(16.dp)) {

        drawRect(
            color = colorUpStick,
            topLeft = Offset(size.width / 2 - 10.dp.toPx(), 0f),
            size = Size(20.dp.toPx(), size.height * 0.8f)
        )

        drawRect(
            color = colorProp,
            topLeft = Offset(size.width / 2 - 30.dp.toPx(), size.height * 0.8f),
            size = Size(60.dp.toPx(), size.height * 0.2f)
        )

        drawContext.canvas.nativeCanvas.drawText(
            title,
            size.width / 2,
            size.height * 0.4f,
            Paint().apply {
                color = android.graphics.Color.WHITE
                textSize = 50f
                textAlign = Paint.Align.CENTER
            }
        )
    }
}