package bisq.client.interactors.notification_detail

import bisq.client.datasource.cache.NotificationCache
import bisq.client.domain.model.GenericMessageInfo
import bisq.client.domain.model.Notification
import bisq.client.domain.model.UIComponentType
import bisq.client.domain.util.CommonFlow
import bisq.client.domain.util.DataState
import bisq.client.domain.util.asCommonFlow
import kotlinx.coroutines.flow.flow

class UpdateNotification (
    private val notificationCache: NotificationCache
) {

    fun execute(
        notificationId: Int
    ): CommonFlow<DataState<List<Notification>>> = flow {
        try {
            emit(DataState.loading())
            notificationCache.clearUnread(notificationId)
            emit(DataState.data(message = null, data = notificationCache.getAll()))
        } catch (e: Exception) {
            emit(DataState.error(
                message = GenericMessageInfo.Builder()
                    .id("UpdateNotification.Error")
                    .title("Error")
                    .uiComponentType(UIComponentType.Dialog)
                    .description(e.message?: "Unknown Error")
            ))
        }
    }.asCommonFlow()

}
