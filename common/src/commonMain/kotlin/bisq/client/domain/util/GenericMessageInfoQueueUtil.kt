package bisq.client.domain.util

import bisq.client.domain.model.GenericMessageInfo

/**
 * Ideally an extension method would be preferred, but KMP cannot use extension functions yet.
 */
class GenericMessageInfoQueueUtil {
    fun doesMessageAlreadyExistInQueue(queue: Queue<GenericMessageInfo>, messageInfo: GenericMessageInfo): Boolean{
        for (item in queue.items) {
            if (item.id == messageInfo.id) {
                return true
            }
        }
        return false
    }
}
