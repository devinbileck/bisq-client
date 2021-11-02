package bisq.client.android.presentation.components.icons

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bisq.client.android.R

@Composable
fun MessageIcon() {
    Text(
        fontFamily = FontFamily(
            Font(R.font.font_awesome)
        ),
        text = "\uf075",
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        modifier = Modifier
            .padding(top = 4.dp)
    )
}
