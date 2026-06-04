package ni.edu.uam.finduam.network

import ni.edu.uam.finduam.model.UsuarioRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/usuarios")
    suspend fun registrarUsuario(
        @Body usuario: UsuarioRequest
    ): Response<UsuarioRequest>
}