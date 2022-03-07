package bisq.client.interactors.notification_list

import bisq.client.datasource.cache.NotificationCache
import bisq.client.domain.model.GenericMessageInfo
import bisq.client.domain.model.Notification
import bisq.client.domain.model.NotificationType
import bisq.client.domain.model.UIComponentType
import bisq.client.domain.util.*
import bisq.client.util.BuildConfig
import kotlinx.coroutines.flow.flow

class FetchNotifications(
    private val notificationCache: NotificationCache
) {
    fun execute(): CommonFlow<DataState<List<Notification>>> = flow  {
        try{
            emit(DataState.loading())

            if (BuildConfig().isDebug()) {
                notificationCache.removeAll()
                notificationCache.insert(
                    listOf(
                        Notification(
                            1, NotificationType.TradeState.name, "Peer confirmed payment sent for trade ID: xyz345",
                            kotlinx.datetime.LocalDateTime(2022, 2, 2, 12, 12, 33),
                            kotlinx.datetime.LocalDateTime(2022, 2, 2, 12, 12, 35),
                        ),
                        Notification(
                            2, NotificationType.OfferTaken.name, "Offer taken for trade ID: abc123",
                            kotlinx.datetime.LocalDateTime(2022, 2, 3, 18, 22, 49),
                            kotlinx.datetime.LocalDateTime(2022, 2, 3, 18, 22, 50),
                        ),
                        Notification(
                            3, NotificationType.PeerMessage.name, "Hi. Sorry for the delay, but I will be sending payment shortly.",
                            kotlinx.datetime.LocalDateTime(2022, 2, 4, 3, 4, 5),
                            kotlinx.datetime.LocalDateTime(2022, 2, 4, 3, 4, 10),
                        ),
                        Notification(
                            4, NotificationType.PriceAlert.name, "BTC is above $60,000 USD",
                            kotlinx.datetime.LocalDateTime(2022, 1, 31, 23, 37, 19),
                            kotlinx.datetime.LocalDateTime(2022, 1, 31, 23, 37, 19),
                        ),
                        Notification(
                            5, NotificationType.MarketOffer.name, "Buy BTC for USD via Zelle (0.1BTC @ $60,000)",
                            kotlinx.datetime.LocalDateTime(2022, 2, 12, 17, 11, 1),
                            kotlinx.datetime.LocalDateTime(2022, 2, 12, 17, 11, 3),
                        )
                    )
                )
            }

            val cacheResult = notificationCache.getAll()

            emit(DataState.data(message = null, data = cacheResult))
        } catch (e: Exception) {
            emit(DataState.error(
                message = GenericMessageInfo.Builder()
                    .id("FetchNotifications.Error")
                    .title("Error")
                    .uiComponentType(UIComponentType.Dialog)
                    .description(e.message?: "Unknown Error")
            ))
        }
    }.asCommonFlow()

}
