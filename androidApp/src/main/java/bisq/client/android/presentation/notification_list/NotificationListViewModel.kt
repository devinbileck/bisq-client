package bisq.client.android.presentation.notification_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bisq.client.domain.model.GenericMessageInfo
import bisq.client.domain.model.Notification
import bisq.client.domain.util.GenericMessageInfoQueueUtil
import bisq.client.domain.util.Queue
import bisq.client.interactors.notification_list.SearchNotifications
import bisq.client.presentation.notification_list.NotificationListEvents
import bisq.client.presentation.notification_list.NotificationListState
import bisq.client.util.Logger
import bisq.client.domain.model.UIComponentType
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NotificationListViewModel
@Inject
constructor(
    private val searchNotifications: SearchNotifications,
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
            is NotificationListEvents.NewSearch -> {
                newSearch()
            }
            is NotificationListEvents.NextPage -> {
                nextPage()
            }
            is NotificationListEvents.OnUpdateQuery -> {
                state.value = state.value.copy(query =  event.query)
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

    private fun nextPage() {
        state.value = state.value.copy(page = state.value.page + 1)
        loadNotifications()
    }

    private fun newSearch() {
        state.value = state.value.copy(page = 1, notifications = listOf())
        loadNotifications()
    }

    private fun loadNotifications() {
        searchNotifications.execute(
            page = state.value.page,
            query = state.value.query
        ).collectCommon(viewModelScope) { dataState ->
            state.value = state.value.copy(isLoading = dataState.isLoading)

            dataState.data?.let { notifications ->
                appendNotifications(notifications)
            }

            dataState.message?.let { message ->
                appendToMessageQueue(message)
            }
        }
    }

    private fun appendNotifications(notifications: List<Notification>) {
        val curr = ArrayList(state.value.notifications)
        curr.addAll(notifications)
        state.value = state.value.copy(notifications = curr)
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
