package ni.edu.uam.finduam.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import ni.edu.uam.finduam.ui.theme.UamBackground
import ni.edu.uam.finduam.ui.theme.UamDarkText
import ni.edu.uam.finduam.ui.theme.UamTurquoise
import ni.edu.uam.finduam.ui.theme.UamWhite
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import ni.edu.uam.finduam.network.RetrofitClient
import ni.edu.uam.finduam.network.SessionManager
import ni.edu.uam.finduam.model.Publicacion

@Composable
fun MisPublicacionesScreen(
    onNavigateHome: () -> Unit,
    onNavigatePublicar: () -> Unit,
    onNavigatePerfil: () -> Unit
) {

    val context = LocalContext.current

    val sessionManager = remember {
        SessionManager(context)
    }

    val idUsuario = sessionManager.obtenerIdUsuario()

    var misPublicaciones by remember {
        mutableStateOf<List<Publicacion>>(emptyList())
    }

    LaunchedEffect(Unit) {
        try {
            val response = RetrofitClient.apiService.obtenerPublicaciones()
            if (response.isSuccessful) {
                misPublicaciones = response.body()
                    ?.filter { it.idUsuario == idUsuario }
                    ?: emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Scaffold(
        containerColor = UamBackground,
        bottomBar = {
            BottomNavigationFindUAM(
                selectedItem = "Perfil",
                onNavigateHome = onNavigateHome,
                onNavigatePublicar = onNavigatePublicar,
                onNavigatePerfil = onNavigatePerfil
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Text(
                text = "Mis Publicaciones",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (misPublicaciones.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = UamWhite
                            )
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Text(
                                    text = "Aún no has publicado objetos",
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = "Cuando publiques un objeto aparecerá aquí.")
                            }
                        }
                    }
                }

                items(misPublicaciones) { publicacion ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = UamWhite
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Icon(
                                imageVector = Icons.Default.Inventory2,
                                contentDescription = null
                            )

                            Text(
                                text = publicacion.objeto?.nombre ?: "Sin nombre",
                                color = UamDarkText,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = publicacion.ubicacion,
                                fontSize = 12.sp
                            )

                            Text(
                                text = publicacion.fechaHora?.take(10) ?: "",
                                fontSize = 12.sp
                            )

                            Button(
                                onClick = { /* TODO: Implementar editar */ },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = UamTurquoise
                                )
                            ) {
                                Text("Editar")
                            }
                        }
                    }
                }
            }
        }
    }
}
