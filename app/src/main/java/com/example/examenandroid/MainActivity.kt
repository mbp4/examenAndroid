package com.example.examenandroid

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.ComponentActivity
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var textRecordatorio: EditText
    private lateinit var botonImagen: ImageButton
    private lateinit var lista: ListView
    private lateinit var btnPendientes: Button
    private lateinit var btnHechas: Button
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var recordatoriosLista: ArrayList<String>
    private lateinit var tareasPendientes: ArrayList<String>
    private lateinit var tareasHechas: ArrayList<String>
    private var pendientes: Boolean = true
    private lateinit var btnIdioma: ToggleButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        val preferences = getSharedPreferences("ajustes", MODE_PRIVATE)
        val language = preferences.getString("idioma", "es") ?: "es"
        cambiarIdioma(language)

        textRecordatorio = findViewById(R.id.textRecordatorio)
        botonImagen = findViewById(R.id.imageButton)
        lista = findViewById(R.id.lista)
        btnPendientes = findViewById(R.id.Pendientes)
        btnHechas = findViewById(R.id.Hechas)
        btnIdioma = findViewById(R.id.btnIdioma)

        recordatoriosLista = ArrayList()
        tareasPendientes = ArrayList()
        tareasHechas = ArrayList()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, recordatoriosLista)
        lista.adapter = adapter

        registerForContextMenu(lista)

        botonImagen.setOnClickListener { añadirRecordatorio() }
        btnPendientes.setOnClickListener { pendientes = true; actualizarLista() }
        btnHechas.setOnClickListener { pendientes = false; actualizarLista() }

        btnIdioma.setOnClickListener {
            if (btnIdioma.isChecked) cambiarIdioma("es") else cambiarIdioma("en")
        }

    }

    private fun cambiarIdioma(idioma: String) {
        val currentLanguage = resources.configuration.locales[0].language
        if (currentLanguage != idioma) {
            val locale = Locale(idioma)
            Locale.setDefault(locale)
            val config = Configuration(resources.configuration)
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)

            val preferences = getSharedPreferences("ajustes", MODE_PRIVATE)
            preferences.edit().putString("idioma", idioma).apply()

            recreate()
        }
    }



    private fun actualizarLista() {
            recordatoriosLista.clear()
            if (pendientes) {
                recordatoriosLista.addAll(tareasPendientes)
            } else {
                recordatoriosLista.addAll(tareasHechas)
            }
            adapter.notifyDataSetChanged()

    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.action_delete -> {
                val tarea = recordatoriosLista.removeAt(info.position)
                adapter.notifyDataSetChanged()
                tareasPendientes.remove(tarea)
                Toast.makeText(this, "$tarea eliminada", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_done -> {
                val tarea = recordatoriosLista[info.position]
                recordatoriosLista[info.position] = "$tarea (Hecho)"
                adapter.notifyDataSetChanged()
                tareasPendientes.remove(tarea)
                tareasHechas.add(tarea)
                Toast.makeText(this, "$tarea marcada como hecha", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun añadirRecordatorio() {
        var recordatorio = textRecordatorio.text.toString()

        if (recordatorio.isNotEmpty()) {

            recordatoriosLista.add(recordatorio)
            adapter.notifyDataSetChanged()
            textRecordatorio.setText("")
            tareasPendientes.add(recordatorio)
            Toast.makeText(this, "Recordatorio añadido", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error al introducir el recordatorio", Toast.LENGTH_SHORT).show()
        }
    }

}
