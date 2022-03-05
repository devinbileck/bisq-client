import SwiftUI
import common

@available(iOS 14.0, *)
struct NotificationListScreen: View {
    
    private let cacheModule: CacheModule
    private let fetchNotificationsModule: FetchNotificationsModule
    private let datetimeUtil = DatetimeUtil()
       
    @ObservedObject var viewModel: NotificationListViewModel
    
    init(
        cacheModule: CacheModule
    ) {
        self.cacheModule = cacheModule
        self.fetchNotificationsModule = FetchNotificationsModule(
            cacheModule: self.cacheModule
        )
        self.viewModel = NotificationListViewModel(
            fetchNotifications: fetchNotificationsModule.fetchNotifications
        )
        // dismiss keyboard when drag starts
        UIScrollView.appearance().keyboardDismissMode = .onDrag
    }
    
    var body: some View {
        NavigationView {
            List {
                ForEach(viewModel.state.notifications, id: \.self.id) { notification in
                    NavigationLink(
                        destination: NotificationDetailScreen(
                            notificationId: Int(notification.id),
                            cacheModule: self.cacheModule
                        )
                    ) {
                        NotificationListItem(notification: notification, dateUtil: datetimeUtil)
                    }
                }
            }
            .navigationBarTitle(Text("Notifications"))
            .alert(isPresented: $viewModel.showDialog, content: {
                let first = viewModel.state.queue.peek()!
                return GenericMessageInfoAlert().build(
                    message: first,
                    onRemoveHeadMessage: {
                        viewModel.onTriggerEvent(stateEvent: NotificationListEvents.OnRemoveHeadMessageFromQueue())
                    }
                )
            })
        }
    }
}

@available(iOS 14.0, *)
struct NotificationListScreen_Previews: PreviewProvider {
    static var previews: some View {
        NotificationListScreen(
            cacheModule: CacheModule()
        )
    }
}
