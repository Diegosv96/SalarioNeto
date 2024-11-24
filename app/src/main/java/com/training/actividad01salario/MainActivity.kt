package com.training.actividad01salario

import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var viewSalario : EditText
    private lateinit var btn12 : Button
    private lateinit var btn14 : Button
    private lateinit var viewEdad : EditText
    private lateinit var viewHijos : EditText
    private lateinit var viewGrupo : EditText
    private lateinit var viewDisc : EditText
    private lateinit var viewEstado : EditText
    private lateinit var btnPlay : CardView

    private var pagas12on : Boolean = true
    private var pagas14on : Boolean = false
    private var salario : Int = 0
    private var edad : Int = 30
    private var grupo : String = "1"
    private var disc : Int = 0
    private var estado : String = ""
    private var pagas : Int = 12
    private var hijos : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initComponents()
        initListeners()
        initUI()

    }

    private fun initComponents(){
        this.viewSalario = findViewById(R.id.viewSalario)
        this.btn12 = findViewById(R.id.btnOption12)
        this.btn14 = findViewById(R.id.btnOption14)
        this.viewEdad = findViewById(R.id.viewEdad)
        this.viewHijos = findViewById(R.id.viewHijos)
        this.viewGrupo = findViewById(R.id.viewGrupo)
        this.viewDisc = findViewById(R.id.viewDisc)
        this.viewEstado = findViewById(R.id.viewEstado)
        this.btnPlay = findViewById(R.id.btnPlay)
    }

    private fun initListeners(){
        this.btn12.setOnClickListener(){
            if(!this.pagas12on){
                this.pagas12on = true
                this.pagas14on = false
                setButtonsPagas()
            }
        }
        this.btn14.setOnClickListener(){
            if(!this.pagas14on){
                this.pagas12on = false
                this.pagas14on = true
                setButtonsPagas()
            }
        }
        this.btnPlay.setOnClickListener(){
            calcular()
        }
    }

    private fun initUI(){
        setButtonsPagas()
    }

    private fun setButtonsPagas(){
        val colorOff = ContextCompat.getColor(this, R.color.custom4)
        val colorOn = ContextCompat.getColor(this, R.color.custom1)
        if (this.pagas12on && !this.pagas14on){
            btn12.setBackgroundColor(colorOn)
            btn14.setBackgroundColor(colorOff)
        } else if (!this.pagas12on && this.pagas14on){
            btn12.setBackgroundColor(colorOff)
            btn14.setBackgroundColor(colorOn)
        }
    }

    private fun calcularRetencion(grupo: String): Double {
        val baseRetencion = when (grupo) {
            "1" -> 0.20
            "2" -> 0.15
            "3" -> 0.10
            else -> 0.12
        }
        return baseRetencion
    }

    private fun calcularDeducciones(edad: Int, grado: Int, estado: String, hijos: Int): Double {
        var reduccion = 0.0

        if (grado > 33) {
            reduccion += 0.05
        }

        if (estado.equals("casado", ignoreCase = true)) {
            reduccion += 0.03
        }

        reduccion += hijos * 0.01

        if (edad > 65) {
            reduccion += 0.02
        }

        return reduccion
    }

    private fun calcularSalarioNeto(salario: Int, retencion: Double, deducciones: Double): Double {
        val retencionFinal = (salario * (retencion - deducciones)).coerceAtLeast(0.0)
        return salario - retencionFinal
    }

    private fun calcular(){
        salario = this.viewSalario.text.toString().toIntOrNull() ?: 0
        if(this.pagas12on){
            pagas = 12
        }else{
            pagas = 14
        }
        edad = this.viewEdad.text.toString().toIntOrNull() ?: 0
        grupo = this.viewGrupo.text.toString()
        disc = this.viewDisc.text.toString().toIntOrNull() ?: 0
        estado = this.viewEstado.text.toString()
        hijos = this.viewHijos.text.toString().toIntOrNull() ?: 0

        val retencion = calcularRetencion(grupo)
        val deducciones = calcularDeducciones(edad, disc, estado, hijos)
        val salarioNeto = calcularSalarioNeto(salario, retencion, deducciones)
        val salarioMensual = salarioNeto/pagas

        val decimalFormat = DecimalFormat("#.##")
        val salarioFormat = decimalFormat.format(salario)
        val salarioNetoFormat = decimalFormat.format(salarioNeto)
        val salarioMensualFormat = decimalFormat.format(salarioMensual)

        val intent = Intent(this, Results::class.java)
        intent.putExtra("salarioBruto", salarioFormat.toString())
        intent.putExtra("salarioNeto", salarioNetoFormat.toString())
        intent.putExtra("retencionIrpf", (retencion * 100).toString())
        intent.putExtra("deducciones", (deducciones * 100).toString())
        intent.putExtra("salarioMensual", salarioMensualFormat.toString())

        startActivity(intent)
    }
}