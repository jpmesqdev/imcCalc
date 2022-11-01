package com.devdroid.imccalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.devdroid.imccalc.model.Calc
import kotlin.math.pow

class ImcActivity : AppCompatActivity() {

    lateinit var height: EditText
    lateinit var weight: EditText
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        weight = findViewById(R.id.imc_weight)
        height = findViewById(R.id.imc_height)
        button = findViewById(R.id.imc_button)

        button.setOnClickListener {

            if (!validateInput(weight, height)) {
                Toast.makeText(this, R.string.imc_form_error, Toast.LENGTH_SHORT).show()
            } else {
                val result = calculateImc(weight, height)
                val resultString = "%.2f".format(result)
                val resultMessage = getMessage(result)

                AlertDialog.Builder(this)
                    .setTitle(resultString)
                    .setMessage(resultMessage)
                    .setPositiveButton("Ok") { _, _ ->  }
                    .setNegativeButton("Salvar") { _, _ ->
                        Thread {
                            val app = application as App
                            val dao = app.db.calcDao()
                            dao.insert(Calc(type = "imc", res = result))

                            runOnUiThread {
                                Toast.makeText(this@ImcActivity, "Salvo com sucesso!", Toast.LENGTH_LONG).show()
                            }

                        }.start()

                    }
                    .create()
                    .show()
                }
            }



    }

    @StringRes
    private fun getMessage(value: Double): Int {
        return when {
            value < 15.5 -> R.string.imc_level1
            value > 18.5 && value < 24.9 -> R.string.imc_level2
            value > 25.0 && value < 29.9 -> R.string.imc_level3
            value > 30.0 && value < 39.9 -> R.string.imc_level4
            else -> R.string.imc_level5
        }
    }

    private fun calculateImc(weight: EditText, height: EditText): Double {
        val w = weight.text.toString().toDouble()
        val h = height.text.toString().toDouble()
        return w / h.div(100).pow(2.0)
    }

    private fun validateInput(input1: EditText, input2: EditText): Boolean {
        val filtered1 = input1.text.toString()
        val filtered2 = input2.text.toString()
        return filtered1 != "0"  && filtered1.isNotEmpty() && !filtered1.startsWith("0")
                && filtered2 != "0" && filtered2.isNotEmpty() && !filtered2.startsWith("0")
    }
}