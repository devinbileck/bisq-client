package bisq.client.presentation.notification_list

sealed class NotificationListEvents {
    object LoadNotifications : NotificationListEvents()

    object NewSearch : NotificationListEvents()

    object NextPage : NotificationListEvents()

    data class OnUpdateQuery(val query: String) : NotificationListEvents()

    object OnRemoveHeadMessageFromQueue : NotificationListEvents()
}
