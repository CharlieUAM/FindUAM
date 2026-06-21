package ni.edu.uam.finduam.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ni.edu.uam.finduam.ui.theme.UamBackground
import ni.edu.uam.finduam.ui.theme.UamBorder
import ni.edu.uam.finduam.ui.theme.UamDarkText
import ni.edu.uam.finduam.ui.theme.UamGrayText
import ni.edu.uam.finduam.ui.theme.UamTurquoise
import ni.edu.uam.finduam.ui.theme.UamTurquoiseDark
import ni.edu.uam.finduam.ui.theme.UamTurquoiseLight
import ni.edu.uam.finduam.ui.theme.UamWhite
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import coil.compose.AsyncImage
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import ni.edu.uam.finduam.network.RetrofitClient
import ni.edu.uam.finduam.network.SessionManager
import ni.edu.uam.finduam.model.ObjetoRequest
import ni.edu.uam.finduam.model.UsuarioIdRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicarScreen(
    onNavigateHome: () -> Unit,
    onNavigatePublicar: () -> Unit,
    onNavigatePerfil: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf("") }
    var menuCategoriaAbierto by remember { mutableStateOf(false) }
    var imagenUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imagenUri = uri
    }

    val categorias = listOf(
        "Llaves",
        "Electrónica",
        "Bolsas y Mochilas",
        "Documentos",
        "Accesorios"

    )
    val context = LocalContext.current

    val sessionManager = SessionManager(context)

    val scope = rememberCoroutineScope()

    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            fecha = "$day/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            hora = String.format("%02d:%02d", hourOfDay, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )
    Scaffold(
        containerColor = UamBackground,
        bottomBar = {
            BottomNavigationFindUAM(
                selectedItem = "Publicar",
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
                    .padding(start = 20.dp, end = 20.dp, top = 42.dp, bottom = 24.dp)
            ) {
                Text(
                    text = "Publicar objeto perdido",
                    color = UamWhite,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Ayuda a que su dueño lo recupere",
                    color = UamWhite,
                    fontSize = 13.sp
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Foto del objeto *",
                    color = UamDarkText,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(
                            color = UamTurquoiseLight,
                            shape = RoundedCornerShape(18.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = UamBorder,
                            shape = RoundedCornerShape(18.dp)
                        )
                        .clickable {
                            imagePicker.launch("image/*")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto,
                            contentDescription = "Agregar foto",
                            tint = UamTurquoiseDark,
                            modifier = Modifier.size(34.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (imagenUri == null)
                                "Toca para añadir foto"
                            else
                                "Foto seleccionada ✓",
                            color = UamGrayText,
                            fontSize = 13.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                CampoTextoFindUAM(
                    label = "Nombre del objeto *",
                    value = nombre,
                    onValueChange = { nombre = it },
                    placeholder = "Ej: Mochila negra Nike",
                    icon = Icons.Default.Title
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Categoría *",
                    color = UamDarkText,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = menuCategoriaAbierto,
                    onExpandedChange = {
                        menuCategoriaAbierto = !menuCategoriaAbierto
                    }
                ) {
                    TextField(
                        value = categoriaSeleccionada,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        placeholder = {
                            Text(text = "Selecciona una categoría")
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Category,
                                contentDescription = "Categoría",
                                tint = UamGrayText
                            )
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = menuCategoriaAbierto
                            )
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = UamWhite,
                            unfocusedContainerColor = UamWhite,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = menuCategoriaAbierto,
                        onDismissRequest = {
                            menuCategoriaAbierto = false
                        }
                    ) {
                        categorias.forEach { categoria ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = categoria)
                                },
                                onClick = {
                                    categoriaSeleccionada = categoria
                                    menuCategoriaAbierto = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                CampoTextoFindUAM(
                    label = "Ubicación *",
                    value = ubicacion,
                    onValueChange = { ubicacion = it },
                    placeholder = "Ej: Cafetería, cerca de la entrada",
                    icon = Icons.Default.LocationOn
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Column(modifier = Modifier.weight(1f)) {

                        Text(
                            text = "Fecha *",
                            color = UamDarkText,
                            fontSize = 13.sp
                        )

                        Button(
                            onClick = { datePickerDialog.show() },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = UamWhite
                            )
                        ) {
                            Text(
                                text = if (fecha.isEmpty()) "Seleccionar" else fecha,
                                color = UamDarkText
                            )
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {

                        Text(
                            text = "Hora *",
                            color = UamDarkText,
                            fontSize = 13.sp
                        )

                        Button(
                            onClick = { timePickerDialog.show() },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = UamWhite
                            )
                        ) {
                            Text(
                                text = if (hora.isEmpty()) "Seleccionar" else hora,
                                color = UamDarkText
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {

                        if (
                            nombre.isBlank() ||
                            categoriaSeleccionada.isBlank() ||
                            ubicacion.isBlank() ||
                            fecha.isBlank() ||
                            hora.isBlank()
                        ) {
                            return@Button
                        }

                        scope.launch {

                            try {

                                val idCategoria =
                                    when (categoriaSeleccionada) {
                                        "Llaves" -> 1
                                        "Electrónica" -> 2
                                        "Bolsas y Mochilas" -> 3
                                        "Documentos" -> 4
                                        "Accesorios" -> 5
                                        else -> 1
                                    }

                                val objeto = ObjetoRequest(
                                    nombre = nombre,
                                    descripcion = "Publicado desde Android",
                                    ubicacion = ubicacion,
                                    fechaPublicacion = "2026-06-07T12:00:00",
                                    fotoObjeto = null,
                                    idCategoria = idCategoria,
                                    usuario = UsuarioIdRequest(
                                        sessionManager.obtenerIdUsuario()
                                    )
                                )

                                val response =
                                    RetrofitClient.apiService.publicarObjeto(
                                        objeto
                                    )

                                if (response.isSuccessful) {

                                    onNavigateHome()

                                } else {

                                    println(
                                        "Error al publicar"
                                    )
                                }

                            } catch (e: Exception) {

                                e.printStackTrace()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = UamTurquoise,
                        contentColor = UamWhite
                    )
                ) {
                    Text(
                        text = "Publicar objeto",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}