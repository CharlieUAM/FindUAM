package ni.edu.uam.finduam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ni.edu.uam.finduam.navigation.AppNavigation
import ni.edu.uam.finduam.ui.theme.FindUAMTheme

// Actividad principal de la aplicación.
// Desde aquí se carga el tema y la navegación general.
class MainActivity : ComponentActivity() {

    // Método que se ejecuta al abrir la app.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Permite usar el espacio completo de la pantalla.
        enableEdgeToEdge()

        // Define el contenido visual de la aplicación con Jetpack Compose.
        setContent {
            // Aplica los colores y estilos generales de la app.
            FindUAMTheme {
                // Carga la navegación principal.
                AppNavigation()
            }
        }
    }
}