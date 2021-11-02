package bisq.client.android.di

import bisq.client.BaseApplication
import bisq.client.datasource.cache.*
import bisq.client.domain.util.DatetimeUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideNotificationDatabase(context: BaseApplication): NotificationDatabase {
        return NotificationDatabaseFactory(driverFactory = DriverFactory(context)).createDatabase()
    }

    @Singleton
    @Provides
    fun provideNotificationCache(
        notificationDatabase: NotificationDatabase,
        datetimeUtil: DatetimeUtil,
    ): NotificationCache {
        return NotificationCacheImpl(
            notificationDatabase = notificationDatabase,
            datetimeUtil = datetimeUtil,
        )
    }

}
