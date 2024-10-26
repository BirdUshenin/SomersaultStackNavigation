package com.ilyaushenin.somersaultstacknavigation.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilyaushenin.somersaultstacknavigation.R
import com.ilyaushenin.somersaultstacknavigation.decorations.GradientButton
import com.ilyaushenin.somersaultstacknavigation.decorations.WaveBackground

@Composable
fun WelcomeActivityScreen(
    onOpenSample: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .padding(bottom = 50.dp)
                    .size(150.dp),
            )
            Text(
                textAlign = TextAlign.Center,
                text = "Welcome to SomersaultStackNavigation!",
                modifier = Modifier.padding(bottom = 20.dp),
                color = Color(0xFFAD00AD),
                fontSize = 20.sp,
            )
            Text(
                textAlign = TextAlign.Center,
                text = "This is a sample app that demonstrates " +
                        "a custom navigation system designed to manage " +
                        "screen states in a convenient and flexible way. This " +
                        "library uses a navigation stack approach, where each new screen " +
                        "is added to the stack, and back navigation is easily managed without" +
                        " any extra code."
            )
            GradientButton(
                text = "Go to sample",
                onClick = {
                    onOpenSample()
                }
            )
        }
        WaveBackground(
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}