package ni.edu.uam.finduam.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ni.edu.uam.finduam.model.Publicacion
import ni.edu.uam.finduam.network.RetrofitClient
import ni.edu.uam.finduam.ui.theme.*

@Composable
fun HomeScreen(
    onNavigateHome: () -> Unit,
    onNavigatePublicar: () -> Unit,
    onNavigatePerfil: () -> Unit
) {
    var textoBusqueda by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf("Todos") }
    var objetosApi by remember { mutableStateOf<List<Publicacion>>(emptyList()) }

    LaunchedEffect(Unit) {
        try {
            val response = RetrofitClient.apiService.obtenerPublicaciones()
            if (response.isSuccessful) {
                objetosApi = response.body() ?: emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    val totalPublicaciones = objetosApi.size
    val totalCategorias = objetosApi.mapNotNull { it.objeto?.categoria }.distinct().size

    val objetosFiltrados = objetosApi.filter { pub ->
        val obj = pub.objeto
        val matchesBusqueda = (obj?.nombre?.contains(textoBusqueda, ignoreCase = true) ?: false) ||
                (obj?.descripcion?.contains(textoBusqueda, ignoreCase = true) ?: false)
        
        val matchesCategoria = when (categoriaSeleccionada) {
            "Todos" -> true
            else -> obj?.categoria == categoriaSeleccionada
        }
        matchesBusqueda && matchesCategoria
    }

    Scaffold(
        containerColor = UamBackground,
        bottomBar = {
            BottomNavigationFindUAM(
                selectedItem = "Inicio",
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
        ) {
            // Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(UamTurquoise)
                    .padding(start = 20.dp, end = 20.dp, top = 42.dp, bottom = 20.dp)
            ) {
                Text(
                    text = "FindUAM",
                    color = UamWhite,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Bienvenido",
                    color = UamWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "¿Qué objeto estás buscando hoy?",
                    color = UamWhite,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = textoBusqueda,
                    onValueChange = { textoBusqueda = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    placeholder = { Text(text = "Buscar objetos perdidos...", color = UamGrayText) },
                    leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar", tint = UamGrayText) },
                    singleLine = true,
                    shape = RoundedCornerShape(28.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = UamWhite,
                        unfocusedContainerColor = UamWhite,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        StatsCard(title = "Publicados", count = totalPublicaciones.toString(), modifier = Modifier.weight(1f))
                        StatsCard(title = "Categorías", count = totalCategorias.toString(), modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CategoriaChip(text = "Todos", selected = categoriaSeleccionada == "Todos") { categoriaSeleccionada = "Todos" }
                        CategoriaChip(text = "Bolsas", selected = categoriaSeleccionada == "Bolsas") { categoriaSeleccionada = "Bolsas" }
                        CategoriaChip(text = "Llaves", selected = categoriaSeleccionada == "Llaves") { categoriaSeleccionada = "Llaves" }
                        CategoriaChip(text = "Electrónica", selected = categoriaSeleccionada == "Electrónica") { categoriaSeleccionada = "Electrónica" }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(objetosFiltrados) { publicacion ->
                    ObjetoCard(publicacion = publicacion)
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun StatsCard(title: String, count: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = UamWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = count, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = UamTurquoiseDark)
            Text(text = title, color = UamGrayText, fontSize = 12.sp)
        }
    }
}

@Composable
fun ObjetoCard(publicacion: Publicacion) {
    val objeto = publicacion.objeto
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = UamWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen del objeto
            Card(
                modifier = Modifier.size(80.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = UamBackground)
            ) {
                if (!objeto?.foto.isNullOrEmpty()) {
                    AsyncImage(
                        model = objeto?.foto,
                        contentDescription = objeto?.nombre,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.ImageNotSupported,
                            contentDescription = null,
                            tint = UamGrayText
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = objeto?.nombre ?: "Sin nombre",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = UamDarkText
                )
                Text(
                    text = objeto?.descripcion ?: "Sin descripción",
                    fontSize = 13.sp,
                    color = UamGrayText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = UamTurquoise
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    
                    val hora = if ((publicacion.fechaHora?.length ?: 0) >= 16) {
                        publicacion.fechaHora?.substring(11, 16) ?: ""
                    } else publicacion.fechaHora ?: ""
                    
                    Text(text = hora, fontSize = 11.sp, color = UamGrayText)
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = UamTurquoise
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = publicacion.ubicacion,
                        fontSize = 11.sp,
                        color = UamGrayText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun CategoriaChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable { onClick() },
        color = if (selected) UamTurquoise else UamBackground,
        shape = RoundedCornerShape(50.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (selected) UamWhite else UamTurquoise,
            fontSize = 12.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
        )
    }
}
