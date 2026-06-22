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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import ni.edu.uam.finduam.model.Categoria
import ni.edu.uam.finduam.model.Objeto
import ni.edu.uam.finduam.model.Usuario
import ni.edu.uam.finduam.ui.theme.UamBackground
import ni.edu.uam.finduam.ui.theme.UamDarkText
import ni.edu.uam.finduam.ui.theme.UamGrayText
import ni.edu.uam.finduam.ui.theme.UamTurquoise
import ni.edu.uam.finduam.ui.theme.UamTurquoiseDark
import ni.edu.uam.finduam.ui.theme.UamTurquoiseLight
import ni.edu.uam.finduam.ui.theme.UamWhite
import java.time.LocalDateTime
import androidx.compose.foundation.clickable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import ni.edu.uam.finduam.network.RetrofitClient
import ni.edu.uam.finduam.model.ObjetoResponse

@Composable
fun HomeScreen(
    onNavigateHome: () -> Unit,
    onNavigatePublicar: () -> Unit,
    onNavigatePerfil: () -> Unit
) {
    var textoBusqueda by remember {
        mutableStateOf("")
    }
    var categoriaSeleccionada by remember {
        mutableStateOf("Todos")
    }
    var objetosApi by remember {
        mutableStateOf<List<ObjetoResponse>>(emptyList())
    }
    LaunchedEffect(Unit) {

        try {

            val response =
                RetrofitClient.apiService.obtenerPublicaciones()

            if (response.isSuccessful) {

                objetosApi =
                    response.body() ?: emptyList()
            }

        } catch (e: Exception) {

            e.printStackTrace()
        }
    }

   /* val usuarioDemo = Usuario(
        idUsuario = 1,
        nombre = "Nicole",
        apellido = "Pérez García",
        fotoPerfil = "",
        correoUam = "nicole@uam.edu.ni",
        telefono = "60000000",
        password = "12345678"
    )

    val categoriaLlaves = Categoria(1, "Llaves")
    val categoriaElectronica = Categoria(2, "Electrónica")
    val categoriaMochilas = Categoria(3, "Bolsas y Mochilas")

    val objetosPublicados = listOf(
        Objeto(
            idObjeto = 1,
            nombre = "Llaves con llavero azul UAM",
            fotoObjeto = "",
            ubicacion = "Cafetería Campus",
            fechaHora = LocalDateTime.of(2026, 5, 28, 11, 15),
            categoria = categoriaLlaves,
            publicadoPor = usuarioDemo
        ),
        Objeto(
            idObjeto = 2,
            nombre = "AirPods Pro blancos",
            fotoObjeto = "",
            ubicacion = "Facultad de Ciencias, Aula 201",
            fechaHora = LocalDateTime.of(2026, 5, 28, 9, 45),
            categoria = categoriaElectronica,
            publicadoPor = usuarioDemo
        ),
        Objeto(
            idObjeto = 3,
            nombre = "Mochila negra Nike",
            fotoObjeto = "",
            ubicacion = "Edificio M, segundo piso",
            fechaHora = LocalDateTime.of(2026, 5, 28, 8, 30),
            categoria = categoriaMochilas,
            publicadoPor = usuarioDemo
        )
    )
*/

    val totalPublicaciones = objetosApi.size

    val totalCategorias = objetosApi
        .map { it.idCategoria }
        .distinct()
        .size

    val objetosFiltrados = when (categoriaSeleccionada) {

        "Llaves" -> objetosApi.filter {
            it.idCategoria == 1
        }

        "Electrónica" -> objetosApi.filter {
            it.idCategoria == 2
        }

        "Bolsas" -> objetosApi.filter {
            it.idCategoria == 3
        }

        else -> objetosApi
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
                    onValueChange = {
                        textoBusqueda = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    placeholder = {
                        Text(
                            text = "Buscar objetos perdidos...",
                            color = UamGrayText
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = UamGrayText
                        )
                    },
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

                        Card(
                            modifier = Modifier.weight(1f),
                            colors = CardDefaults.cardColors(
                                containerColor = UamWhite
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = totalPublicaciones.toString(),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = UamTurquoiseDark
                                )

                                Text(
                                    text = "Publicados",
                                    color = UamGrayText
                                )
                            }
                        }

                        Card(
                            modifier = Modifier.weight(1f),
                            colors = CardDefaults.cardColors(
                                containerColor = UamWhite
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = totalCategorias.toString(),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = UamTurquoiseDark
                                )

                                Text(
                                    text = "Categorías",
                                    color = UamGrayText
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CategoriaChip(
                            text = "Todos",
                            selected = categoriaSeleccionada == "Todos",
                            onClick = {
                                categoriaSeleccionada = "Todos"
                            }
                        )

                        CategoriaChip(
                            text = "Bolsas",
                            selected = categoriaSeleccionada == "Bolsas",
                            onClick = {
                                categoriaSeleccionada = "Bolsas"
                            }
                        )
                    }
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
        modifier = Modifier.clickable {
            onClick()
        },
        color = if (selected) UamTurquoiseDark else UamTurquoiseLight,
        shape = RoundedCornerShape(50.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(
                horizontal = 14.dp,
                vertical = 7.dp
            ),
            color = if (selected) UamWhite else UamTurquoiseDark,
            fontSize = 12.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
