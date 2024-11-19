package com.example.gsmobile.network

import com.example.gsmobile.TomadaFragment
import retrofit2.http.Body
import retrofit2.http.POST


data class Information(
    val nome: String,
    val voltagem: Int
)
interface InformationAPI {

    @POST("tomada/criar?cpfUser=92712116003")
    suspend fun sendInformation(@Body request: Information): TomadaFragment
}