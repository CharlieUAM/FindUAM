package ni.edu.uam.finduam.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ni.edu.uam.finduam.screens.HomeScreen
import ni.edu.uam.finduam.screens.LoginScreen
import ni.edu.uam.finduam.screens.PublicarScreen
import ni.edu.uam.finduam.screens.PerfilScreen
import ni.edu.uam.finduam.screens.RegisterScreen
import ni.edu.uam.finduam.screens.MisPublicacionesScreen
import ni.edu.uam.finduam.screens.AjustesScreen
import ni.edu.uam.finduam.screens.EditarPerfilScreen

object Routes {
    const val LOGIN = "login"
    const val HOME = "home"
    const val PUBLICAR = "publicar"
    const val PERFIL = "perfil"
    const val REGISTER = "register"
    const val MIS_PUBLICACIONES = "mis_publicaciones"
    const val AJUSTES = "ajustes"
    const val EDITAR_PERFIL = "editar_perfil"
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
        composable(Routes.MIS_PUBLICACIONES) {
            MisPublicacionesScreen()
        }

        // Pantalla de inicio de sesión.
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Routes.REGISTER)
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

        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Routes.LOGIN)
                },
                onBackToLogin = {
                    navController.popBackStack()
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
                },
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
                },
                onNavigateMisPublicaciones = {
                    navController.navigate(Routes.MIS_PUBLICACIONES)
                },
                onNavigateAjustes = {
                    navController.navigate(Routes.AJUSTES)
                }
            )
        }
        composable(Routes.AJUSTES) {

            AjustesScreen(

                onVolver = {
                    navController.popBackStack()
                },

                onEditarPerfil = {
                    navController.navigate(Routes.EDITAR_PERFIL)
                },

                onCambiarPassword = {
                    // luego navegaremos a CambiarPasswordScreen
                }
            )
        }
        composable(Routes.EDITAR_PERFIL) {
            EditarPerfilScreen(
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

    }
}