package com.example.gsmobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.gsmobile.databinding.FragmentHistoricoBinding
import com.example.gsmobile.network.InformationService
import com.example.gsmobile.network.Tomadas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class historicoFragment : Fragment() {

    private var _binding: FragmentHistoricoBinding? = null
    private val binding get() = _binding!!
    private lateinit var service: InformationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val httpClient = OkHttpClient.Builder().build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoricoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadTomadas()
    }

    private fun loadTomadas() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val tomadas = service.getAllTomadas()
                lifecycleScope.launch(Dispatchers.Main) {
                    renderTomadas(tomadas)
                }
            } catch (e: Exception) {
                lifecycleScope.launch(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Erro ao carregar tomadas: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun renderTomadas(tomadas: List<Tomadas>) {
        binding.historicoCard.removeAllViews()

        tomadas.forEach { tomada ->
            val cardView = LayoutInflater.from(requireContext())
                .inflate(R.layout.fragment_historico, binding.historicoCard, false)

            val nomeEquipamento = cardView.findViewById<TextView>(R.id.nomeEquipamento)
            val voltagemEquipamento = cardView.findViewById<TextView>(R.id.voltagemEquipamento)
            val codigoEquipamento = cardView.findViewById<TextView>(R.id.codigoEquipamento)
            val excluirButton = cardView.findViewById<Button>(R.id.excluirButton)
            val editarButton = cardView.findViewById<Button>(R.id.editarButton)

            nomeEquipamento.text = tomada.nomeTomada
            voltagemEquipamento.text = "${tomada.voltagem} Voltz"
            codigoEquipamento.text = "ID: ${tomada.idTomada}"

            excluirButton.setOnClickListener {
                Toast.makeText(requireContext(), "Excluir: ${tomada.nomeTomada}", Toast.LENGTH_SHORT).show()
            }

            editarButton.setOnClickListener {
                Toast.makeText(requireContext(), "Editar: ${tomada.nomeTomada}", Toast.LENGTH_SHORT).show()
            }

            binding.historicoCard.addView(cardView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
