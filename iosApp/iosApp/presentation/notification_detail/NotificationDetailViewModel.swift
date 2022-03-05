import SwiftUI
import common

class NotificationDetailViewModel: ObservableObject {
    
    private let logger = Logger(className: "NotificationDetailViewModel")

    private let getNotification: GetNotification

    @Published var state: NotificationDetailState = NotificationDetailState()
    
    init(
        notificationId: Int,
        getNotification: GetNotification
    ) {
        self.getNotification = getNotification
        onTriggerEvent(stateEvent: NotificationDetailEvents.GetNotification(notificationId: Int32(notificationId)))
    }
    
    func onTriggerEvent(stateEvent: NotificationDetailEvents) {
        switch stateEvent {
        case is NotificationDetailEvents.GetNotification:
            getNotification(notificationId: Int((stateEvent as! NotificationDetailEvents.GetNotification).notificationId))
        case is NotificationDetailEvents.OnRemoveHeadMessageFromQueue:
            removeHeadFromQueue()
        default: doNothing()
        }
    }
        
    private func getNotification(notificationId: Int) {
        do {
            try self.getNotification.execute(
                notificationId: Int32(notificationId)
            ).collectCommon(
                coroutineScope: nil,
                callback: { dataState in
                if (dataState != nil) {
                    let data = dataState?.data
                    let message = dataState?.message
                    let loading = dataState?.isLoading ?? false
                    self.updateState(isLoading: loading)
                    
                    if (data != nil) {
                        self.updateState(notification: data! as common.Notification)
                    }
                    if (message != nil) {
                        self.handleMessageByUIComponentType(message!.build())
                    }
                } else {
                    self.logger.log(msg: "GetNotification: DataState is nil")
                }
            })
        } catch {
            self.logger.log(msg: "\(error)")
        }
    }
    
    private func handleMessageByUIComponentType(_ message: GenericMessageInfo) {
        switch message.uiComponentType {
        case UIComponentType.Dialog():
            appendToQueue(message: message)
        case UIComponentType.None():
            logger.log(msg: "\(message.description)")
        default:
            doNothing()
        }
    }
    
    private func appendToQueue(message: GenericMessageInfo) {
        let currentState = (self.state.copy() as! NotificationDetailState)
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
    private func removeHeadFromQueue() {
        let currentState = (self.state.copy() as! NotificationDetailState)
        let queue = currentState.queue
        do {
            try queue.remove()
            updateState(queue: queue)
        } catch {
            logger.log(msg: "\(error)")
        }
    }
    
    private func doNothing() {}
    
    private func updateState(
        isLoading: Bool? = nil,
        notification: common.Notification? = nil,
        queue: Queue<GenericMessageInfo>? = nil
    ) {
        let currentState = (self.state.copy() as! NotificationDetailState)
        self.state = self.state.doCopy(
            isLoading: isLoading ?? currentState.isLoading,
            notification: notification ?? currentState.notification,
            queue: queue ?? currentState.queue
        )
    }
    
}
