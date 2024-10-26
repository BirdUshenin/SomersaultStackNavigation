package com.ilyaushenin.somersaultstacknavigation.decorations

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ParkingSignWithPole() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    color = Color(0xFF1E88E5),
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 0.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "P",
                color = Color.White,
                fontSize = 80.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        Box(
            modifier = Modifier
                .width(15.dp)
                .height(180.dp)
                .background(color = Color.Gray)
        )
    }
}