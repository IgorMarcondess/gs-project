package com.example.gsmobile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.gsmobile.databinding.ItemTomadaBinding
import com.example.gsmobile.network.InformationAPI
import com.example.gsmobile.network.Tomadas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class TomadaAdapter(
    private val context: Context,
    private val api: InformationAPI,
    private val onExcluirClick: (Tomadas) -> Unit,
    private val onEditarClick: (Tomadas) -> Unit
) : RecyclerView.Adapter<TomadaAdapter.ViewHolder>() {

    private lateinit var tomadasList: List<Tomadas>

    init {
        fetchTomadas()
    }

    inner class ViewHolder(private val binding: ItemTomadaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tomada: Tomadas) {
            binding.nomeEquipamento.text = tomada.nomeTomada
            binding.voltagemEquipamento.text = "${tomada.voltagem} Voltz"
            binding.codigoEquipamento.text = "ID: ${tomada.idTomada}"
            binding.excluirButton.setOnClickListener {
                onExcluirClick(tomada)
            }
            binding.editarButton.setOnClickListener {
                onEditarClick(tomada)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTomadaBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = tomadasList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tomadasList[position])
    }

    private fun fetchTomadas() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.getTomadas()
                withContext(Dispatchers.Main) {
                    tomadasList = response
                    notifyDataSetChanged()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "erro ao carregar os dados", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
