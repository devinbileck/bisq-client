package bisq.client.android.presentation.notification_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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

@ExperimentalStdlibApi
@ExperimentalCoroutinesApi
@Composable
fun NotificationView(
    notification: Notification,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.surface)
    ) {
        item {
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
            }
        }
    }
}
