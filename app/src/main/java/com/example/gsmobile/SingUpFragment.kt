package com.example.gsmobile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gsmobile.databinding.FragmentSingUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SingUpFragment : Fragment() {

    private var _binding: FragmentSingUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSingUpBinding.inflate(inflater, container, false)

        binding.nextButton.setOnClickListener {
            val nome = binding.nomeEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val senha = binding.passwordEditText.text.toString().trim()
            val telefone = binding.phoneEditText.text.toString().trim()

            if (!emailValido(email)) {
                Toast.makeText(context, "Email no formato incorreto.", Toast.LENGTH_SHORT).show()
            } else if (!nomeValido(nome)) {
                Toast.makeText(context, "Nome no formato incorreto.", Toast.LENGTH_SHORT).show()
            } else if (senha.isEmpty()) {
                Toast.makeText(context, "Senha não pode ser vazia.", Toast.LENGTH_SHORT).show()
            } else if (!telefoneValido(telefone)) {
                Toast.makeText(context, "Somente números são permitidos no telefone.", Toast.LENGTH_SHORT).show()
            } else {

                registerUser(nome, email, senha, telefone)
            }
        }

        return binding.root
    }


    private fun registerUser(nome: String, email: String, senha: String, telefone: String) {
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        saveUserData(userId, nome, email, telefone)
                    }
                    Toast.makeText(context, "Cadastro bem-sucedido", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_homeFragment_to_singUpFragment)
                } else {
                    Toast.makeText(context, "Erro ao realizar cadastro: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    Log.e("SignUpFragment", "Erro ao realizar cadastro de usuário", task.exception)
                }
            }
    }

    private fun saveUserData(userId: String, nome: String, email: String, telefone: String) {
        val userMap = mapOf(
            "Nome" to nome,
            "E-mail" to email,
            "Telefone" to telefone
        )
        database.getReference("Usuario cadastrados").child(userId).setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i("SignUpFragment", "Dados do usuário salvos no banco de dados")
                    Toast.makeText(context, "Dados salvos com sucesso no banco de dados!", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("SignUpFragment", "Erro ao salvar dados no Banco de dados", task.exception)
                    Toast.makeText(context, "Erro ao salvar dados: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun emailValido(email: String): Boolean {
        return email.contains("@") && email.contains(".com") && email.isNotEmpty()
    }

    private fun nomeValido(nome: String): Boolean {
        return nome.isNotEmpty() && nome.length >= 3 && nome.all { it.isLetter() || it.isWhitespace() }
    }

    private fun telefoneValido(telefone: String): Boolean {
        return telefone.all { it.isDigit() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
