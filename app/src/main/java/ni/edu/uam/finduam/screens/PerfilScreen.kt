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
import androidx.compose.ui.platform.LocalContext
import ni.edu.uam.finduam.network.SessionManager
import androidx.compose.material3.Text
import androidx.compose.material.icons.filled.ArrowForward

@Composable
fun PerfilScreen(
    onCerrarSesion: () -> Unit,
    onNavigateHome: () -> Unit,
    onNavigatePublicar: () -> Unit,
    onNavigatePerfil: () -> Unit,
    onNavigateMisPublicaciones: () -> Unit,
    onNavigateAjustes: () -> Unit
) {

    val context = LocalContext.current

    val sessionManager = SessionManager(context)

    val nombre = sessionManager.obtenerNombre()
    val apellido = sessionManager.obtenerApellido()
    val correo = sessionManager.obtenerCorreo()
    val telefono = sessionManager.obtenerTelefono()

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
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(UamWhite),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = nombre.take(1),
                            color = UamTurquoiseDark,
                            fontSize = 38.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.size(16.dp))

                    Column {
                        Text(
                            "$nombre $apellido",
                            color = UamWhite,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = correo,
                            color = UamWhite,
                            fontSize = 13.sp
                        )

                        Text(
                            text = "Estudiante UAM",
                            color = UamWhite,
                            fontSize = 12.sp
                        )

                        Text(
                            text = "Ayudando a recuperar objetos perdidos",
                            color = UamWhite,
                            fontSize = 11.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    PerfilStatCard(
                        title = "Publicados",
                        value = "12",
                        modifier = Modifier.weight(1f)
                    )

                    PerfilStatCard(
                        title = "Hallados",
                        value = "4",
                        modifier = Modifier.weight(1f)
                    )

                    PerfilStatCard(
                        title = "Activos",
                        value = "8",
                        modifier = Modifier.weight(1f)
                    )
                }
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
                            value = "$nombre $apellido"
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        PerfilInfoRow(
                            icon = Icons.Default.Phone,
                            label = "Teléfono",
                            value = telefono
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        PerfilInfoRow(
                            icon = Icons.Default.Email,
                            label = "Correo UAM",
                            value = correo
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))



                PerfilMenuItem(
                    icon = Icons.Default.Inventory2,
                    title = "Mis publicaciones"
                ) {
                    onNavigateMisPublicaciones()
                }

                Spacer(modifier = Modifier.height(8.dp))

                PerfilMenuItem(
                    icon = Icons.Default.Settings,
                    title = "Ajustes"
                ) {
                    onNavigateAjustes()
                }

                Button(
                    onClick = {

                        sessionManager.cerrarSesion()

                        onCerrarSesion()
                    },
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
            containerColor = UamWhite
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
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
                color = UamTurquoiseDark,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = title,
                color = UamTurquoiseDark,
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

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Ir",
                tint = UamGrayText
            )
        }
    }
}