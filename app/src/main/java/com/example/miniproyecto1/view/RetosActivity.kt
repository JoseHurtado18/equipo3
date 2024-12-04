package com.example.miniproyecto1.view

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miniproyecto1.R
import com.example.miniproyecto1.databinding.RetosBinding
import com.example.miniproyecto1.model.Reto
import com.example.miniproyecto1.viewmodel.RetosViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RetosActivity : AppCompatActivity() {

    private lateinit var binding: RetosBinding
    private lateinit var retoAdapter: RetoAdapter
    private val retosViewModel: RetosViewModel by viewModels()
    //private val retos = mutableListOf<Reto>() // Lista mutable para los retos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.retos)

        viewModelObservers()

        val dialogView = layoutInflater.inflate(R.layout.dialog_add_reto, null)
        val saveButton = dialogView.findViewById<Button>(R.id.saveButton)

        retosViewModel.isGuardarButtonEnabled.observe(this) { isEnabled ->
            saveButton.isEnabled = isEnabled
            saveButton.apply {
                val backgroundColor = if (isEnabled) {
                    ContextCompat.getColor(context, R.color.verde)
                }else{
                    ContextCompat.getColor(context, R.color.gray)
                }
                saveButton.setBackgroundColor(backgroundColor)
            }

        }



        // Inicialización del adaptador con la lista de retos
        retoAdapter = RetoAdapter(emptyList(),
            onEditClick = { reto -> showEditRetoDialog(reto) },
            onDeleteClick = { reto -> showEliminarRetoDialog(reto)}
        )

        // Configuración del RecyclerView
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@RetosActivity)
            adapter = retoAdapter
        }

        retosViewModel.getRetos()

        binding.fab.setOnClickListener {
            showAddRetoDialog()
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun viewModelObservers(){
        observerRetos()
    }

    private fun observerRetos(){
        retosViewModel.retos.observe(this){ retos ->
            retoAdapter.setRetos(retos)
            retoAdapter.notifyDataSetChanged()
        }
    }

    // Mostrar diálogo para añadir un nuevo reto
    private fun showAddRetoDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_reto, null)
        val retoInput = dialogView.findViewById<TextInputEditText>(R.id.retoInput)
        val cancelButton = dialogView.findViewById<Button>(R.id.cancelButton)
        val saveButton = dialogView.findViewById<Button>(R.id.saveButton)

        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle(" ")
            .setView(dialogView)
            .create()

        // Configurar listener para el botón Cancelar
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        // Configurar listener para el botón Guardar
        saveButton.setOnClickListener {
            val retoText = retoInput.text.toString()
            if (retoText.isNotBlank()) {
                val nuevoReto = Reto(descripcion = retoText, id = "")
                addReto(nuevoReto)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "El reto no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    // Añadir un reto a la lista y notificar al adaptador
    private fun addReto(reto: Reto) {
        retosViewModel.addReto(reto)
        val currentList = retoAdapter.getRetos().toMutableList()
        currentList.add(0, reto)  // Agrega el reto al inicio
        retoAdapter.setRetos(currentList)  // Actualiza el adaptador
        retoAdapter.notifyItemInserted(0)  // Notifica que se insertó un item en la posición 0
        binding.recyclerView.scrollToPosition(0)
    }

    // Mostrar diálogo para editar un reto existente
    private fun showEditRetoDialog(reto: Reto) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_reto, null)
        val retoInput = dialogView.findViewById<TextInputEditText>(R.id.retoInput)
        val cancelButton = dialogView.findViewById<Button>(R.id.cancelButton)
        val saveButton = dialogView.findViewById<Button>(R.id.saveButton)

        retoInput.setText(reto.descripcion)

        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle(" ")
            .setView(dialogView)
            .create()

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        saveButton.setOnClickListener {
            val updatedText = retoInput.text.toString()
            if (updatedText.isNotBlank()) {
                updateReto(reto, updatedText)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "El reto no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    // Actualizar un reto existente
    private fun updateReto(reto: Reto, updatedText: String) {
        retosViewModel.updateReto(reto, updatedText)

        //val currentList = retoAdapter.getRetos().toMutableList()
        val position = retoAdapter.getRetos().indexOf(reto)
        val reto = retoAdapter.getRetos()[position]
        reto.descripcion = updatedText
        retoAdapter.notifyItemChanged(position)
    }


    private fun showEliminarRetoDialog(reto: Reto) {
        // Inflar el diseño del diálogo personalizado
        val dialogView = layoutInflater.inflate(R.layout.dialog_eliminar_reto, null)
        val tvReto = dialogView.findViewById<TextView>(R.id.tvReto)
        tvReto.text = reto.descripcion

        // Crear el diálogo
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialogView.findViewById<Button>(R.id.btnNo).setOnClickListener {
            dialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.btnSi).setOnClickListener {
            deleteReto(reto)
            dialog.dismiss()
        }

        dialog.show()
    }

    // Eliminar un reto de la lista y notificar al adaptador
    private fun deleteReto(reto: Reto) {
        retosViewModel.deleteReto(reto)

        val currentList = retoAdapter.getRetos().toMutableList()
        currentList.remove(reto)
        retoAdapter.setRetos(currentList)
        val position = currentList.indexOf(reto)
        if (position != -1) {
            retoAdapter.notifyItemRemoved(position)
        }

        binding.recyclerView.scrollToPosition(0)
    }
}
