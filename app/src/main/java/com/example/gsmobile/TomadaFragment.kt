package com.example.gsmobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gsmobile.databinding.FragmentTomadaBinding
import com.example.gsmobile.network.InformationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class TomadaFragment : Fragment() {

    private var _binding: FragmentTomadaBinding? = null
    private val binding get() = _binding!!
    private lateinit var service: InformationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //configuração do OKHTTP ( MOSTRA LOG DOQ ESTA ACONTECENDO COM A MINHA REQUISIÇÃO )
        val httpClient = OkHttpClient.Builder().build()
        service = InformationService(httpClient, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTomadaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.enviarButton.setOnClickListener {
            sendInformation()
        }

    }

    private fun sendInformation() {
        val nome = binding.nomeEquipamentoEditText.text.toString().trim()
        val voltagemText = binding.voltagemEditText.text.toString().trim()


        if (nome.isEmpty() || voltagemText.isEmpty()) {
            Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            return
        }


//                val voltagem = voltagemText.toIntOrNull()
//                if (voltagem == null) {
//                    Toast.makeText(requireContext(), "Voltagem deve ser um número válido!", Toast.LENGTH_SHORT).show()
//                    return
//                }


        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.sendInformation(nome, voltagemText)
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(requireContext(), "Informações enviadas com sucesso!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(requireContext(), "Erro ao enviar informações: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
