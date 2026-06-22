package ni.edu.uam.finduam.network

import android.content.Context

class SessionManager(context: Context) {

    private val prefs =
        context.getSharedPreferences(
            "finduam_session",
            Context.MODE_PRIVATE
        )

    fun guardarUsuario(
        idUsuario: Long,
        nombre: String,
        apellido: String,
        correoUam: String,
        telefono: String
    ) {
        prefs.edit()
            .putLong("idUsuario", idUsuario)
            .putString("nombre", nombre)
            .putString("apellido", apellido)
            .putString("correoUam", correoUam)
            .putString("telefono", telefono)
            .apply()
    }

    fun obtenerIdUsuario(): Long =
        prefs.getLong("idUsuario", 0L)

    fun obtenerNombre(): String =
        prefs.getString("nombre", "") ?: ""

    fun obtenerApellido(): String =
        prefs.getString("apellido", "") ?: ""

    fun obtenerCorreo(): String =
        prefs.getString("correoUam", "") ?: ""

    fun obtenerTelefono(): String =
        prefs.getString("telefono", "") ?: ""

    fun cerrarSesion() {
        prefs.edit().clear().apply()
    }
}