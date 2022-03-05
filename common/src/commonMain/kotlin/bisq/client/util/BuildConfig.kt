package bisq.client.util

expect class BuildConfig() {
    fun isDebug(): Boolean
    fun isAndroid(): Boolean
}
