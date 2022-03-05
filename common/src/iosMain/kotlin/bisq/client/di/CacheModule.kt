package bisq.client.di

import bisq.client.datasource.cache.*
import bisq.client.domain.util.DatetimeUtil

class CacheModule {

    private val driverFactory: DriverFactory by lazy {DriverFactory()}
    val notificationDatabase: NotificationDatabase by lazy {
        NotificationDatabaseFactory(driverFactory = driverFactory).createDatabase()
    }
    val notificationCache: NotificationCache by lazy {
        NotificationCacheImpl(
            notificationDatabase = notificationDatabase,
            datetimeUtil = DatetimeUtil()
        )
    }

}
