package bisq.client.android.presentation.notification_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bisq.client.android.presentation.components.icons.*
import bisq.client.domain.model.Notification
import bisq.client.domain.model.NotificationType
import bisq.client.domain.util.DatetimeUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalStdlibApi
@Composable
fun NotificationListItem(
    notification: Notification,
    onClick: () -> Unit
) {
    val datetimeUtil = remember { DatetimeUtil() }
    Column(
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
            )
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 0.dp, start = 8.dp, end = 8.dp)
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp, bottom = 8.dp, start = 38.dp, end = 8.dp)
        ) {
            Text(
                text = datetimeUtil.prettyDatetime(notification.timeOccurred),
                style = MaterialTheme.typography.caption
            )
        }
    }
}
