package ni.edu.uam.finduam.model

import com.google.gson.annotations.SerializedName

data class Publicacion(
    val id: Long? = null,
    val ubicacion: String,
    val fechaHora: String? = null,
    val idUsuario: Long? = null,
    val objeto: Objeto? = null
)
