package bisq.client.android.presentation.notification_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import bisq.client.android.presentation.components.NothingHere
import bisq.client.android.presentation.components.icons.*
import bisq.client.domain.model.Notification
import bisq.client.domain.model.NotificationType
import bisq.client.presentation.notification_list.NOTIFICATION_PAGINATION_PAGE_SIZE
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@ExperimentalStdlibApi
@Composable
fun NotificationList(
    loading: Boolean,
    notifications: List<Notification>,
    page: Int,
    onTriggerNextPage: () -> Unit,
    onClickNotificationListItem: (Int) -> Unit,
) {
    Box(modifier = Modifier
        .background(color = MaterialTheme.colors.surface)
    ) {
        if (notifications.isEmpty()) {
            NothingHere()
        }
        else {
            LazyColumn{
                itemsIndexed(
                    items = notifications
                ) { index, notification ->
                    if ((index + 1) >= (page * NOTIFICATION_PAGINATION_PAGE_SIZE) && !loading) {
                        onTriggerNextPage()
                    }
                    NotificationListItem(
                        notification = notification,
                        onClick = {
                            onClickNotificationListItem(notification.id)
                        }
                    )
                }
            }
        }
    }
}
