package bisq.client.domain.model

sealed class NotificationType(var name: String) {
    object TradeState: NotificationType("TradeState")
    object OfferTaken: NotificationType("OfferTaken")
    object PeerMessage: NotificationType("PeerMessage")
    object PriceAlert: NotificationType("PriceAlert")
    object MarketOffer: NotificationType("MarketOffer")

    companion object {
        fun valueOf(name: String): NotificationType = when (name) {
            "TradeState" -> TradeState
            "OfferTaken" -> OfferTaken
            "PeerMessage" -> PeerMessage
            "PriceAlert" -> PriceAlert
            "MarketOffer" -> MarketOffer
            else -> throw Exception("Unknown notification type '$name'")
        }

        fun toString(type: NotificationType): String = when (type) {
            TradeState -> "Trade state changed"
            OfferTaken -> "Offer taken"
            PeerMessage -> "Peer message received"
            PriceAlert -> "Price alert"
            MarketOffer -> "Market offer"
        }
    }
}
