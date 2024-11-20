package com.example.gsmobile.network

import com.example.gsmobile.TomadaFragment
import com.example.gsmobile.historicoFragment
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


data class Information(
    val nomeTomada: String,
    val voltagem: String
)
data class Tomadas(
    val idTomada: Int,
    val nomeTomada: String,
    val diaContagem: String?,
    val qtdGasta: Int?,
    val tarifaEletricidade: Int?,
    val voltagem: String
)
interface InformationAPI {

    @GET("tomada/todas")
    suspend fun getTomadas() : List<Tomadas>

    @POST("tomada/criar?cpfUser=92712116003")
    suspend fun sendInformation(@Body request: Information): TomadaFragment
}