package bisq.client.presentation.notification_detail

import bisq.client.domain.model.GenericMessageInfo
import bisq.client.domain.model.Notification
import bisq.client.domain.util.Queue

data class NotificationDetailState(
    val isLoading: Boolean = false,
    val notification: Notification? = null,
    val queue: Queue<GenericMessageInfo> = Queue(mutableListOf()), // messages to be displayed in ui
) {
    // Need secondary constructor to initialize with no args in SwiftUI
    constructor(): this(
        isLoading = false,
        notification = null,
        queue = Queue(mutableListOf()),
    )
}
