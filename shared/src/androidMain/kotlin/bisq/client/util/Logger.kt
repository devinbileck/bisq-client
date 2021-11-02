package bisq.client.util

actual class Logger actual constructor(
    private val className: String
) {
    actual fun log(msg: String) {
        if (!BuildConfig().isDebug()) {
            // TODO: Implement production logging - Crashlytics or something else?
        } else {
            printLogD(className, msg)
        }
    }
}
