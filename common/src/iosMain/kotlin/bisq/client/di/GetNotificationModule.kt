package bisq.client.di

import bisq.client.interactors.notification_detail.GetNotification

class GetNotificationModule(
    private val cacheModule: CacheModule
) {
    val getNotification: GetNotification by lazy {
        GetNotification(
            notificationCache = cacheModule.notificationCache
        )
    }
}
