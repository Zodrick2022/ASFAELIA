package com.example.asfelia10_13

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase

class Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setup()
    }
    private fun setup() {
        Registrar_boton.setOnClickListener {//ingresar al boton de registrar
            if (editTextText.text.isnotEmpty() && editTextTextPassword.text.isNotEmpty()){//verificar si los 2 textos no estan vacios
                //registrar usuario
                Firebase.getInstance().createUserWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString()).addOnCompleteListener {//convertir los textos en String y agregar una alerta de para verificar si la operacion de registro se ha echo satisfgactoriamente o no
                    if (it.isSuccessful){//finalizo correctamente
                        showVinculacion(email: it.result?.user?.email ?:"", ProviderType.BASIC)//si pasa la verificacion mostrar la pantalla de vinculacion
                    }
                    else{//no finalizo correctamente
                        showAlert()
                    }
                }
            }
        }
        Acceder_boton.setOnClickListener {//ingresar al boton de acceder
            if (editTextText.text.isnotEmpty() && editTextTextPassword.text.isNotEmpty()){
                //en ves de llamar la funcion createUserWithEmailAndPassword se llama la funcion signInWithEmailAndPassword para iniciar sesion con una cuenta ya existente
                Firebase.getInstance().signInWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        showVinculacion(email: it.result?.user?.email ?:"", ProviderType.BASIC)
                    }
                    else{
                        showAlert()
                    }
                }
            }
        }
    }

    //mostrar la alerta
    private fun showAlert() {
        val builder = AlertDialog.Builder(this)//tomar la alerta
        builder.setTitle("Error")//constrir el titulo
        builder.setMessage("Se ha producido un error autenticando al usuario")//constrir el mensaje
        builder.setPositiveButton("Aceptar", null)//agregar el boton de aceptar
        val dialog: AlertDialog = builder.create() //crear
        dialog.show()//mostrar
    }

    //navegar hacia la pantalla de vinculacion
    private fun showVinculacion(email: String, provider: ProviderType) {
        val intent = Intent(this, Vinculacion::class.java).apply{ //navegar hacia la pantalla de vinculacion
            putExtra(name: "email", email)//pasar el correo a la pantalla de vinculacion
            putExtra(name: "provider", provider.name)//pasar el provider a la pantalla de vinculacion
        }
        startActivity(intent)//activar la pantalla de vinculacion
    }
}