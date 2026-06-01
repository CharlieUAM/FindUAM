package ni.edu.uam.finduam.model


import java.time.LocalDateTime

// Esta clase representa un objeto perdido publicado dentro de FindUAM.
// Sus atributos coinciden con lo que se mostrará en la publicación.
data class Objeto(
    // Identificador único del objeto.
    val idObjeto: Int,

    // Nombre del objeto publicado.
    val nombre: String,

    // Foto del objeto.
    // Por ahora se maneja como String porque puede ser URL o ruta local.
    val fotoObjeto: String,

    // Ubicación escrita manualmente por el usuario.
    val ubicacion: String,

    // Fecha y hora en que se publicó o encontró el objeto.
    val fechaHora: LocalDateTime,

    // Categoría seleccionada para clasificar el objeto.
    val categoria: Categoria,

    // Usuario que publicó el objeto.
    val publicadoPor: Usuario
) {
    // Método para mostrar el detalle del objeto.
    fun mostrarDetalle(): String {
        return "$nombre encontrado en $ubicacion"
    }

    // Método que obtiene el contacto del usuario publicador.
    // Luego este número se usará para abrir WhatsApp desde Android.
    fun contactarPorWhatsapp(): String {
        return publicadoPor.telefono
    }
}