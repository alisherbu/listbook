package kaa.alisherbu.listbook.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kaa.alisherbu.listbook.core.util.theme.Pink40
import kaa.alisherbu.listbook.core.util.theme.Purple40
import kaa.alisherbu.listbook.core.util.theme.PurpleGrey40

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun ListbookTheme(
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.Transparent, darkIcons = true)
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            dynamicLightColorScheme(LocalContext.current)
        }

        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
