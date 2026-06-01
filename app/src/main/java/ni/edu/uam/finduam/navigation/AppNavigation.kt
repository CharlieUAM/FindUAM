package ni.edu.uam.finduam.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ni.edu.uam.finduam.screens.HomePlaceholderScreen
import ni.edu.uam.finduam.screens.LoginScreen

// Objeto que guarda los nombres de las rutas de navegación.
// Esto evita escribir strings repetidos en varias partes del código.
object Routes {
    const val LOGIN = "login"
    const val HOME = "home"
}

// Composable principal encargado de manejar la navegación entre pantallas.
@Composable
fun AppNavigation() {
    // Controlador de navegación de Compose.
    val navController = rememberNavController()

    // Contenedor de navegación.
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        // Ruta de la pantalla Login.
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    // Cuando el login sea correcto, navega hacia Home.
                    navController.navigate(Routes.HOME) {
                        // Elimina Login del historial para que no regrese con atrás.
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Ruta temporal de Home.
        // Luego aquí pondremos la pantalla principal real.
        composable(Routes.HOME) {
            HomePlaceholderScreen()
        }
    }
}