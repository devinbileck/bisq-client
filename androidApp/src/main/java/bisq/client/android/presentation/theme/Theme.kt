package bisq.client.android.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import bisq.client.android.presentation.components.CircularIndeterminateProgressBar
import bisq.client.android.presentation.components.ProcessDialogQueue
import bisq.client.domain.model.GenericMessageInfo
import bisq.client.domain.util.Queue

private val DarkColorPalette = darkColors(
  primary = BisqGreen,
  primaryVariant = BisqGreen,
  onPrimary = Color.Black,
  secondary = BisqGreen,
  secondaryVariant = BisqGreen,
  onSecondary = Color.Black,
  error = RedErrorDark,
  onError = RedErrorLight,
  background = LightGrey,
  onBackground = Color.Black,
  surface = LightGrey,
  onSurface = Color.Black
)

private val LightColorPalette = lightColors(
  primary = BisqGreen,
  primaryVariant = BisqGreen,
  onPrimary = Color.Black,
  secondary = BisqGreen,
  secondaryVariant = BisqGreen,
  onSecondary = Color.Black,
  error = RedErrorDark,
  onError = RedErrorLight,
  background = LightGrey,
  onBackground = Color.Black,
  surface = LightGrey,
  onSurface = Color.Black
)

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun AppTheme(
  displayProgressBar: Boolean,
  dialogQueue: Queue<GenericMessageInfo> = Queue(mutableListOf()),
  onRemoveHeadMessageFromQueue: () -> Unit,
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colors = if (darkTheme) {
    DarkColorPalette
  } else {
    LightColorPalette
  }
  MaterialTheme(
    colors = colors,
    typography = IBMPlexSansTypography,
    shapes = AppShapes,
  ) {
    Box(
//      modifier = Modifier
//        .fillMaxSize()
//        .background(color=DarkGrey)
    ) {
      // For android we can process the DialogQueue at the Application level
      // on iOS you cannot do this because SwiftUI preloads the views in a List
      ProcessDialogQueue(
        dialogQueue = dialogQueue,
        onRemoveHeadMessageFromQueue = onRemoveHeadMessageFromQueue,
      )
      Column {
        content()
      }
      CircularIndeterminateProgressBar(isDisplayed = displayProgressBar, 0.3f)
    }
  }
}
