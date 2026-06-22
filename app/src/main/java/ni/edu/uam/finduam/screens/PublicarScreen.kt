package ni.edu.uam.finduam.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ni.edu.uam.finduam.ui.theme.*
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar
import android.net.Uri
import android.util.Base64
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import ni.edu.uam.finduam.network.RetrofitClient
import ni.edu.uam.finduam.network.SessionManager
import ni.edu.uam.finduam.model.ObjetoRequest
import ni.edu.uam.finduam.model.UsuarioIdRequest
import java.io.InputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicarScreen(
    onNavigateHome: () -> Unit,
    onNavigatePublicar: () -> Unit,
    onNavigatePerfil: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf("") }
    var menuCategoriaAbierto by remember { mutableStateOf(false) }
    var imagenUri by remember { mutableStateOf<Uri?>(null) }
    var subiendo by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> imagenUri = uri }

    val categorias = listOf("Llaves", "Electrónica", "Bolsas y Mochilas", "Documentos", "Accesorios")
    val sessionManager = remember { SessionManager(context) }
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            fecha = String.format("%04d-%02d-%02d", year, month + 1, day)
        },
        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            hora = String.format("%02d:%02d", hourOfDay, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
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
            // Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(UamTurquoise)
                    .padding(start = 20.dp, end = 20.dp, top = 42.dp, bottom = 24.dp)
            ) {
                Text("Publicar objeto perdido", color = UamWhite, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text("Ayuda a que su dueño lo recupere", color = UamWhite, fontSize = 13.sp)
            }

            Column(modifier = Modifier.padding(20.dp)) {
                // Selector de Imagen
                Text("Foto del objeto *", color = UamDarkText, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(UamTurquoiseLight, RoundedCornerShape(18.dp))
                        .border(1.dp, UamBorder, RoundedCornerShape(18.dp))
                        .clickable { imagePicker.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (imagenUri != null) {
                        AsyncImage(model = imagenUri, contentDescription = null, modifier = Modifier.fillMaxSize())
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.AddAPhoto, null, tint = UamTurquoiseDark, modifier = Modifier.size(40.dp))
                            Text("Toca para añadir foto", color = UamGrayText, fontSize = 13.sp)
                        }
                    }
                }

                Spacer(Modifier.height(18.dp))

                // Campos de texto
                CampoTextoFindUAM("Nombre del objeto *", nombre, { nombre = it }, "Ej: Mochila negra Nike", Icons.Default.Title)
                Spacer(Modifier.height(16.dp))
                CampoTextoFindUAM("Descripción", descripcion, { descripcion = it }, "Color, marca, etc.", Icons.Default.Description)
                Spacer(Modifier.height(16.dp))

                // Categoría Dropdown
                Text("Categoría *", color = UamDarkText, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                ExposedDropdownMenuBox(
                    expanded = menuCategoriaAbierto,
                    onExpandedChange = { menuCategoriaAbierto = !menuCategoriaAbierto }
                ) {
                    TextField(
                        value = categoriaSeleccionada, onValueChange = {}, readOnly = true,
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        placeholder = { Text("Selecciona una categoría") },
                        leadingIcon = { Icon(Icons.Default.Category, null, tint = UamGrayText) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuCategoriaAbierto) },
                        shape = RoundedCornerShape(14.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = UamWhite, unfocusedContainerColor = UamWhite,
                            focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                    ExposedDropdownMenu(expanded = menuCategoriaAbierto, onDismissRequest = { menuCategoriaAbierto = false }) {
                        categorias.forEach { cat ->
                            DropdownMenuItem(text = { Text(cat) }, onClick = {
                                categoriaSeleccionada = cat
                                menuCategoriaAbierto = false
                            })
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
                CampoTextoFindUAM("Ubicación exacta *", ubicacion, { ubicacion = it }, "Ej: Segundo piso Edificio D", Icons.Default.LocationOn)
                Spacer(Modifier.height(16.dp))

                // Fecha y Hora
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Column(Modifier.weight(1f)) {
                        Text("Fecha *", fontSize = 13.sp)
                        Button(onClick = { datePickerDialog.show() }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = UamWhite)) {
                            Text(if (fecha.isEmpty()) "Elegir" else fecha, color = UamDarkText)
                        }
                    }
                    Column(Modifier.weight(1f)) {
                        Text("Hora *", fontSize = 13.sp)
                        Button(onClick = { timePickerDialog.show() }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = UamWhite)) {
                            Text(if (hora.isEmpty()) "Elegir" else hora, color = UamDarkText)
                        }
                    }
                }

                Spacer(Modifier.height(30.dp))

                // Botón Publicar
                Button(
                    onClick = {
                        if (nombre.isBlank() || categoriaSeleccionada.isBlank() || ubicacion.isBlank() || fecha.isBlank() || hora.isBlank()) {
                            Toast.makeText(context, "Llene los campos marcados con *", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        
                        subiendo = true
                        scope.launch {
                            try {
                                val idCat = when (categoriaSeleccionada) {
                                    "Llaves" -> 1; "Electrónica" -> 2; "Bolsas y Mochilas" -> 3; "Documentos" -> 4; else -> 5
                                }
                                
                                // Convertir imagen a Base64
                                val base64Image = imagenUri?.let { uri ->
                                    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                                    val bytes = inputStream?.readBytes()
                                    Base64.encodeToString(bytes, Base64.NO_WRAP)
                                }

                                val request = ObjetoRequest(
                                    nombre = nombre,
                                    descripcion = descripcion.ifBlank { "Sin descripción" },
                                    ubicacion = ubicacion,
                                    fechaPublicacion = "${fecha}T${hora}:00",
                                    fotoObjeto = base64Image,
                                    idCategoria = idCat,
                                    usuario = UsuarioIdRequest(sessionManager.obtenerIdUsuario())
                                )

                                val response = RetrofitClient.apiService.publicarObjeto(request)
                                if (response.isSuccessful) {
                                    Toast.makeText(context, "¡Publicado con éxito!", Toast.LENGTH_LONG).show()
                                    onNavigateHome()
                                } else {
                                    Toast.makeText(context, "Error del servidor", Toast.LENGTH_SHORT).show()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                            } finally {
                                subiendo = false
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    enabled = !subiendo,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = UamTurquoise)
                ) {
                    if (subiendo) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    else Text("Publicar objeto", fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}
