package ni.edu.uam.finduam.network

import ni.edu.uam.finduam.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // Endpoints para Usuarios
    @POST("api/usuarios/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<UsuarioResponse>

    @GET("api/usuarios")
    suspend fun obtenerUsuarios(): Response<List<Usuario>>

    @GET("api/usuarios/{id}")
    suspend fun obtenerUsuarioPorId(@Path("id") id: Long): Response<Usuario>

    @POST("api/usuarios")
    suspend fun registrarUsuario(
        @Body usuario: UsuarioRequest
    ): Response<UsuarioResponse>

    @PUT("api/usuarios/{id}")
    suspend fun actualizarUsuario(
        @Path("id") id: Long,
        @Body usuario: Usuario
    ): Response<UsuarioResponse>

    @DELETE("api/usuarios/{id}")
    suspend fun eliminarUsuario(@Path("id") id: Long): Response<Unit>

    // Endpoints para Publicaciones (Objetos)
    @GET("api/publicaciones")
    suspend fun obtenerPublicaciones(): Response<List<ObjetoResponse>>

    @GET("api/publicaciones/{id}")
    suspend fun obtenerPublicacionPorId(@Path("id") id: Long): Response<ObjetoResponse>

    @POST("api/publicaciones")
    suspend fun publicarObjeto(
        @Body objeto: ObjetoRequest
    ): Response<ObjetoResponse>

    @PUT("api/publicaciones/{id}")
    suspend fun actualizarPublicacion(
        @Path("id") id: Long,
        @Body objeto: ObjetoRequest
    ): Response<ObjetoResponse>

    @DELETE("api/publicaciones/{id}")
    suspend fun eliminarPublicacion(@Path("id") id: Long): Response<Unit>
}
