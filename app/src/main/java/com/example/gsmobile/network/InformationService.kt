package com.example.gsmobile.network

import com.example.gsmobile.TomadaFragment
import okhttp3.OkHttpClient

class InformationService(
    private val httpClient: OkHttpClient
) {
    private val api: InformationAPI = NetworkUtils.retrofit.create(InformationAPI::class.java)

    suspend fun sendInformation(nomeTomada: String, voltagem: String): TomadaFragment {
        val request = Information(nomeTomada, voltagem)
        return api.sendInformation(request)
    }

    suspend fun getAllTomadas(): List<Tomadas> {
        return api.getTomadas()
    }

    suspend fun deleteTomada(id: Int) {
        api.deleteTomada(id)
    }

    suspend fun updateTomada(id: Int, nomeTomada: String, voltagem: String) {
        val request = Information(nomeTomada, voltagem)
        api.updateTomada(id, request)
    }
}
