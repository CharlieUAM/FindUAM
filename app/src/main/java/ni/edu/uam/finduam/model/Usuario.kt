package ni.edu.uam.finduam.model

import com.google.gson.annotations.SerializedName

data class Usuario(
    val id: Long? = null,
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val correo: String,
    @SerializedName("contrasena") val password: String? = null
)
