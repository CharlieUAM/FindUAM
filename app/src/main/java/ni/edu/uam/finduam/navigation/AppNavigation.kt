package ni.edu.uam.finduam.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ni.edu.uam.finduam.screens.HomeScreen
import ni.edu.uam.finduam.screens.LoginScreen
import ni.edu.uam.finduam.screens.PublicarScreen
import ni.edu.uam.finduam.screens.PerfilScreen


object Routes {
    const val LOGIN = "login"
    const val HOME = "home"
    const val PUBLICAR = "publicar"
    const val PERFIL = "perfil"
}

// Función principal que controla la navegación entre pantallas.
@Composable
fun AppNavigation() {

    // Controlador de navegación.
    val navController = rememberNavController()

    // Contenedor de pantallas.
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {

        // Pantalla de inicio de sesión.
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Pantalla principal.
        composable(Routes.HOME) {
            HomeScreen(
                onNavigateHome = {
                    navController.navigate(Routes.HOME)
                },
                onNavigatePublicar = {
                    navController.navigate(Routes.PUBLICAR)
                },
                onNavigatePerfil = {
                    navController.navigate(Routes.PERFIL)
                }
            )
        }

        // Pantalla para publicar objeto.
        composable(Routes.PUBLICAR) {
            PublicarScreen(
                onNavigateHome = {
                    navController.navigate(Routes.HOME)
                },
                onNavigatePublicar = {
                    navController.navigate(Routes.PUBLICAR)
                },
                onNavigatePerfil = {
                    navController.navigate(Routes.PERFIL)
                }
            )
        }

        // Pantalla de perfil.
        composable(Routes.PERFIL) {
            PerfilScreen(
                onCerrarSesion = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) {
                            inclusive = true
                        }
                    }
                },
                onNavigateHome = {
                    navController.navigate(Routes.HOME)
                },
                onNavigatePublicar = {
                    navController.navigate(Routes.PUBLICAR)
                },
                onNavigatePerfil = {
                    navController.navigate(Routes.PERFIL)
                }
            )
        }
    }
}