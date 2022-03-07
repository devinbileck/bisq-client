package bisq.client.presentation.notification_detail

sealed class NotificationDetailEvents {
    data class GetNotification(val notificationId: Int): NotificationDetailEvents()
    data class UpdateNotification(val notificationId: Int): NotificationDetailEvents()
    data class RemoveNotification(val notificationId: Int): NotificationDetailEvents()
    object OnRemoveHeadMessageFromQueue: NotificationDetailEvents()
}
