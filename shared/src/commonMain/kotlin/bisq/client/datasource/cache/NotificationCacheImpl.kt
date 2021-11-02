package bisq.client.datasource.cache

import bisq.client.domain.model.Notification
import bisq.client.domain.util.DatetimeUtil
import bisq.client.presentation.notification_list.NOTIFICATION_PAGINATION_PAGE_SIZE

class NotificationCacheImpl(
    val notificationDatabase: NotificationDatabase,
    private val datetimeUtil: DatetimeUtil,
): NotificationCache {

    private var queries: NotificationDbQueries = notificationDatabase.notificationDbQueries

    override fun insert(notification: Notification) {
        queries.insertNotification(
            id = notification.id.toLong(),
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

    override fun search(query: String, page: Int): List<Notification> {
        return queries.searchNotifications(
            query = query,
            pageSize = NOTIFICATION_PAGINATION_PAGE_SIZE.toLong(),
            offset = ((page - 1) * NOTIFICATION_PAGINATION_PAGE_SIZE).toLong()
        ).executeAsList().toNotificationList()
    }

    override fun getAll(page: Int): List<Notification> {
        return queries.getAllNotifications(
            pageSize = NOTIFICATION_PAGINATION_PAGE_SIZE.toLong(),
            offset = ((page - 1) * NOTIFICATION_PAGINATION_PAGE_SIZE).toLong()
        ).executeAsList().toNotificationList()
    }

    override fun get(notificationId: Int): Notification? {
        return try {
            queries.getNotificationById(id = notificationId.toLong()).executeAsOne().toNotification()
        } catch (e: NullPointerException) {
            null
        }
    }

}
