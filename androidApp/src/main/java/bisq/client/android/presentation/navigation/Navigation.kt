package bisq.client.android.presentation.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import bisq.client.android.presentation.notification_detail.NotificationDetailScreen
import bisq.client.android.presentation.notification_detail.NotificationDetailViewModel
import bisq.client.android.presentation.notification_list.NotificationListScreen
import bisq.client.android.presentation.notification_list.NotificationListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalStdlibApi
@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.NotificationList.route) {
        composable(route = Screen.NotificationList.route) {
            val viewModel: NotificationListViewModel = hiltViewModel()
            NotificationListScreen(
                state = viewModel.state.value,
                onTriggerEvent = viewModel::onTriggerEvent,
                onClickNotificationListItem = { notificationId ->
                    navController.navigate("${Screen.NotificationDetail.route}/$notificationId")
                }
            )
        }
        composable(
            route = Screen.NotificationDetail.route + "/{notificationId}",
            arguments = listOf(navArgument("notificationId") {
                type = NavType.IntType
            })
        ) {
            val viewModel: NotificationDetailViewModel = hiltViewModel()
            NotificationDetailScreen(
                state = viewModel.state.value,
                onTriggerEvent = viewModel::onTriggerEvent
            )
        }
    }
}
