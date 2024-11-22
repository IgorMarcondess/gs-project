package com.example.gsmobile.network

import com.example.gsmobile.TomadaFragment
import retrofit2.http.*

data class Information(
    val nomeTomada: String,
    val voltagem: String
)

data class Tomadas(
    val idTomada: Int,
    val nomeTomada: String,
    val voltagem: String
)

interface InformationAPI {

    @GET("tomada/todas")
    suspend fun getTomadas(): List<Tomadas>

    @POST("tomada/criar?cpfUser=92712116003")
    suspend fun sendInformation(@Body request: Information): TomadaFragment

    @DELETE("tomada/deletar/{id}")
    suspend fun deleteTomada(@Path("id") id: Int): TomadaFragment

    @PUT("/tomada/atualizar/{id}")
    suspend fun updateTomada(
        @Path("id") id: Int,
        @Body request: Information
    ): TomadaFragment

}
