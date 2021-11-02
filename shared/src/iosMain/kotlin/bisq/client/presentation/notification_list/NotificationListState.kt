package bisq.client.presentation.notification_list

import bisq.client.domain.model.GenericMessageInfo
import bisq.client.domain.model.Notification
import bisq.client.domain.util.Queue

actual data class NotificationListState(
    val isLoading: Boolean = false,
    val page: Int = 1,
    val query: String = "",
    val notifications: List<Notification> = listOf(),
    val bottomNotification: Notification? = null, // track the notification at the bottom of the list so we know when to trigger pagination
    val queue: Queue<GenericMessageInfo> = Queue(mutableListOf()), // messages to be displayed in ui
)  {
    // Need secondary constructor to initialize with no args in SwiftUI

    constructor(): this(
        isLoading = false,
        page = 1,
        query = "",
        notifications = listOf(),
        bottomNotification = null,
        queue = Queue(mutableListOf()),
    )

    companion object{
        const val NOTIFICATION_PAGINATION_PAGE_SIZE = 30
    }
}
