package bisq.client

import android.app.Application
import bisq.client.util.Logger
import dagger.hilt.android.HiltAndroidApp

val logger = Logger("AppDebug")

@HiltAndroidApp
class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }

}
