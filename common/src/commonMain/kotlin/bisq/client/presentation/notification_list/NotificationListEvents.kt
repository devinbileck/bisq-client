package bisq.client.presentation.notification_list

sealed class NotificationListEvents {
    object LoadNotifications : NotificationListEvents()
    data class OnUpdateQuery(val query: String) : NotificationListEvents()
    data class RemoveNotification(val notificationId: Int): NotificationListEvents()
    object OnRemoveHeadMessageFromQueue : NotificationListEvents()
}
