package ni.edu.uam.finduam.model

import com.google.gson.annotations.SerializedName

data class UsuarioResponse(
    @SerializedName("id")
    val idUsuario: Long, // Cambiado a Long para coincidir con el backend
    
    val nombre: String,
    val apellido: String,
    
    @SerializedName("correo")
    val correoUam: String,
    
    val telefono: String,
    
    @SerializedName("contrasena")
    val password: String? = null,
    
    val fotoPerfil: String? = null
)