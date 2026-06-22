package ni.edu.uam.finduam.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun AjustesScreen(
    onVolver: () -> Unit,
    onEditarPerfil: () -> Unit,
    onCambiarPassword: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        IconButton(
            onClick = onVolver
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver"
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Ajustes",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        PerfilMenuItem(
            icon = Icons.Default.Edit,
            title = "Editar perfil",
            onClick = onEditarPerfil
        )

        Spacer(modifier = Modifier.height(8.dp))

        PerfilMenuItem(
            icon = Icons.Default.Lock,
            title = "Cambiar contraseña",
            onClick = onCambiarPassword
        )

        Spacer(modifier = Modifier.height(8.dp))

        PerfilMenuItem(
            icon = Icons.Default.Info,
            title = "Acerca de FindUAM"
        ) {
        }
    }
}