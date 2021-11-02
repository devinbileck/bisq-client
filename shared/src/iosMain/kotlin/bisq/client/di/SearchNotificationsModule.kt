package bisq.client.di

import bisq.client.interactors.notification_list.SearchNotifications

class SearchNotificationsModule(
    private val cacheModule: CacheModule,
) {
    val searchNotifications: SearchNotifications by lazy{
        SearchNotifications(
            notificationCache = cacheModule.notificationCache
        )
    }

}
