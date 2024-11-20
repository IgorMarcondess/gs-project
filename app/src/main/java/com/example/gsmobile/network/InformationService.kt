package com.example.gsmobile.network

import androidx.fragment.app.Fragment
import com.example.gsmobile.TomadaFragment
import com.example.gsmobile.historicoFragment
import okhttp3.OkHttpClient



class InformationService(
    private val httpClient: OkHttpClient,
    private val TomadaFragment: TomadaFragment,
) {

    private val api: InformationAPI = NetworkUtils.retrofit.create(InformationAPI::class.java)

    suspend fun sendInformation(nomeTomada: String, voltagem: String): TomadaFragment {
        val request = Information(nomeTomada, voltagem)
        return api.sendInformation(request)
    }

    suspend fun getAllTomadas(): List<Tomadas> {
        return api.getTomadas()
    }
}