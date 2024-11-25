package com.example.gsmobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gsmobile.databinding.FragmentHistoricoBinding
import com.example.gsmobile.network.InformationAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class historicoFragment : Fragment() {
    private var _binding: FragmentHistoricoBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: TomadaAdapter
    private lateinit var api: InformationAPI


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://40.90.198.227:80/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(InformationAPI::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoricoBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = TomadaAdapter(
            requireContext(),
            api,
            onExcluirClick = { tomada ->
                Toast.makeText(
                    requireContext(),
                    "Excluir: ${tomada.nomeTomada}",
                    Toast.LENGTH_SHORT
                ).show()
            },
            onEditarClick = { tomada ->
                Toast.makeText(
                    requireContext(),
                    "Editar: ${tomada.nomeTomada}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        binding.recyclerViewTomadas.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTomadas.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}