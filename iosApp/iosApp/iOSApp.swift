import SwiftUI
import common

@main
struct iOSApp: App {
    
    private let cacheModule = CacheModule()
    
    var body: some Scene {
        WindowGroup {
            NotificationListScreen(
                cacheModule: cacheModule
            )
        }
    }
}
