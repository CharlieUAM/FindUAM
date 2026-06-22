package ni.edu.uam.finduam.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("correo")
    val correoUam: String,
    
    @SerializedName("contrasena")
    val password: String
)