package com.ilyaushenin.somersaultstacknavigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilyaushenin.somersault.SomersaultStackNavigation
import com.ilyaushenin.somersaultstacknavigation.decorations.ParkingPost
import com.ilyaushenin.somersaultstacknavigation.decorations.ParkingSignWithPole

@Composable
fun ParkingPlace(
    title: String,
    stackNav: SomersaultStackNavigation<String>
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ParkingSignWithPole()
        Spacer(modifier = Modifier.height(16.dp))
        Text(title, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            stackNav.onForwardFlip("BlockA")
        }) { Text("Go to Block A") }

        Button(onClick = {
            stackNav.onBackFlip()
        }) { Text("Back") }
    }
}

@Composable
fun BlockA(
    title: String,
    stackNav: SomersaultStackNavigation<String>
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ParkingPost(
            title = "A",
            colorUpStick = Color(0xFF1E88E5),
            colorProp = Color(0xFF144C88)
        )

        Text(title, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            stackNav.onForwardFlip("BlockB")
        }) { Text("Go to Block B") }

        Button(onClick = {
            stackNav.onForwardFlip("BlockC")
        }) { Text("Go to Block C") }

        Button(onClick = {
            stackNav.onBackFlip()
        }) { Text("Back") }
    }
}

@Composable
fun BlockB(
    title: String,
    stackNav: SomersaultStackNavigation<String>
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ParkingPost(
            title = "B",
            colorUpStick = Color(0xFFFC0000),
            colorProp = Color(0xFFB62323)
        )

        Text(title, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            stackNav.onForwardFlip("ParkingPlace")
        }) { Text("Go to ParkingPlace") }

        Button(onClick = {
            stackNav.onBackFlip()
        }) { Text("Back") }
    }
}

@Composable
fun BlockC(
    title: String,
    stackNav: SomersaultStackNavigation<String>
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ParkingPost(
            title = "C",
            colorUpStick = Color.Gray,
            colorProp = Color.DarkGray
        )
        Text(title, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            stackNav.onForwardFlip("BlockA")
        }) { Text("Go to Block A") }

        Button(onClick = {
            stackNav.onBackFlip()
        }) { Text("Back") }
    }
}