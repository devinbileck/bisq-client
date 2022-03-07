package bisq.client.android.di

import bisq.client.datasource.cache.NotificationCache
import bisq.client.interactors.notification_detail.GetNotification
import bisq.client.interactors.notification_detail.RemoveNotification
import bisq.client.interactors.notification_detail.UpdateNotification
import bisq.client.interactors.notification_list.FetchNotifications
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
    fun provideFetchNotifications(
        notificationCache: NotificationCache
    ): FetchNotifications {
        return FetchNotifications(
            notificationCache = notificationCache
        )
    }

    @Singleton
    @Provides
    fun provideGetNotification(
        notificationCache: NotificationCache
    ): GetNotification {
        return GetNotification(
            notificationCache = notificationCache
        )
    }

    @Singleton
    @Provides
    fun provideUpdateNotification(
        notificationCache: NotificationCache
    ): UpdateNotification {
        return UpdateNotification(
            notificationCache = notificationCache
        )
    }

    @Singleton
    @Provides
    fun provideRemoveNotification(
        notificationCache: NotificationCache
    ): RemoveNotification {
        return RemoveNotification(
            notificationCache = notificationCache
        )
    }

}
