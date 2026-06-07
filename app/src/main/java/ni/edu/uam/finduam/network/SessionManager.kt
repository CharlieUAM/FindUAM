package ni.edu.uam.finduam.network

import android.content.Context

class SessionManager(context: Context) {

    private val prefs =
        context.getSharedPreferences(
            "finduam_session",
            Context.MODE_PRIVATE
        )

    fun guardarUsuario(
        idUsuario: Int,
        nombre: String,
        apellido: String,
        correoUam: String,
        telefono: String
    ) {
        prefs.edit()
            .putInt("idUsuario", idUsuario)
            .putString("nombre", nombre)
            .putString("apellido", apellido)
            .putString("correoUam", correoUam)
            .putString("telefono", telefono)
            .apply()
    }

    fun obtenerIdUsuario() =
        prefs.getInt("idUsuario", 0)

    fun obtenerNombre() =
        prefs.getString("nombre", "") ?: ""

    fun obtenerApellido() =
        prefs.getString("apellido", "") ?: ""

    fun obtenerCorreo() =
        prefs.getString("correoUam", "") ?: ""

    fun obtenerTelefono() =
        prefs.getString("telefono", "") ?: ""

    fun cerrarSesion() {
        prefs.edit().clear().apply()
    }
}