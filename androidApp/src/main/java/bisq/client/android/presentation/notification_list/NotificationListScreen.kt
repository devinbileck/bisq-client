package bisq.client.android.presentation.notification_list

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import bisq.client.android.presentation.notification_list.components.NotificationList
import bisq.client.android.presentation.theme.AppTheme
import bisq.client.presentation.notification_list.NotificationListEvents
import bisq.client.presentation.notification_list.NotificationListState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun NotificationListScreen(
    state: NotificationListState,
    onTriggerEvent: (NotificationListEvents) -> Unit,
    onClickNotificationListItem: (Int) -> Unit
) {
    AppTheme(
        displayProgressBar = state.isLoading,
        dialogQueue = state.queue,
        onRemoveHeadMessageFromQueue = {
            onTriggerEvent(NotificationListEvents.OnRemoveHeadMessageFromQueue)
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Notifications",
                        style = MaterialTheme.typography.h1)
                    }
                )
            },
        ) {
            NotificationList(
                notifications = state.notifications,
                onClickNotificationListItem = onClickNotificationListItem,
                onTriggerEvent = onTriggerEvent
            )
        }
    }
}
