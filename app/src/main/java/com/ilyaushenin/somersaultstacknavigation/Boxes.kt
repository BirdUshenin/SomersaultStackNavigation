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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilyaushenin.somersault.SomersaultStackNavigation

@Composable
fun BoxA(
    title: String,
    stackNav: SomersaultStackNavigation<String>
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            stackNav.onForwardFlip("BoxB")
        }) { Text("Go to Box B") }

        Button(onClick = {
            stackNav.onForwardFlip("BoxC")
        }) { Text("Go to Box C") }

        Button(onClick = {
            stackNav.onBackFlip()
        }) { Text("Back") }
    }
}

@Composable
fun BoxB(
    title: String,
    stackNav: SomersaultStackNavigation<String>
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            stackNav.onForwardFlip("BoxC")
        }) { Text("Go to Box C") }

        Button(onClick = {
            stackNav.onBackFlip()
        }) { Text("Back") }
    }
}

@Composable
fun BoxC(
    title: String,
    stackNav: SomersaultStackNavigation<String>
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            stackNav.onForwardFlip("BoxA")
        }) { Text("Go to Box A") }

        Button(onClick = {
            stackNav.onBackFlip()
        }) { Text("Back") }
    }
}