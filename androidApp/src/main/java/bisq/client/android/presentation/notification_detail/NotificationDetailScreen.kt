package bisq.client.android.presentation.notification_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bisq.client.android.presentation.navigation.Screen
import bisq.client.android.presentation.notification_detail.components.NotificationDetailView
import bisq.client.android.presentation.theme.AppTheme
import bisq.client.presentation.notification_detail.NotificationDetailEvents
import bisq.client.presentation.notification_detail.NotificationDetailState
import bisq.client.presentation.notification_list.NotificationListEvents
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
@Composable
fun NotificationDetailScreen(
    state: NotificationDetailState,
    onTriggerEvent: (NotificationDetailEvents) -> Unit,
    navController: NavController
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
                NotificationDetailView(
                    notification = state.notification!!,
                    onRemoveNotification = {
                        onTriggerEvent(
                            NotificationDetailEvents.RemoveNotification(notificationId = state.notification!!.id))
                        navController.navigateUp()
                    }
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(modifier = Modifier.align(Alignment.Center)) {
                        Text(
                            modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally),
                            text = "¯\\_(ツ)_/¯",
                            style = TextStyle(fontSize = 55.sp)
                        )
                        Text(
                            modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally),
                            text = "Unable to retrieve details for this notification",
                            style = MaterialTheme.typography.h4
                        )
                    }
                }
            }
        }
    }
}
