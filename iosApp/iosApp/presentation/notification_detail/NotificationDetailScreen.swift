import SwiftUI
import common

struct NotificationDetailScreen: View {
    
    private let cacheModule: CacheModule
    private let getNotificationModule: GetNotificationModule
    private let notificationId: Int
    private let datetimeUtil = DatetimeUtil()
    
    @ObservedObject var viewModel: NotificationDetailViewModel
    
    init(
        notificationId: Int,
        cacheModule: CacheModule
     ) {
        self.notificationId = notificationId
        self.cacheModule = cacheModule
        self.getNotificationModule = GetNotificationModule(
            cacheModule: self.cacheModule
        )
        viewModel = NotificationDetailViewModel(
            notificationId: self.notificationId,
            getNotification: self.getNotificationModule.getNotification
        )
    }
    
    var body: some View {
        NotificationDetailView(
            notification: viewModel.state.notification,
            dateUtil: datetimeUtil,
            message: viewModel.state.queue.peek(),
            onTriggerEvent: viewModel.onTriggerEvent
        )
    }
}
