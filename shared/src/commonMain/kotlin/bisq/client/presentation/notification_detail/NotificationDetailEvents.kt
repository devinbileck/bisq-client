package bisq.client.presentation.notification_detail

sealed class NotificationDetailEvents {
    data class GetNotification(val notificationId: Int): NotificationDetailEvents()
    object OnRemoveHeadMessageFromQueue: NotificationDetailEvents()
}
