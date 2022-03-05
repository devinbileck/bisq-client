import SwiftUI
import common

struct NotificationListItem: View {
    
    private let notification: common.Notification
    private let dateUtil: DatetimeUtil
    
    init(
        notification: common.Notification,
        dateUtil: DatetimeUtil
    ) {
        self.notification = notification
        self.dateUtil = dateUtil
    }
    
    var body: some View {
        HStack() {
            switch NotificationType.companion.valueOf(name: notification.type) {
                case is NotificationType.PeerMessage:
                    MessageIcon()
                case is NotificationType.MarketOffer:
                    BitcoinIcon()
                case is NotificationType.OfferTaken:
                    OfferIcon()
                case is NotificationType.PriceAlert:
                    PriceIcon()
                case is NotificationType.TradeState:
                    TradeIcon()
                default:
                    UnknownIcon()
            }
            VStack(alignment: .leading) {
                //DefaultText(String(notification.message))
                DefaultText(String(NotificationType.companion.toString(type: NotificationType.companion.valueOf(name: notification.type))))
                DefaultText("\(dateUtil.prettyDatetime(date: notification.timeOccurred))")
                    .foregroundColor(Color.gray)
            }
        }
    }
}
