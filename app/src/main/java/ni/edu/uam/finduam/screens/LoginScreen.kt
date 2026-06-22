package ni.edu.uam.finduam.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import ni.edu.uam.finduam.R
import ni.edu.uam.finduam.ui.theme.*
import kotlinx.coroutines.launch
import ni.edu.uam.finduam.network.RetrofitClient
import ni.edu.uam.finduam.model.LoginRequest
import ni.edu.uam.finduam.network.SessionManager
import androidx.compose.ui.platform.LocalContext

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var correoUam by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize().background(UamTurquoiseLight),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_uam),
                contentDescription = "Logo Uam",
                modifier = Modifier.size(160.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "FindUAM",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = UamTurquoiseDark
            )

            Text(
                text = "Objetos perdidos · Universidad Americana",
                fontSize = 12.sp,
                color = UamGrayText,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = UamWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                    Text(text = "Correo UAM", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = UamDarkText)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = correoUam,
                        onValueChange = { correoUam = it; errorMessage = "" },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = "nombre@uam.edu.ni") },
                        leadingIcon = { Icon(Icons.Default.Mail, contentDescription = null, tint = UamGrayText) },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = UamTurquoise,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = UamTurquoiseLight,
                            unfocusedContainerColor = UamTurquoiseLight
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Contraseña", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = UamDarkText)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it; errorMessage = "" },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = "Ingresa tu contraseña") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = UamGrayText) },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = null,
                                    tint = UamGrayText
                                )
                            }
                        },
                        singleLine = true,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = UamTurquoise,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = UamTurquoiseLight,
                            unfocusedContainerColor = UamTurquoiseLight
                        )
                    )

                    if (errorMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = errorMessage, color = Color.Red, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.height(22.dp))

                    Button(
                        onClick = {
                            if (correoUam.isBlank() || password.isBlank()) {
                                errorMessage = "Completa tu correo UAM y contraseña."
                                return@Button
                            }
                            scope.launch {
                                try {
                                    val response = RetrofitClient.apiService.login(
                                        LoginRequest(correoUam = correoUam, password = password)
                                    )

                                    if (response.isSuccessful) {
                                        val usuario = response.body()
                                        if (usuario != null) {
                                            SessionManager(context).guardarUsuario(
                                                idUsuario = usuario.idUsuario,
                                                nombre = usuario.nombre,
                                                apellido = usuario.apellido,
                                                correoUam = usuario.correoUam,
                                                telefono = usuario.telefono
                                            )
                                            onLoginSuccess()
                                        }
                                    } else {
                                        errorMessage = when(response.code()) {
                                            401 -> "Correo o contraseña incorrectos."
                                            405 -> "Error del servidor (405): Conflicto de rutas."
                                            else -> "Error del servidor: ${response.code()}"
                                        }
                                    }
                                } catch (e: Exception) {
                                    errorMessage = "Error de conexión: ${e.message}"
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = UamTurquoise, contentColor = UamWhite)
                    ) {
                        Text(text = "Entrar", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(
                        onClick = { onRegisterClick() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "¿No tienes cuenta? Regístrate", color = UamTurquoise, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}