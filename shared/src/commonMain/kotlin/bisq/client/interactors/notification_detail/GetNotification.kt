package bisq.client.interactors.notification_detail

import bisq.client.datasource.cache.NotificationCache
import bisq.client.domain.model.GenericMessageInfo
import bisq.client.domain.model.Notification
import bisq.client.domain.util.*
import bisq.client.util.BuildConfig
import bisq.client.domain.model.UIComponentType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class GetNotification (
    private val notificationCache: NotificationCache,
) {

    fun execute(
        notificationId: Int,
    ): CommonFlow<DataState<Notification>> = flow {
        try {
            emit(DataState.loading())

            val notification = notificationCache.get(notificationId)

            emit(DataState.data(message = null, data = notification))

        } catch (e: Exception) {
            emit(DataState.error<Notification>(
                message = GenericMessageInfo.Builder()
                    .id("GetNotification.Error")
                    .title("Error")
                    .uiComponentType(UIComponentType.Dialog)
                    .description(e.message?: "Unknown Error")
            ))
        }
    }.asCommonFlow()

}
