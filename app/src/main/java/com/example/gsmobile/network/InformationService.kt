package com.example.gsmobile.network

import com.example.gsmobile.TomadaFragment
import okhttp3.OkHttpClient

class InformationService(
    private val httpClient: OkHttpClient
) {
    private val api: InformationAPI = NetworkUtils.retrofit.create(InformationAPI::class.java)

    // Criar uma nova tomada
    suspend fun sendInformation(nomeTomada: String, voltagem: String): TomadaFragment {
        val request = Information(nomeTomada, voltagem)
        return api.sendInformation(request)
    }

    // Buscar todas as tomadas
    suspend fun getAllTomadas(): List<Tomadas> {
        return api.getTomadas()
    }

    // Deletar uma tomada pelo ID
    suspend fun deleteTomada(id: Int) {
        api.deleteTomada(id)
    }

    // Editar uma tomada pelo ID
    suspend fun updateTomada(id: Int, nomeTomada: String, voltagem: String) {
        val request = Information(nomeTomada, voltagem)
        api.updateTomada(id, request)
    }
}
