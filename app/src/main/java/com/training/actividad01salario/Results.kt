package com.training.actividad01salario

import android.content.Intent
import android.icu.number.FormattedNumber
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Results : AppCompatActivity() {

    private lateinit var viewSalario : TextView
    private lateinit var viewSalMes : TextView
    private lateinit var viewIRPF : TextView
    private lateinit var viewDeduc : TextView
    private lateinit var btnReset : CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_results)

        initComponents()
        initListeners()
        initUI()
    }

    private fun initComponents(){
        this.viewSalario = findViewById(R.id.viewSalarioRes)
        this.viewSalMes = findViewById(R.id.viewSalMesRes)
        this.viewIRPF = findViewById(R.id.viewIRPFRes)
        this.viewDeduc = findViewById(R.id.viewDeducRes)
        this.btnReset = findViewById(R.id.btnReset)
    }

    private fun initListeners(){
        this.btnReset.setOnClickListener(){
            val int = Intent(this, MainActivity::class.java)
            startActivity(int)
        }
    }

    private fun initUI(){

        val salarioBruto = intent.extras?.getString("salarioBruto").orEmpty()
        val salarioNeto = intent.extras?.getString("salarioNeto").orEmpty()
        val retencionIrpf = intent.extras?.getString("retencionIrpf").orEmpty()
        val deducciones = intent.extras?.getString("deducciones").orEmpty()
        val salarioMensual = intent.extras?.getString("salarioMensual").orEmpty()

        viewSalario.text = "$salarioNeto €"
        viewSalMes.text = "$salarioMensual €"
        viewIRPF.text = "$retencionIrpf %"
        viewDeduc.text = "$deducciones %"
    }
}