package com.example.ahorcado

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private var botones : ArrayList<Button>? = null
    private var timer : CountDownTimer? = null
    private var puntos : Int = 120
    private var hasPerdido : Boolean = false
    private var listaPalabras = mutableListOf(
            Palabra("Saturno", "Astronomía"),
            Palabra("Venus", "Astronomía"),
            Palabra("Gardenia", "Plantas"),
            Palabra("Espliego" , "Plantas"),
            Palabra("Camelia", "Plantas"),
            Palabra("Galaxia", "Astronomía"),
            Palabra("Baloncesto", "Deportes"),
            Palabra("Tenis" , "Deportes")
    )
    private var listaImagenes = arrayListOf(R.drawable.hangman1, R.drawable.hangman2, R.drawable.hangman3, R.drawable.hangman4, R.drawable.hangman5, R.drawable.hangman6)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        botones = arrayListOf<Button>(
                findViewById<Button>(R.id.btnA),
                findViewById<Button>(R.id.btnB),
                findViewById<Button>(R.id.btnC),
                findViewById<Button>(R.id.btnD),
                findViewById<Button>(R.id.btnE),
                findViewById<Button>(R.id.btnF),
                findViewById<Button>(R.id.btnG),
                findViewById<Button>(R.id.btnH),
                findViewById<Button>(R.id.btnI),
                findViewById<Button>(R.id.btnJ),
                findViewById<Button>(R.id.btnK),
                findViewById<Button>(R.id.btnL),
                findViewById<Button>(R.id.btnM),
                findViewById<Button>(R.id.btnN),
                findViewById<Button>(R.id.btnÑ),
                findViewById<Button>(R.id.btnO),
                findViewById<Button>(R.id.btnP),
                findViewById<Button>(R.id.btnQ),
                findViewById<Button>(R.id.btnR),
                findViewById<Button>(R.id.btnS),
                findViewById<Button>(R.id.btnT),
                findViewById<Button>(R.id.btnU),
                findViewById<Button>(R.id.btnV),
                findViewById<Button>(R.id.btnW),
                findViewById<Button>(R.id.btnX),
                findViewById<Button>(R.id.btnY),
                findViewById<Button>(R.id.btnZ)
        );
        
        val txtTema : TextView = findViewById(R.id.txtTema)
        val txtFraseOculta : TextView = findViewById(R.id.txtFraseOculta)
        val txtTiempo : TextView = findViewById(R.id.txtTiempo)
        val txtPuntuacion : TextView = findViewById(R.id.txtPuntuacion)
        val imgAhorcado : ImageView = findViewById(R.id.imgAhorcado)
        imgAhorcado.setImageResource(R.drawable.hangman0)

        var palabraOcultaChar : CharArray = charArrayOf()

        var contadorImagenMostradaAhorcado : Int = 0

        /* METODOS */

        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                puntos -= 1
                txtTiempo.text = java.lang.String.format(Locale.getDefault(), "%d", millisUntilFinished / 1000L)
            }

            override fun onFinish() {
                txtTiempo.text = "Tiempo."
                puntos = 0
                deshabilitarTodosBotones()
            }
        }.start()


        fun escribirPalabraEnView(palabraAhorcado : Palabra) {
            palabraAhorcado.palabra = palabraAhorcado.palabra.toLowerCase()
            val palabraChar = palabraAhorcado.palabra.toCharArray()
            var palabraOculta : String = ""

            for (i in palabraChar){
                palabraOculta += " _ "
            }

            palabraOcultaChar = palabraOculta.toCharArray()

            txtTema.text = palabraAhorcado.tema
            txtFraseOculta.text = palabraOculta
        }

        fun obtenerPalabra(listaPalabras : MutableList<Palabra>) : CharArray {
            val max : Int = listaPalabras.size-1
            val valor = (0..max).shuffled().first()
            val palabraAhorcado = listaPalabras[valor]
            escribirPalabraEnView(palabraAhorcado)
            return palabraAhorcado.palabra.toCharArray()
        }

        fun dibujarAhorcado() {
            if(contadorImagenMostradaAhorcado < listaImagenes.size){
                imgAhorcado.setImageResource(listaImagenes[contadorImagenMostradaAhorcado])
            }

            contadorImagenMostradaAhorcado++

            if(contadorImagenMostradaAhorcado == listaImagenes.size){
                Toast.makeText(this, "HAS PERDIDO", Toast.LENGTH_SHORT).show()
                hasPerdido = true
                deshabilitarTodosBotones()
                puntos = 0
                return
            }
        }

        fun comprobarLetra(letra : Char, caracteres : CharArray){
            var contador : Int = 1
            var oculta : String = ""
            var haAcertado : Boolean = false

            for (c in caracteres){
                if (c == letra){
                    haAcertado = true
                    palabraOcultaChar[contador] = letra

                    oculta = ""
                    for(i in palabraOcultaChar){
                        oculta = "$oculta$i"
                    }
                    txtFraseOculta.text = oculta
                }
                contador += 3
            }

            if (!haAcertado) {
                puntos -= 10
                dibujarAhorcado()
            } else if (oculta.indexOf("_") == -1) {
                Toast.makeText(this, "HAS GANADO", Toast.LENGTH_SHORT).show()
                deshabilitarTodosBotones()
            }
        }

        val caracteresPalabraAhorcado = obtenerPalabra(listaPalabras)

        for (boton in botones!!) {
            boton.setOnClickListener{
                val letra : Char = resources.getResourceName(boton.id).toCharArray().last().toLowerCase()
                comprobarLetra(letra, caracteresPalabraAhorcado)
                deshabilitaBoton(boton)
            }
        }
    }

    private fun deshabilitaBoton(button: Button) {
        button.isClickable = false
        button.setBackgroundColor(Color.GRAY)
    }

    private fun deshabilitarTodosBotones() {
        for (button in botones!!) {
            deshabilitaBoton(button)
        }
        timer?.cancel()

        if(hasPerdido) puntos = 0
        val txtPuntuacion : TextView = findViewById(R.id.txtPuntuacion)

        txtPuntuacion.setText("Tienes $puntos puntos.")
    }
}