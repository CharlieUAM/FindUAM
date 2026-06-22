package ni.edu.uam.finduam.model

data class Objeto(
    val id: Long? = null,
    val nombre: String,
    val descripcion: String,
    val categoria: String,
    val foto: String? = null
)
