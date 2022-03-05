package bisq.client.android.presentation.notification_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bisq.client.android.presentation.components.icons.*
import bisq.client.domain.model.Notification
import bisq.client.domain.model.NotificationType
import bisq.client.domain.util.DatetimeUtil
import bisq.client.presentation.notification_detail.NotificationDetailEvents
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun NotificationDetailView(
    notification: Notification,
    onRemoveNotification: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        ) {
            when (NotificationType.valueOf(notification.type)) {
                is NotificationType.PeerMessage -> MessageIcon()
                is NotificationType.MarketOffer -> BitcoinIcon()
                is NotificationType.OfferTaken -> OfferIcon()
                is NotificationType.PriceAlert -> PriceIcon()
                is NotificationType.TradeState -> TradeIcon()
                else -> UnknownIcon()
            }
            Text(
                text = notification.message,
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        }
        val datetimeUtil = remember { DatetimeUtil() }
        Text(
            text = "${datetimeUtil.prettyDatetime(notification.timeOccurred)}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, start = 24.dp),
            style = MaterialTheme.typography.caption
        )
        Button(onClick = onRemoveNotification) {
            Text("Remove")
        }
    }
}
