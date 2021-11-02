package bisq.client.domain.model

sealed class NotificationType(var name: String) {
    object TradeState: NotificationType("TradeState")
    object OfferTaken: NotificationType("OfferTaken")
    object PeerMessage: NotificationType("PeerMessage")
    object PriceAlert: NotificationType("PriceAlert")
    object MarketOffer: NotificationType("MarketOffer")

    companion object {
        fun valueOf(name: String): NotificationType? {
            return NotificationType::class.sealedSubclasses.firstOrNull { it.simpleName == name }?.objectInstance
        }
    }
}
