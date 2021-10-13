package bisq.client.web

import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable
import bisq.client.Greeting

fun greet(): String {
    return Greeting().greeting()
}

fun main() {
    renderComposable(rootElementId = "root") {
        Text(greet())
    }
}
