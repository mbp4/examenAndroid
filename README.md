# examenAndroid
 
link al repositorio: https://github.com/mbp4/examenAndroid.git

## Descripción 

En este proyecto se nos pedía realizar una pantalla que mostrará mediante un ListView una lista de recordatorios.

### Actividad principal

Aquí tendremos toda la lógica del programa, su pseudocódigo sería:

```

Clase MainActivity:
    Variables privadas:
        textRecordatorio: EditText
        botonImagen: ImageButton
        lista: ListView
        btnPendientes: Button
        btnHechas: Button
        adapter: ArrayAdapter<String>
        recordatoriosLista: ArrayList<String>
        tareasPendientes: ArrayList<String>
        tareasHechas: ArrayList<String>
        pendientes: Boolean
        btnIdioma: ToggleButton

    Método onCreate(savedInstanceState):
        Llamar a super.onCreate(savedInstanceState)
        Establecer contenido de vista a R.layout.activity_principal

        preferences = obtener preferencias "ajustes"
        language = preferences.getString("idioma", "es")
        llamar cambiarIdioma(language)

        Inicializar vistas (textRecordatorio, botonImagen, lista, btnPendientes, btnHechas, btnIdioma)

        Inicializar recordatoriosLista, tareasPendientes, tareasHechas como ArrayList

        adapter = crear nuevo ArrayAdapter con recordatoriosLista
        lista.setAdapter(adapter)

        Registrar lista para menú contextual

        botonImagen.setOnClickListener:
            llamar añadirRecordatorio()

        btnPendientes.setOnClickListener:
            pendientes = true
            llamar actualizarLista()

        btnHechas.setOnClickListener:
            pendientes = false
            llamar actualizarLista()

        btnIdioma.setOnClickListener:
            Si btnIdioma.isChecked:
                llamar cambiarIdioma("es")
            Sino:
                llamar cambiarIdioma("en")

    Método cambiarIdioma(idioma):
        currentLanguage = obtener idioma actual
        Si currentLanguage != idioma:
            Crear nuevo locale con idioma
            Establecer locale por defecto
            Actualizar configuración de recursos

            preferences = obtener preferencias "ajustes"
            guardar idioma en preferencias

            recrear actividad

    Método actualizarLista():
        Limpiar recordatoriosLista
        Si pendientes:
            Añadir tareasPendientes a recordatoriosLista
        Sino:
            Añadir tareasHechas a recordatoriosLista
        Notificar cambios en adapter

    Método onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo)
        Inflar menú desde "context_menu"
        
        Obtener información del elemento seleccionado en la lista
        Cambiar el color de fondo del elemento seleccionado a "colorFondo1"

    Método onContextItemSelected(item: MenuItem): Boolean
        Obtener información de posición del elemento seleccionado en lista
        Obtener referencia de vista seleccionada
        Definir color de fondo "noSeleccionado" como blanco
        
        Si el item seleccionado es "action_delete":
            Remover elemento de recordatoriosLista en la posición seleccionada
            Actualizar adaptador
            Remover tarea de tareasPendientes
            Mostrar mensaje "tarea eliminada"
            Cambiar fondo de vista seleccionada a color "noSeleccionado"
            Devolver true
            
        Si el item seleccionado es "action_done":
            Marcar tarea como "Hecho" en la lista
            Actualizar adaptador
            Remover tarea de tareasPendientes y añadir a tareasHechas
            Mostrar mensaje "tarea marcada como hecha"
            Cambiar fondo de vista seleccionada a color "noSeleccionado"
            Devolver true
            
        Para cualquier otra opción, restablecer color de fondo a "noSeleccionado" y devolver resultado de super.onContextItemSelected(item)

    Método añadirRecordatorio():
        recordatorio = obtener texto de textRecordatorio

        Si recordatorio no está vacío:
            Añadir recordatorio a recordatoriosLista
            Notificar cambios en adapter
            Limpiar textRecordatorio
            Añadir recordatorio a tareasPendientes
            Mostrar mensaje "Recordatorio añadido"
        Sino:
            Mostrar mensaje "Error al introducir el recordatorio"

```

Esta activity se compone de varios TextEdit para que el usuario pueda introducir los datos necesarios, el ListView con todos los recordatorios y su correspondiente Menu Contextual (el cual aparece al dejar pulsado sobre un elemento) y dos botones:

  -> Botón que muestra las tareas pendientes. 

  -> Botón que muestra las tareas ya completadas.
