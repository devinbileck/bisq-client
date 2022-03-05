import SwiftUI
import common

struct NotificationDetailView: View {
    
    private let notification: common.Notification?
    private let dateUtil: DatetimeUtil
    private let message: GenericMessageInfo?
    private let onTriggerEvent: (NotificationDetailEvents) -> Void

    @State var showDialog: Bool
    
    init(
        notification: common.Notification?,
        dateUtil: DatetimeUtil,
        message: GenericMessageInfo? = nil,
        onTriggerEvent: @escaping (NotificationDetailEvents) -> Void
    ) {
        self.notification = notification
        self.dateUtil = dateUtil
        self.message = message
        if (message != nil) {
            self.showDialog = true
        } else {
            self.showDialog = false
        }
        self.onTriggerEvent = onTriggerEvent
        print("SHOW DIALOG: \(self.showDialog)")
    }
    
    var body: some View {
        NavigationView {
            ScrollView {
                if (notification != nil) {
                    VStack(alignment: .leading) {
                        HStack() {
                            switch NotificationType.companion.valueOf(name: notification?.type ?? "Unknown") {
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
                            DefaultText(String(notification?.message ?? ""))
                            Spacer()
                        }
                        .padding(/*@START_MENU_TOKEN@*/.all/*@END_MENU_TOKEN@*/)
                        Spacer()
                        DefaultText("\(dateUtil.prettyDatetime(date: notification?.timeOccurred))")
                            .foregroundColor(Color.gray)
                    }
                    .padding(/*@START_MENU_TOKEN@*/.all/*@END_MENU_TOKEN@*/)
                }
            }
            .navigationBarTitle(Text(NotificationType.companion.toString(type: NotificationType.companion.valueOf(name: notification!.type))))
            .alert(isPresented: $showDialog, content: {
                return GenericMessageInfoAlert().build(
                    message: message!,
                    onRemoveHeadMessage: {
                        onTriggerEvent(NotificationDetailEvents.OnRemoveHeadMessageFromQueue())
                    }
                )
            })
        }
        .navigationBarTitleDisplayMode(.inline)
    }
}
