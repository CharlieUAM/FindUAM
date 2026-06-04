package ni.edu.uam.finduam.model

data class UsuarioRequest(
    val nombre: String,
    val apellido: String,
    val correoUam: String,
    val telefono: String,
    val password: String,
    val fotoPerfil: String = ""
)