package bisq.client.interactors.notification_detail

import bisq.client.datasource.cache.NotificationCache
import bisq.client.domain.model.GenericMessageInfo
import bisq.client.domain.model.Notification
import bisq.client.domain.util.*
import bisq.client.domain.model.UIComponentType
import kotlinx.coroutines.flow.flow

class RemoveNotification (
    private val notificationCache: NotificationCache
) {

    fun execute(
        notificationId: Int
    ): CommonFlow<DataState<List<Notification>>> = flow {
        try {
            emit(DataState.loading())
            notificationCache.remove(notificationId)
            emit(DataState.data(message = null, data = notificationCache.getAll()))
        } catch (e: Exception) {
            emit(DataState.error(
                message = GenericMessageInfo.Builder()
                    .id("DeleteNotification.Error")
                    .title("Error")
                    .uiComponentType(UIComponentType.Dialog)
                    .description(e.message?: "Unknown Error")
            ))
        }
    }.asCommonFlow()

}
