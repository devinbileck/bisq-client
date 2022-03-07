package bisq.client.android.presentation.notification_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bisq.client.domain.model.GenericMessageInfo
import bisq.client.domain.model.UIComponentType
import bisq.client.domain.util.GenericMessageInfoQueueUtil
import bisq.client.domain.util.Queue
import bisq.client.interactors.notification_detail.UpdateNotification
import bisq.client.interactors.notification_detail.RemoveNotification
import bisq.client.interactors.notification_list.FetchNotifications
import bisq.client.presentation.notification_list.NotificationListEvents
import bisq.client.presentation.notification_list.NotificationListState
import bisq.client.util.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NotificationListViewModel
@Inject
constructor(
    private val fetchNotifications: FetchNotifications,
    private val updateNotification: UpdateNotification,
    private val removeNotification: RemoveNotification
): ViewModel() {

    private val logger = Logger("NotificationListViewModel")

    val state: MutableState<NotificationListState> = mutableStateOf(NotificationListState())

    init {
        loadNotifications()
    }

    fun onTriggerEvent(event: NotificationListEvents) {
        when (event) {
            is NotificationListEvents.LoadNotifications -> {
                loadNotifications()
            }
            is NotificationListEvents.OnUpdateQuery -> {
                state.value = state.value.copy(query = event.query)
            }
            is NotificationListEvents.UpdateNotification -> {
                updateNotification(notificationId = event.notificationId)
            }
            is NotificationListEvents.RemoveNotification -> {
                removeNotification(notificationId = event.notificationId)
            }
            is NotificationListEvents.OnRemoveHeadMessageFromQueue -> {
                removeHeadMessage()
            }
            else -> {
                val messageInfoBuilder = GenericMessageInfo.Builder()
                    .id(UUID.randomUUID().toString())
                    .title("Invalid Event")
                    .uiComponentType(UIComponentType.Dialog)
                    .description("Something went wrong.")
                appendToMessageQueue(messageInfo = messageInfoBuilder)
            }
        }
    }

    private fun removeHeadMessage() {
        try {
            val queue = state.value.queue
            queue.remove() // can throw exception if empty
            state.value = state.value.copy(queue = Queue(mutableListOf())) // force recompose
            state.value = state.value.copy(queue = queue)
        } catch (e: Exception) {
            logger.log("Nothing to remove from DialogQueue")
        }
    }

    private fun loadNotifications() {
        fetchNotifications.execute().collectCommon(viewModelScope) { dataState ->
            state.value = state.value.copy(isLoading = dataState.isLoading)

            dataState.data?.let { notifications ->
                state.value = state.value.copy(notifications = notifications)
            }

            dataState.message?.let { message ->
                appendToMessageQueue(message)
            }
        }
    }

    private fun updateNotification(notificationId: Int) {
        updateNotification.execute(notificationId = notificationId).collectCommon(viewModelScope) { dataState ->
            state.value = state.value.copy(isLoading = dataState.isLoading)

            dataState.data?.let { notifications ->
                state.value = state.value.copy(notifications = notifications)
            }

            dataState.message?.let { message ->
                appendToMessageQueue(message)
            }
        }
    }

    private fun removeNotification(notificationId: Int) {
        removeNotification.execute(notificationId = notificationId).collectCommon(viewModelScope) { dataState ->
            state.value = state.value.copy(isLoading = dataState.isLoading)

            dataState.data?.let { notifications ->
                state.value = state.value.copy(notifications = notifications)
            }

            dataState.message?.let { message ->
                appendToMessageQueue(message)
            }
        }
    }

    private fun appendToMessageQueue(messageInfo: GenericMessageInfo.Builder) {
        if(!GenericMessageInfoQueueUtil()
                .doesMessageAlreadyExistInQueue(queue = state.value.queue,messageInfo = messageInfo.build())) {
            val queue = state.value.queue
            queue.add(messageInfo.build())
            state.value = state.value.copy(queue = queue)
        }
    }

}
