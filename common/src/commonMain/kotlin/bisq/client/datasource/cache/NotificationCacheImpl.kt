package bisq.client.datasource.cache

import bisq.client.domain.model.Notification
import bisq.client.domain.util.DatetimeUtil

class NotificationCacheImpl(
    val notificationDatabase: NotificationDatabase,
    private val datetimeUtil: DatetimeUtil,
): NotificationCache {

    private var queries: NotificationDbQueries = notificationDatabase.notificationDbQueries

    override fun insert(notification: Notification) {
        queries.insertNotification(
            type = notification.type,
            message = notification.message,
            time_occurred = datetimeUtil.toEpochMilliseconds(notification.timeOccurred),
            time_received = datetimeUtil.toEpochMilliseconds(notification.timeReceived),
        )
    }

    override fun insert(notifications: List<Notification>) {
        for (notification in notifications) {
            insert(notification)
        }
    }

    override fun getAll(): List<Notification> {
        return queries.getAllNotifications().executeAsList().toNotificationList()
    }

    override fun get(notificationId: Int): Notification? {
        return try {
            queries.getNotificationById(id = notificationId.toLong()).executeAsOne().toNotification()
        } catch (e: NullPointerException) {
            null
        }
    }

    override fun clearUnread(notificationId: Int) {
        queries.clearNotificationUnreadById(id = notificationId.toLong())
    }

    override fun clearAllUnread() {
        queries.clearAllNotificationsUnread()
    }

    override fun remove(notificationId: Int) {
        queries.removeNotificationById(id = notificationId.toLong())
    }

    override fun removeAll() {
        queries.removeAllNotifications()
    }

}
