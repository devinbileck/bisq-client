package bisq.client.domain.model

import kotlinx.datetime.LocalDateTime

data class Notification(
    val id: Int,
    val type: String,
    val message: String,
    val timeOccurred: LocalDateTime,
    val timeReceived: LocalDateTime,
)
