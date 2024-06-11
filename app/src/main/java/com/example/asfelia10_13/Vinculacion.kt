package com.example.asfelia10_13

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
//tipo de auntenticacion basica ya que solo se requiere el correo y la contraseña
enum class ProviderType {
    BASIC
}

class Vinculacion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vinculacion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //recuperar datos
        val bundle: Bundle? = intent.extras
        //pueden ser nulos
        val email: String? = bundle?.getString("email")
        val provider: String? = bundle?.getString("provider")
        //y si no existen enviar un String vacio
        setup(email ?: "", provider: provider ?:"")

    }
    //agarrar los datos que nos da y mostrarlos
    private fun setup(email: String, provider: ProviderType) {//mostrar el email y el tipo de auntenticacion
        EmailTextView.text = email //cambiar el texto a email
        ProviderTextView.text = provider //cambiar el texto a el tipo de auntenticacion

        //cerrar sesion
        Cerrar_sesion.setOnClickListener {
            FirebaseAuth.getInstance().signOut()//cerrar sesion en la base de datos
            onBackPressed()//devolvernos a la pantalla anterior
        }
    }
}