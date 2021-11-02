package bisq.client.datasource.cache

import bisq.client.domain.model.Notification

interface NotificationCache {

    fun insert(notification: Notification)

    fun insert(notifications: List<Notification>)

    fun search(query: String, page: Int): List<Notification>

    fun getAll(page: Int): List<Notification>

    @Throws(NullPointerException::class)
    fun get(notificationId: Int): Notification?

}
