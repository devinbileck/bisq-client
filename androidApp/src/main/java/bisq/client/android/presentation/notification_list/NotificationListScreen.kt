package bisq.client.android.presentation.notification_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import bisq.client.android.presentation.notification_list.components.NotificationList
import bisq.client.android.presentation.theme.AppTheme
import bisq.client.presentation.notification_list.NotificationListEvents
import bisq.client.presentation.notification_list.NotificationListState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalStdlibApi
@Composable
fun NotificationListScreen(
    state: NotificationListState,
    onTriggerEvent: (NotificationListEvents) -> Unit,
    onClickNotificationListItem: (Int) -> Unit,
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
                Text(
                    text = "Notifications",
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            },
        ) {
            NotificationList(
                loading = state.isLoading,
                notifications = state.notifications,
                page = state.page,
                onTriggerNextPage = {
                    onTriggerEvent(NotificationListEvents.NextPage)
                },
                onClickNotificationListItem = onClickNotificationListItem
            )
        }
    }
}
