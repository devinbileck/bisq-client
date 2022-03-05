package bisq.client.di

import bisq.client.interactors.notification_list.FetchNotifications

class FetchNotificationsModule(
    private val cacheModule: CacheModule
) {
    val fetchNotifications: FetchNotifications by lazy {
        FetchNotifications(
            notificationCache = cacheModule.notificationCache
        )
    }

}
