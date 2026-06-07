package ni.edu.uam.finduam.network

import ni.edu.uam.finduam.model.LoginRequest
import ni.edu.uam.finduam.model.UsuarioRequest
import ni.edu.uam.finduam.model.UsuarioResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ni.edu.uam.finduam.model.ObjetoRequest
import ni.edu.uam.finduam.model.ObjetoResponse
import retrofit2.http.GET

interface ApiService {

        @POST("api/usuarios")
        suspend fun registrarUsuario(
            @Body usuario: UsuarioRequest
        ): Response<UsuarioRequest>

        @POST("api/usuarios/login")
        suspend fun login(
            @Body request: LoginRequest
        ): Response<UsuarioResponse>

        @POST("api/objetos")
        suspend fun publicarObjeto(
        @Body objeto: ObjetoRequest
        ): Response<ObjetoRequest>

        @GET("api/objetos")
        suspend fun obtenerObjetos(
        ): Response<List<ObjetoResponse>>
    }

