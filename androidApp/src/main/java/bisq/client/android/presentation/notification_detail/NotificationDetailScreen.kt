package bisq.client.android.presentation.notification_detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bisq.client.android.presentation.notification_detail.components.NotificationView
import bisq.client.android.presentation.theme.AppTheme
import bisq.client.presentation.notification_detail.NotificationDetailEvents
import bisq.client.presentation.notification_detail.NotificationDetailState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalStdlibApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
@Composable
fun NotificationDetailScreen(
    state: NotificationDetailState,
    onTriggerEvent: (NotificationDetailEvents) -> Unit,
) {
    AppTheme(
        displayProgressBar = state.isLoading,
        dialogQueue = state.queue,
        onRemoveHeadMessageFromQueue = {
           onTriggerEvent(NotificationDetailEvents.OnRemoveHeadMessageFromQueue)
        }
    ) {
        if (!state.isLoading) {
            if (state.notification != null) {
                NotificationView(notification = state.notification!!)
            } else {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Unable to retrieve details for this notification",
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}
