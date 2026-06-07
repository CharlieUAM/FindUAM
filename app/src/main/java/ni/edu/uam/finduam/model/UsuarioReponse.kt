package ni.edu.uam.finduam.model

data class UsuarioResponse(
    val idUsuario: Int,
    val nombre: String,
    val apellido: String,
    val correoUam: String,
    val telefono: String,
    val password: String,
    val fotoPerfil: String?
)