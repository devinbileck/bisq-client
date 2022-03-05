package bisq.client.util

import bisq.client.BuildConfig

actual class BuildConfig {
    actual fun isDebug() = BuildConfig.DEBUG
    actual fun isAndroid() = true
}
