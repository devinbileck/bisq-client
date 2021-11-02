package bisq.client.android.di

import bisq.client.datasource.cache.NotificationCache
import bisq.client.interactors.notification_detail.GetNotification
import bisq.client.interactors.notification_list.SearchNotifications
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorsModule {

    @Singleton
    @Provides
    fun provideSearchNotifications(
        notificationCache: NotificationCache,
    ): SearchNotifications {
        return SearchNotifications(
            notificationCache = notificationCache
        )
    }

    @Singleton
    @Provides
    fun provideGetNotification(
        notificationCache: NotificationCache,
    ): GetNotification {
        return GetNotification(
            notificationCache = notificationCache
        )
    }

}
