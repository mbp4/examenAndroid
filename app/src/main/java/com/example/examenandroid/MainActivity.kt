package com.example.examenandroid

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var textRecordatorio: EditText
    private lateinit var botonImagen: ImageButton
    private lateinit var lista: ListView
    private lateinit var btnPendientes: Button
    private lateinit var btnHechas: Button
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var recordatoriosLista: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        textRecordatorio = findViewById(R.id.textRecordatorio)
        botonImagen = findViewById(R.id.imageButton)
        lista = findViewById(R.id.lista)
        btnPendientes = findViewById(R.id.Pendientes)
        btnHechas = findViewById(R.id.Hechas)
        recordatoriosLista = ArrayList()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, recordatoriosLista)
        lista.adapter = adapter

        botonImagen.setOnClickListener {
            añadirRecordatorio()
        }

        btnPendientes.setOnClickListener {
            mostrarPendientes()
        }

        btnHechas.setOnClickListener {
            mostrarHechas()
        }

    }

    private fun mostrarHechas() {

    }

    private fun mostrarPendientes() {

    }

    private fun añadirRecordatorio() {
        var recordatorio = textRecordatorio.text.toString()

        if (recordatorio.isNotEmpty()) {

            recordatoriosLista.add(recordatorio)
            adapter.notifyDataSetChanged()
            textRecordatorio.setText("")
            Toast.makeText(this, "Recordatorio añadido", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error al introducir el recordatorio", Toast.LENGTH_SHORT).show()
        }
    }
}
