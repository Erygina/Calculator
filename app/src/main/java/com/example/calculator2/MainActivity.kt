package com.example.calculator2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.calculator2.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var plusMinusCount = 0
    var pointBoolean: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        for (i in binding.numbersGroup.referencedIds.indices) {
            findViewById<Button>(binding.numbersGroup.referencedIds[i]).setOnClickListener {
                val number = (it as Button).text
                binding.resultView.append(number)
            }
        }

        for (i in binding.mathOperationGroup.referencedIds.indices) {
            findViewById<Button>(binding.mathOperationGroup.referencedIds[i]).setOnClickListener {
                val oper = (it as Button).text
                    if (listOf("0", "1", "2","3","4","5","6","7","8","9").any { it == binding.resultView.text.last().toString() } ) {
                        binding.resultView.append(oper)
                        plusMinusCount = 0
                    }
                pointBoolean = false
            }
}
            binding.AcButton.setOnClickListener() {
                binding.resultView.text = ""
                pointBoolean = false
            }

            binding.pointButton.setOnClickListener() {
                if (pointBoolean == false) {
                    if (binding.resultView.text.isNotEmpty()) {
                            if (binding.resultView.text.last() != '.') {
                                binding.resultView.append(".")
                        }
                    } else {
                        binding.resultView.append("0.")
                    }

                    pointBoolean = true
                }
            }

            binding.plusMinusButton.setOnClickListener() {
                plusMinusCount++
                val str = binding.resultView.text
                if (plusMinusCount % 2 == 1) {
                    binding.resultView.append("-")
                }
                else {
                    binding.resultView.text = str.substring(0, str.length-1)
                }
            }

            binding.percentButton.setOnClickListener(){
                val str = ExpressionBuilder(binding.resultView.text.toString()).build()
                val result = str.evaluate() / 100
               binding.resultView.text = result.toString()
            }

            binding.equalButton.setOnClickListener() {
                try{
                    val expr = ExpressionBuilder(binding.resultView.text.toString()).build()
                    val res = expr.evaluate()
                    val longRes = res.toLong()
                    if (res == longRes.toDouble())  binding.resultView.text = longRes.toString()
                    else binding.resultView.text = res.toString()

                } catch(e:Exception){
                    Log.d("Ошибка","сообщение: ${e.message}")
                    binding.resultView.text = getString(R.string.text_mistake)
                }
            }
        }
    }











