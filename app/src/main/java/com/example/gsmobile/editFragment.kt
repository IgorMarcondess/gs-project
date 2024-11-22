package com.example.gsmobile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.gsmobile.databinding.FragmentEditBinding
import com.example.gsmobile.network.InformationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient


class editFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var informationService: InformationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val httpClient = OkHttpClient.Builder().build()
        informationService = InformationService(httpClient)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)

        binding.editarButton.setOnClickListener {
            editarTomada()
        }

        binding.voltarButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    private fun editarTomada() {
        val idText = binding.idEquipamentoEditText.text.toString().trim()
        val nome = binding.nomeEquipamentoEditText.text.toString().trim()
        val voltagem = binding.voltagemEditText.text.toString().trim()

        if (idText.isEmpty() || nome.isEmpty() || voltagem.isEmpty()) {
            Toast.makeText(context, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            return
        }

        val idTomada = idText.toIntOrNull()
        if (idTomada == null) {
            Toast.makeText(context, "ID inválido.", Toast.LENGTH_SHORT).show()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                informationService.updateTomada(idTomada, nome, voltagem)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Tomada atualizada com sucesso!", Toast.LENGTH_SHORT).show()
                    requireActivity().onBackPressed()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("EditFragment", "Erro ao atualizar tomada", e)
                    Toast.makeText(context, "Erro ao atualizar: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}