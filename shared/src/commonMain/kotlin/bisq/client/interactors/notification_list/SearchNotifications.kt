package bisq.client.interactors.notification_list

import bisq.client.datasource.cache.NotificationCache
import bisq.client.domain.model.GenericMessageInfo
import bisq.client.domain.model.Notification
import bisq.client.domain.model.NotificationType
import bisq.client.domain.model.UIComponentType
import bisq.client.domain.util.*
import bisq.client.util.BuildConfig
import bisq.client.util.Logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class SearchNotifications(
    private val notificationCache: NotificationCache,
) {
    private val logger = Logger("SearchNotifications")

    fun execute(
        page: Int,
        query: String,
    ): CommonFlow<DataState<List<Notification>>> = flow  {
        try{
            emit(DataState.loading())

            if (BuildConfig().isDebug()) {
                notificationCache.insert(
                    listOf(
                        Notification(
                            1, NotificationType.TradeState.name, "Peer confirmed payment sent (trade ID: xyz345)",
                            kotlinx.datetime.LocalDateTime(2021, 10, 29, 12, 12, 33),
                            kotlinx.datetime.LocalDateTime(2021, 10, 29, 12, 12, 35),
                        ),
                        Notification(
                            2, NotificationType.OfferTaken.name, "Offer taken (trade ID: abc123)",
                            kotlinx.datetime.LocalDateTime(2021, 10, 30, 18, 22, 49),
                            kotlinx.datetime.LocalDateTime(2021, 10, 30, 18, 22, 50),
                        ),
                        Notification(
                            3, NotificationType.PeerMessage.name, "Peer message received",
                            kotlinx.datetime.LocalDateTime(2021, 10, 31, 3, 4, 5),
                            kotlinx.datetime.LocalDateTime(2021, 10, 31, 3, 4, 10),
                        ),
                        Notification(
                            4, NotificationType.PriceAlert.name, "BTC above $60,000 USD",
                            kotlinx.datetime.LocalDateTime(2021, 11, 1, 23, 37, 19),
                            kotlinx.datetime.LocalDateTime(2021, 11, 1, 23, 37, 19),
                        ),
                        Notification(
                            5, NotificationType.MarketOffer.name, "Buy BTC for USD via Zelle (0.1BTC @ $60,000)",
                            kotlinx.datetime.LocalDateTime(2021, 11, 2, 17, 11, 1),
                            kotlinx.datetime.LocalDateTime(2021, 11, 2, 17, 11, 3),
                        )
                    )
                )
            }

            val cacheResult = if (query.isBlank()) {
                notificationCache.getAll(page = page)
            } else {
                notificationCache.search(
                    query = query,
                    page = page,
                )
            }

            emit(DataState.data(message = null, data = cacheResult))
        } catch (e: Exception) {
            emit(DataState.error(
                message = GenericMessageInfo.Builder()
                    .id("SearchNotifications.Error")
                    .title("Error")
                    .uiComponentType(UIComponentType.Dialog)
                    .description(e.message?: "Unknown Error")
            ))
        }
    }.asCommonFlow()

}
