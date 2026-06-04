package ni.edu.uam.finduam.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ni.edu.uam.finduam.model.Usuario
import ni.edu.uam.finduam.ui.theme.UamBackground
import ni.edu.uam.finduam.ui.theme.UamDarkText
import ni.edu.uam.finduam.ui.theme.UamGrayText
import ni.edu.uam.finduam.ui.theme.UamTurquoise
import ni.edu.uam.finduam.ui.theme.UamTurquoiseDark
import ni.edu.uam.finduam.ui.theme.UamTurquoiseLight
import ni.edu.uam.finduam.ui.theme.UamWhite
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings

@Composable
fun PerfilScreen(
    onCerrarSesion: () -> Unit,
    onNavigateHome: () -> Unit,
    onNavigatePublicar: () -> Unit,
    onNavigatePerfil: () -> Unit,
    onNavigateMisPublicaciones: () -> Unit
) {

    val usuarioDemo = Usuario(
        idUsuario = 1,
        nombre = "Nicole",
        apellido = "Pérez García",
        fotoPerfil = "",
        correoUam = "nicole@uam.edu.ni",
        telefono = "85013423",
        password = "12345678"
    )

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
                .background(UamBackground)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(UamTurquoise)
                    .padding(start = 22.dp, end = 22.dp, top = 46.dp, bottom = 28.dp)
            ) {
                Text(
                    text = "Mi perfil",
                    color = UamWhite,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(22.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(CircleShape)
                            .background(UamWhite),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = "Foto de perfil",
                            tint = UamTurquoiseDark,
                            modifier = Modifier.size(38.dp)
                        )
                    }

                    Spacer(modifier = Modifier.size(16.dp))

                    Column {
                        Text(
                            text = "${usuarioDemo.nombre} ${usuarioDemo.apellido}",
                            color = UamWhite,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = usuarioDemo.correoUam,
                            color = UamWhite,
                            fontSize = 13.sp
                        )

                        Text(
                            text = "Estudiante UAM",
                            color = UamWhite,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                PerfilStatCard(
                    title = "Objetos publicados",
                    value = "0",
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = UamWhite
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 3.dp
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {
                        Text(
                            text = "Información del usuario",
                            color = UamGrayText,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        PerfilInfoRow(
                            icon = Icons.Default.Badge,
                            label = "Nombre completo",
                            value = "${usuarioDemo.nombre} ${usuarioDemo.apellido}"
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        PerfilInfoRow(
                            icon = Icons.Default.Phone,
                            label = "Teléfono",
                            value = usuarioDemo.telefono
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        PerfilInfoRow(
                            icon = Icons.Default.Email,
                            label = "Correo UAM",
                            value = usuarioDemo.correoUam
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                PerfilMenuItem(
                    icon = Icons.Default.Edit,
                    title = "Editar perfil"
                ) {
                    // Aquí luego navegaremos a EditarPerfilScreen
                }

                Spacer(modifier = Modifier.height(8.dp))

                PerfilMenuItem(
                    icon = Icons.Default.Inventory2,
                    title = "Mis publicaciones"
                ) {
                    onNavigateMisPublicaciones()
                }

                Spacer(modifier = Modifier.height(8.dp))

                PerfilMenuItem(
                    icon = Icons.Default.Settings,
                    title = "Cambiar contraseña"
                ) {
                    // Aquí luego navegaremos a CambiarPasswordScreen
                }

                Spacer(modifier = Modifier.height(22.dp))

                Button(
                    onClick = onCerrarSesion,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = UamTurquoiseDark,
                        contentColor = UamWhite
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "Cerrar sesión"
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        text = "Cerrar sesión",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun PerfilStatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = UamTurquoiseDark
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                color = UamWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = title,
                color = UamWhite,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun PerfilInfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = UamTurquoiseDark,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.size(14.dp))

        Column {
            Text(
                text = label,
                color = UamGrayText,
                fontSize = 12.sp
            )

            Text(
                text = value,
                color = UamDarkText,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
@Composable
fun PerfilMenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = UamWhite
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = UamTurquoiseDark
            )

            Spacer(modifier = Modifier.size(14.dp))

            Text(
                text = title,
                color = UamDarkText,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}