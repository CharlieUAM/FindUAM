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
// Esquema de colores principal de FindUAM.
private val FindUAMColorScheme = lightColorScheme(
    primary = UamNavy,
    secondary = UamTurquoise,
    background = UamBackground,
    surface = UamWhite,
    onPrimary = UamWhite,
    onSecondary = UamWhite,
    onBackground = UamDarkText,
    onSurface = UamDarkText
)

// Tema general de la aplicación.
// Todas las pantallas usarán estos colores base.
@Composable
fun FindUAMTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = FindUAMColorScheme,
        content = content
    )
}