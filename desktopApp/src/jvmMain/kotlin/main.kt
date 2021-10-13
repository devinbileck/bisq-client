package bisq.client.desktop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import bisq.client.Greeting

fun greet(): String {
    return Greeting().greeting()
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Bisq",
        state = rememberWindowState(
            width = 600.dp,
            height = 600.dp,
            position = WindowPosition(Alignment.Center))
    ) {
        MaterialTheme {
            Row(modifier = Modifier.fillMaxSize(), Arrangement.Center) {
                Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                    Text(text = greet())
                }
            }
        }
    }
}
