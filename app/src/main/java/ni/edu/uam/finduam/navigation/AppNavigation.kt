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


object Routes {
    const val LOGIN = "login"
    const val HOME = "home"
    const val PUBLICAR = "publicar"
    const val PERFIL = "perfil"
    const val REGISTER = "register"
    const val MIS_PUBLICACIONES = "mis_publicaciones"
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.MIS_PUBLICACIONES) {
            MisPublicacionesScreen(
                onNavigateHome = { navController.navigate(Routes.HOME) },
                onNavigatePublicar = { navController.navigate(Routes.PUBLICAR) },
                onNavigatePerfil = { navController.navigate(Routes.PERFIL) }
            )
        }

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
                }
            )
        }
    }
}
