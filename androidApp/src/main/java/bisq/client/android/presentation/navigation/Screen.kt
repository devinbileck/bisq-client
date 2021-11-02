package bisq.client.android.presentation.navigation

sealed class Screen(
    val route: String,
) {
    object NotificationList: Screen("notificationList")
    object NotificationDetail: Screen("notificationDetail")
}
