package ni.edu.uam.finduam.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import ni.edu.uam.finduam.network.SessionManager
import android.widget.Toast
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import ni.edu.uam.finduam.model.UsuarioResponse
import ni.edu.uam.finduam.network.RetrofitClient
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ni.edu.uam.finduam.ui.theme.UamTurquoiseDark
import ni.edu.uam.finduam.ui.theme.UamWhite

@Composable
fun EditarPerfilScreen(
    onVolver: () -> Unit
) {

    val context = LocalContext.current
    val sessionManager = SessionManager(context)
    val scope = rememberCoroutineScope()

    var nombre by remember {
        mutableStateOf(sessionManager.obtenerNombre())
    }

    var apellido by remember {
        mutableStateOf(sessionManager.obtenerApellido())
    }

    var telefono by remember {
        mutableStateOf(sessionManager.obtenerTelefono())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = UamTurquoiseDark
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "✏️ Editar Perfil",
                    color = UamWhite,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Actualiza tu información personal",
                    color = UamWhite
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
            },
            label = {
                Text("Nombre")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = apellido,
            onValueChange = {
                apellido = it
            },
            label = {
                Text("Apellido")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = telefono,
            onValueChange = {
                telefono = it
            },
            label = {
                Text("Teléfono")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                scope.launch {

                    try {

                        val usuarioActualizado =
                            UsuarioResponse(
                                idUsuario = sessionManager.obtenerIdUsuario(),
                                nombre = nombre,
                                apellido = apellido,
                                correoUam = sessionManager.obtenerCorreo(),
                                telefono = telefono,
                                password = "",
                                fotoPerfil = null
                            )

                        val response =
                            RetrofitClient.apiService.actualizarUsuario(
                                sessionManager.obtenerIdUsuario(),
                                usuarioActualizado
                            )

                        if (response.isSuccessful) {

                            sessionManager.guardarUsuario(
                                idUsuario = sessionManager.obtenerIdUsuario(),
                                nombre = nombre,
                                apellido = apellido,
                                correoUam = sessionManager.obtenerCorreo(),
                                telefono = telefono
                            )

                            Toast.makeText(
                                context,
                                "Perfil actualizado",
                                Toast.LENGTH_SHORT
                            ).show()

                            onVolver()
                        }

                    } catch (e: Exception) {

                        Toast.makeText(
                            context,
                            "Error al actualizar",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar cambios")
        }
    }
}