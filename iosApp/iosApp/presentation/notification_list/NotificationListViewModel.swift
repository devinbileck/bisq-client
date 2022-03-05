import SwiftUI
import common

class NotificationListViewModel: ObservableObject {
    
    private let logger = Logger(className: "NotificationListViewModel")
    
    let fetchNotifications: FetchNotifications
    
    @Published var state: NotificationListState = NotificationListState()
    
    @Published var showDialog: Bool = false
    
    init(
        fetchNotifications: FetchNotifications
    ) {
        self.fetchNotifications = fetchNotifications
        onTriggerEvent(stateEvent: NotificationListEvents.LoadNotifications())
    }
    
    func onTriggerEvent(stateEvent: NotificationListEvents) {
        switch stateEvent {
            case is NotificationListEvents.LoadNotifications:
                loadNotifications()
            case is NotificationListEvents.OnUpdateQuery:
                onUpdateQuery(query: (stateEvent as! NotificationListEvents.OnUpdateQuery).query)
            case NotificationListEvents.OnRemoveHeadMessageFromQueue():
                removeHeadFromQueue()
            default:
                doNothing()
        }
    }
    
    func doNothing() {}
    
    private func loadNotifications() {
        do {
            try fetchNotifications.execute().collectCommon(
                coroutineScope: nil,
                callback: { dataState in
                if (dataState != nil) {
                    let data = dataState?.data
                    let message = dataState?.message
                    let loading = dataState?.isLoading ?? false

                    self.updateState(isLoading: loading)

                    if (data != nil) {
                        self.appendNotifications(notifications: data as! [common.Notification])
                    }
                    if (message != nil) {
                        self.handleMessageByUIComponentType(message!.build())
                    }
                } else {
                    self.logger.log(msg: "DataState is nil")
                }
            })
        } catch {
            self.logger.log(msg: "\(error)")
        }
    }
    
    private func resetSearchState() {
        let currentState = (self.state.copy() as! NotificationListState)
        self.state = self.state.doCopy(
            isLoading: currentState.isLoading,
            page: 1,
            query: currentState.query,
            notifications: [],
            bottomNotification: currentState.bottomNotification,
            queue: currentState.queue
        )
    }

    private func onUpdateQuery(query: String) {
        updateState(query: query)
    }

    private func onUpdateBottomNotification(notification: common.Notification) {
        updateState(bottomNotification: notification)
    }

    private func appendNotifications(notifications: [common.Notification]) {
        var currentState = (self.state.copy() as! NotificationListState)
        var currentNotifications = currentState.notifications
        currentNotifications.append(contentsOf: notifications)
        self.state = self.state.doCopy(
            isLoading: currentState.isLoading,
            page: currentState.page,
            query: currentState.query,
            notifications: currentNotifications,
            bottomNotification:  currentState.bottomNotification,
            queue: currentState.queue
        )
        currentState = (self.state.copy() as! NotificationListState)
        self.onUpdateBottomNotification(notification: currentState.notifications[currentState.notifications.count - 1])
    }
    
    private func handleMessageByUIComponentType(_ message: GenericMessageInfo) {
        switch message.uiComponentType{
            case UIComponentType.Dialog():
                appendToQueue(message: message)
            case UIComponentType.None():
                logger.log(msg: "\(message.description)")
            default:
                doNothing()
        }
    }
    
    func shouldQueryNextPage(notification: common.Notification) -> Bool {
        // check if looking at the bottom notification
        // if lookingAtBottom -> proceed
        // if PAGE_SIZE * page <= notifications.length
        // if !queryInProgress
        // else -> do nothing
        let currentState = (self.state.copy() as! NotificationListState)
        if (notification.id == currentState.bottomNotification?.id) {
            if (NotificationListState.Companion().NOTIFICATION_PAGINATION_PAGE_SIZE * currentState.page <= currentState.notifications.count) {
                if (!currentState.isLoading) {
                    return true
                }
            }
        }
        return false
    }
    
    private func appendToQueue(message: GenericMessageInfo) {
        let currentState = (self.state.copy() as! NotificationListState)
        let queue = currentState.queue
        let queueUtil = GenericMessageInfoQueueUtil() // prevent duplicates
        if !queueUtil.doesMessageAlreadyExistInQueue(queue: queue, messageInfo: message) {
            queue.add(element: message)
            updateState(queue: queue)
        }
    }
    
    /**
     *  Remove the head message from queue
     */
    func removeHeadFromQueue() {
        let currentState = (self.state.copy() as! NotificationListState)
        let queue = currentState.queue
        do {
            try queue.remove()
            updateState(queue: queue)
        } catch {
            self.logger.log(msg: "\(error)")
        }
    }
    
    /**
     *  Not everything can be conveniently updated with this function.
     *  Things like notifications, selectedCategory must have their own functions.
     */
    private func updateState(
        isLoading: Bool? = nil,
        page: Int? = nil,
        query: String? = nil,
        bottomNotification: common.Notification? = nil,
        queue: Queue<GenericMessageInfo>? = nil
    ) {
        let currentState = (self.state.copy() as! NotificationListState)
        self.state = self.state.doCopy(
            isLoading: isLoading ?? currentState.isLoading,
            page: Int32(page ?? Int(currentState.page)),
            query: query ?? currentState.query,
            notifications: currentState.notifications,
            bottomNotification: bottomNotification ?? currentState.bottomNotification,
            queue: queue ?? currentState.queue
        )
        shouldShowDialog()
    }
    
    func shouldShowDialog() {
        let currentState = (self.state.copy() as! NotificationListState)
        showDialog = currentState.queue.items.count > 0
    }
}
