package ni.edu.uam.finduam.model
// Esta clase representa a un usuario dentro de la aplicación FindUAM.
// Se basa en los atributos definidos en el diagrama de clases.
data class Usuario(
    // Identificador único del usuario.
    val idUsuario: Int,
    // Nombre del estudiante.
    val nombre: String,
    // Apellido del estudiante.
    val apellido: String,
    // Foto de perfil del usuario.
    // Por ahora se maneja como String porque puede ser una URL o ruta local.
    val fotoPerfil: String,
    // Correo institucional UAM.
    val correoUam: String,
    // Número telefónico del usuario.
    // Este dato se utilizará para abrir WhatsApp.
    val telefono: String,
    // Contraseña del usuario.
    // En una app real, no se debería guardar directamente así en frontend.
    val password: String
) {
    // Método que valida si los datos ingresados coinciden con el usuario.
    fun iniciarSesion(correoIngresado: String, passwordIngresado: String): Boolean {
        return correoUam == correoIngresado && password == passwordIngresado
    }

    // Método representativo para cerrar sesión.
    fun cerrarSesion() {
        // Aquí luego se podría limpiar la sesión del usuario.
    }

    // Método representativo para actualizar perfil.
    fun actualizarPerfil() {
        // Aquí luego se podría conectar con la API para actualizar datos.
    }

    // Método representativo para publicar un objeto.
    fun publicarObjeto() {
        // La lógica real de publicación se desarrollará en la pantalla Publicar.
    }
}