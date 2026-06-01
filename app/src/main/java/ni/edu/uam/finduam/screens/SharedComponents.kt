package ni.edu.uam.finduam.screens
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ni.edu.uam.finduam.ui.theme.UamDarkText
import ni.edu.uam.finduam.ui.theme.UamGrayText
import ni.edu.uam.finduam.ui.theme.UamTurquoise
import ni.edu.uam.finduam.ui.theme.UamTurquoiseLight
import ni.edu.uam.finduam.ui.theme.UamWhite

@Composable
fun BottomNavigationFindUAM(
    selectedItem: String,
    onNavigateHome: () -> Unit,
    onNavigatePublicar: () -> Unit,
    onNavigatePerfil: () -> Unit
) {
    NavigationBar(
        containerColor = UamWhite
    ) {
        NavigationBarItem(
            selected = selectedItem == "Inicio",
            onClick = onNavigateHome,
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Inicio"
                )
            },
            label = {
                Text(text = "Inicio")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = UamTurquoise,
                selectedTextColor = UamTurquoise,
                indicatorColor = UamTurquoiseLight,
                unselectedIconColor = UamGrayText,
                unselectedTextColor = UamGrayText
            )
        )

        NavigationBarItem(
            selected = selectedItem == "Publicar",
            onClick = onNavigatePublicar,
            icon = {
                Icon(
                    imageVector = Icons.Default.AddCircleOutline,
                    contentDescription = "Publicar"
                )
            },
            label = {
                Text(text = "Publicar")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = UamTurquoise,
                selectedTextColor = UamTurquoise,
                indicatorColor = UamTurquoiseLight,
                unselectedIconColor = UamGrayText,
                unselectedTextColor = UamGrayText
            )
        )

        NavigationBarItem(
            selected = selectedItem == "Perfil",
            onClick = onNavigatePerfil,
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil"
                )
            },
            label = {
                Text(text = "Perfil")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = UamTurquoise,
                selectedTextColor = UamTurquoise,
                indicatorColor = UamTurquoiseLight,
                unselectedIconColor = UamGrayText,
                unselectedTextColor = UamGrayText
            )
        )
    }
}

@Composable
fun CampoTextoFindUAM(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector
) {
    Text(
        text = label,
        color = UamDarkText,
        fontSize = 13.sp
    )

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        placeholder = {
            Text(
                text = placeholder,
                color = UamGrayText
            )
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = UamGrayText
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(14.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = UamWhite,
            unfocusedContainerColor = UamWhite,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}