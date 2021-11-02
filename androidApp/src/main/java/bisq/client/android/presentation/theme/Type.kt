package bisq.client.android.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import bisq.client.android.R

private val IBMPlexSans = FontFamily(
        Font(R.font.ibm_plex_sans_light, FontWeight.W300),
        Font(R.font.ibm_plex_sans_regular, FontWeight.W400),
        Font(R.font.ibm_plex_sans_medium, FontWeight.W500),
        Font(R.font.ibm_plex_sans_bold, FontWeight.W600)
)

val IBMPlexSansTypography = Typography(
        h1 = TextStyle(
                fontFamily = IBMPlexSans,
                fontWeight = FontWeight.W500,
                fontSize = 30.sp,
        ),
        h2 = TextStyle(
                fontFamily = IBMPlexSans,
                fontWeight = FontWeight.W500,
                fontSize = 24.sp,
        ),
        h3 = TextStyle(
                fontFamily = IBMPlexSans,
                fontWeight = FontWeight.W500,
                fontSize = 20.sp,
        ),
        h4 = TextStyle(
                fontFamily = IBMPlexSans,
                fontWeight = FontWeight.W400,
                fontSize = 18.sp,
        ),
        h5 = TextStyle(
                fontFamily = IBMPlexSans,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
        ),
        h6 = TextStyle(
                fontFamily = IBMPlexSans,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
        ),
        subtitle1 = TextStyle(
                fontFamily = IBMPlexSans,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
        ),
        subtitle2 = TextStyle(
                fontFamily = IBMPlexSans,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
        ),
        body1 = TextStyle(
                fontFamily = IBMPlexSans,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
        ),
        body2 = TextStyle(
                fontFamily = IBMPlexSans,
                fontSize = 14.sp
        ),
        button = TextStyle(
                fontFamily = IBMPlexSans,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp,
                color = Color.White
        ),
        caption = TextStyle(
                fontFamily = IBMPlexSans,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
        ),
        overline = TextStyle(
                fontFamily = IBMPlexSans,
                fontWeight = FontWeight.W400,
                fontSize = 12.sp
        )
)
