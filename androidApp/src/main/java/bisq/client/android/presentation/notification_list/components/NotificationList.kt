package bisq.client.android.presentation.notification_list.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.DismissDirection.EndToStart
import androidx.compose.material.DismissDirection.StartToEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bisq.client.android.presentation.components.NothingHere
import bisq.client.domain.model.Notification
import bisq.client.presentation.notification_list.NotificationListEvents
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun NotificationList(
    notifications: List<Notification>,
    onClickNotificationListItem: (Int) -> Unit,
    onTriggerEvent: (NotificationListEvents) -> Unit
) {
    Box(modifier = Modifier
        .background(color = MaterialTheme.colors.surface)
    ) {
        if (notifications.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally),
                        text = "No notifications",
                        style = MaterialTheme.typography.h2
                    )
                }
            }
        }
        else {
            LazyColumn {
                items(notifications, {notification: Notification -> notification.id}) { notification ->
                    var unread by remember { mutableStateOf(true) }
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToEnd) {
                                unread = false
                                it != DismissValue.DismissedToEnd
                            }
                            else if (it == DismissValue.DismissedToStart) {
                                onTriggerEvent(NotificationListEvents.RemoveNotification(notificationId = notification.id))
                                it != DismissValue.DismissedToStart
                            }
                            it == DismissValue.Default
                        }
                    )
                    SwipeToDismiss(
                        state = dismissState,
                        modifier = Modifier.padding(vertical = 1.dp),
                        directions = setOf(StartToEnd, EndToStart),
                        dismissThresholds = { direction ->
                            FractionalThreshold(if (direction == StartToEnd) 0.25f else 0.5f)
                        },
                        background = {
                            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                            val color by animateColorAsState(
                                when (dismissState.targetValue) {
                                    DismissValue.Default -> Color.LightGray
                                    DismissValue.DismissedToEnd -> Color.Green
                                    DismissValue.DismissedToStart -> Color.Red
                                }
                            )
                            val alignment = when (direction) {
                                StartToEnd -> Alignment.CenterStart
                                EndToStart -> Alignment.CenterEnd
                            }
                            val icon = when (direction) {
                                StartToEnd -> Icons.Default.Done
                                EndToStart -> Icons.Default.Delete
                            }
                            val scale by animateFloatAsState(
                                if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                            )

                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(horizontal = 20.dp),
                                contentAlignment = alignment
                            ) {
                                Icon(
                                    icon,
                                    contentDescription = "Localized description",
                                    modifier = Modifier.scale(scale)
                                )
                            }
                        },
                        dismissContent = {
                            Card(
                                elevation = animateDpAsState(
                                    if (dismissState.dismissDirection != null) 4.dp else 0.dp
                                ).value
                            ) {
                                NotificationListItem(
                                    notification = notification,
                                    unread = unread,
                                    onClick = {
                                        onClickNotificationListItem(notification.id)
                                    }
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}
