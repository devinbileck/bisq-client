package bisq.client.android.presentation.notification_detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bisq.client.domain.model.GenericMessageInfo
import bisq.client.domain.util.GenericMessageInfoQueueUtil
import bisq.client.domain.util.Queue
import bisq.client.interactors.notification_detail.GetNotification
import bisq.client.presentation.notification_detail.NotificationDetailEvents
import bisq.client.presentation.notification_detail.NotificationDetailState
import bisq.client.util.Logger
import bisq.client.domain.model.UIComponentType
import bisq.client.interactors.notification_detail.RemoveNotification
import bisq.client.presentation.notification_list.NotificationListEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NotificationDetailViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getNotification: GetNotification,
    private val removeNotification: RemoveNotification
): ViewModel() {

    private val logger = Logger("NotificationDetailViewModel")

    val state: MutableState<NotificationDetailState> = mutableStateOf(NotificationDetailState())

    init {
        savedStateHandle.get<Int>("notificationId")?.let { notificationId ->
            onTriggerEvent(NotificationDetailEvents.GetNotification(notificationId = notificationId))
        }
    }

    fun onTriggerEvent(event: NotificationDetailEvents) {
        when (event) {
            is NotificationDetailEvents.GetNotification -> {
                getNotification(notificationId = event.notificationId)
            }
            is NotificationDetailEvents.RemoveNotification -> {
                removeNotification(notificationId = event.notificationId)
            }
            is NotificationDetailEvents.OnRemoveHeadMessageFromQueue -> {
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

    private fun getNotification(notificationId: Int) {
        getNotification.execute(notificationId = notificationId).collectCommon(viewModelScope) { dataState ->
            state.value = state.value.copy(isLoading = dataState.isLoading)

            dataState.data?.let { notification ->
                state.value = state.value.copy(notification = notification)
            }

            dataState.message?.let { message ->
                appendToMessageQueue(message)
            }
        }
    }

    private fun removeNotification(notificationId: Int) {
        removeNotification.execute(notificationId = notificationId).collectCommon(viewModelScope) { dataState ->
            state.value = state.value.copy(isLoading = dataState.isLoading)

            dataState.message?.let { message ->
                appendToMessageQueue(message)
            }
        }
    }

    private fun appendToMessageQueue(messageInfo: GenericMessageInfo.Builder) {
        if (!GenericMessageInfoQueueUtil()
                .doesMessageAlreadyExistInQueue(queue = state.value.queue,
                    messageInfo = messageInfo.build())) {
            val queue = state.value.queue
            queue.add(messageInfo.build())
            state.value = state.value.copy(queue = queue)
        }
    }
}
