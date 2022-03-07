package bisq.client.datasource.cache

import bisq.client.domain.model.Notification

interface NotificationCache {

    fun insert(notification: Notification)

    fun insert(notifications: List<Notification>)

    fun getAll(): List<Notification>

    @Throws(NullPointerException::class)
    fun get(notificationId: Int): Notification?

    fun clearUnread(notificationId: Int)

    fun clearAllUnread()

    fun remove(notificationId: Int)

    fun removeAll()

}
