package ni.edu.uam.finduam.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


// Esquema de colores general usando el turquesa del logo UAM como color principal.
private val FindUAMColorScheme = lightColorScheme(
    primary = UamTurquoise,
    secondary = UamTurquoiseDark,
    background = UamBackground,
    surface = UamWhite,
    onPrimary = UamWhite,
    onSecondary = UamWhite,
    onBackground = UamDarkText,
    onSurface = UamDarkText
)

// Tema principal de la aplicación.
@Composable
fun FindUAMTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = FindUAMColorScheme,
        content = content
    )
}
