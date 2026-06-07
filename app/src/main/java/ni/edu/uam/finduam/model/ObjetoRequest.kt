package ni.edu.uam.finduam.model

data class ObjetoRequest(

    val nombre: String,

    val descripcion: String,

    val ubicacion: String,

    val fechaPublicacion: String,

    val fotoObjeto: String?,

    val idCategoria: Int,

    val usuario: UsuarioIdRequest
)