package bisq.client.presentation.notification_list

import bisq.client.domain.model.GenericMessageInfo
import bisq.client.domain.model.Notification
import bisq.client.domain.util.Queue

actual data class NotificationListState(
    val isLoading: Boolean = false,
    val page: Int = 1,
    val query: String = "",
    val notifications: List<Notification> = listOf(),
    val queue: Queue<GenericMessageInfo> = Queue(mutableListOf()), // messages to be displayed in ui
) {
    // Need secondary constructor to initialize with no args in SwiftUI
    constructor(): this(
        isLoading = false,
        page = 1,
        query = "",
        notifications = listOf(),
        queue = Queue(mutableListOf()),
    )
}
