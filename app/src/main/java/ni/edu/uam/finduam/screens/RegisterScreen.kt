package ni.edu.uam.finduam.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ni.edu.uam.finduam.ui.theme.*
import kotlinx.coroutines.launch
import ni.edu.uam.finduam.model.UsuarioRequest
import ni.edu.uam.finduam.network.RetrofitClient

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize().background(UamTurquoiseLight),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(24.dp).verticalScroll(scrollState),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = UamWhite)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(text = "Crear cuenta", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = UamTurquoiseDark)
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(value = apellido, onValueChange = { apellido = it }, label = { Text("Apellido") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(value = correo, onValueChange = { correo = it }, label = { Text("Correo UAM") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(value = telefono, onValueChange = { telefono = it }, label = { Text("Teléfono") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Contraseña") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(value = confirmPassword, onValueChange = { confirmPassword = it }, label = { Text("Confirmar contraseña") }, modifier = Modifier.fillMaxWidth(), singleLine = true)

                if (error.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = error, color = Color.Red, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (nombre.isBlank() || apellido.isBlank() || correo.isBlank() || telefono.isBlank() || password.isBlank()) {
                            error = "Completa todos los campos."
                            return@Button
                        }
                        if (password != confirmPassword) {
                            error = "Las contraseñas no coinciden."
                            return@Button
                        }
                        scope.launch {
                            try {
                                val response = RetrofitClient.apiService.registrarUsuario(
                                    UsuarioRequest(
                                        nombre = nombre,
                                        apellido = apellido,
                                        correoUam = correo,
                                        telefono = telefono,
                                        password = password
                                    )
                                )
                                if (response.isSuccessful) {
                                    onRegisterSuccess()
                                } else {
                                    error = "Error al registrar: ${response.code()}. Verifica que el correo no esté en uso."
                                }
                            } catch (e: Exception) {
                                error = "Error de conexión: ${e.message}"
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = UamTurquoise)
                ) {
                    Text("Registrarse")
                }

                TextButton(onClick = onBackToLogin, modifier = Modifier.fillMaxWidth()) {
                    Text("Ya tengo cuenta", color = UamTurquoise)
                }
            }
        }
    }
}