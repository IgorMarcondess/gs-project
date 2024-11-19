package com.example.gsmobile.network

import com.example.gsmobile.TomadaFragment
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class InformationService(
    private val httpClient: OkHttpClient,
    private val TomadaFragment: TomadaFragment
) {

    private val api: InformationAPI = NetworkUtils.retrofit.create(InformationAPI::class.java)

    suspend fun sendInformation(nome: String, voltagem: String): TomadaFragment {
        val request = Information(nome, voltagem)
        return api.sendInformation(request)
    }
}