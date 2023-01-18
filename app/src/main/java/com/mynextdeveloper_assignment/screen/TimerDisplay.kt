package com.mynextdeveloper_assignment

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mynextdeveloper_assignment.core.State

@Composable
fun TimerDisplay(
    timerState: Triple<State, String, Float>,
    toggleStartStop: (state: State) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Timer",
            color = Color.Black,
            fontSize = 35.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)

        )
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                timerState.third,
                Modifier.size(300.dp)
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(timerState.second, fontSize = 40.sp)
                Spacer(modifier = Modifier.size(10.dp))
                Row {
                    Button(onClick = {
                        toggleStartStop(timerState.first)
                    }, shape = RectangleShape) {
                        Text(
                            text = when (timerState.first) {
                                State.RUNNING -> {
                                    "Pause"
                                }
                                State.PAUSE -> {
                                    "Resume"
                                }
                                else -> {
                                    "Start"
                                }
                            }, style = TextStyle(color = Color.White)
                        )
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Button(onClick = { toggleStartStop(State.STOP) }, shape = RectangleShape) {
                        Text(text = "Stop", style = TextStyle(color = Color.White))
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewTimerDisplay() {
    TimerDisplay(timerState = Triple(State.STOP, "00:000", 0.0f)) {}
}