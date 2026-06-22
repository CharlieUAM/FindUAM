package ni.edu.uam.finduam.model

import com.google.gson.annotations.SerializedName

data class UsuarioRequest(
    val nombre: String,
    val apellido: String,

    @SerializedName("correo")
    val correoUam: String,

    val telefono: String,

    @SerializedName("contrasena")
    val password: String,

    val fotoPerfil: String = ""
)