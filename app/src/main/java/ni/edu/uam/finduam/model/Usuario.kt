package ni.edu.uam.finduam.model

// Esta clase representa a un usuario dentro de la aplicación FindUAM.
// Se basa directamente en los atributos definidos en el diagrama de clases.
data class Usuario(
    val idUsuario: Int,
    val nombre: String,
    val apellido: String,
    val fotoPerfil: String,
    val correoUam: String,
    val telefono: String,
    val password: String
) {

    // Valida si el correo y la contraseña ingresados coinciden con los datos del usuario.
    fun iniciarSesion(correoIngresado: String, passwordIngresado: String): Boolean {
        return correoUam == correoIngresado && password == passwordIngresado
    }

    // Representa el cierre de sesión del usuario.
    fun cerrarSesion() {
        // Luego aquí se puede limpiar la sesión o regresar al login.
    }

    // Representa la actualización de los datos del perfil.
    fun actualizarPerfil() {
        // Luego aquí se puede conectar con la API para actualizar datos.
    }

    // Representa la acción de publicar un objeto.
    fun publicarObjeto() {
        // Luego esta acción se conectará con la pantalla Publicar.
    }

    // Este método ayuda a mostrar nombre y apellido juntos en Perfil y Home.
    fun obtenerNombreCompleto(): String {
        return "$nombre $apellido"
    }

    // Este método devuelve el teléfono para usarlo al contactar por WhatsApp.
    fun obtenerContacto(): String {
        return telefono
    }
}