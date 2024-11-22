package com.example.gsmobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.gsmobile.databinding.FragmentInicioBinding
import com.example.gsmobile.databinding.FragmentTomadaBinding

class InicioFragment : Fragment() {

    private var _binding: FragmentInicioBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cadastroButton.setOnClickListener {
            findNavController().navigate(R.id.action_inicioFragment_to_tomadaFragment)
        }

        binding.editButton.setOnClickListener{
            findNavController().navigate(R.id.action_inicioFragment_to_editFragment2)
        }
        binding.equipamentoButton.setOnClickListener{
            findNavController().navigate(R.id.action_inicioFragment_to_historicoFragment)
        }

    }
}