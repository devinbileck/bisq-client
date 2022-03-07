package bisq.client.datasource.cache

import bisq.client.domain.model.Notification
import bisq.client.domain.util.DatetimeUtil
import com.squareup.sqldelight.db.SqlDriver

class NotificationDatabaseFactory(
    private val driverFactory: DriverFactory
) {
    fun createDatabase(): NotificationDatabase {
        return NotificationDatabase(driverFactory.createDriver())
    }
}

expect class DriverFactory {
   fun createDriver(): SqlDriver
}

fun Notification_Entity.toNotification(): Notification {
    val datetimeUtil = DatetimeUtil()
    return Notification(
        id = id.toInt(),
        type = type,
        message = message,
        timeOccurred = datetimeUtil.toLocalDate(time_occurred),
        timeReceived = datetimeUtil.toLocalDate(time_received),
        unread = unread
    )
}

fun List<Notification_Entity>.toNotificationList(): List<Notification>{
    return map { it.toNotification() }
}
