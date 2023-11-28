package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.example.domain.model.KeyboardKey
import com.example.domain.usecase.CalculateResults
import com.example.myapplication.ui.views.KeyboardView
class MainActivity : AppCompatActivity() {
    private var canAddOperation = false
    private var canAddDecimal = true
    private var canAddParenthesis = true
    private val calculateResultsUseCase = CalculateResults()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val workingsTextView = findViewById<TextView>(R.id.WorkingsTV)
        val resultsTextView = findViewById<TextView>(R.id.ResultsTV)
        findViewById<KeyboardView>(R.id.keyboard_view).onKeyPressed = { key ->
            when(key){
                com.example.domain.model.KeyboardKey.KeyEquals -> {
                    resultsTextView.text = calculateResultsUseCase.execute(workingsTextView.text.toString())
                }
                com.example.domain.model.KeyboardKey.KeyClear ->{
                    workingsTextView.text=""
                    resultsTextView.text=""
                    canAddParenthesis = true
                    canAddDecimal = true
                }
                com.example.domain.model.KeyboardKey.KeyBackspace -> {
                    val length = workingsTextView.length()
                    if(length>0){
                        workingsTextView.text = workingsTextView.text.subSequence(0, length - 1)
                    }
                    if(!canAddDecimal)
                        canAddDecimal = true
                    if(!canAddOperation)
                        canAddOperation = true
                }
                com.example.domain.model.KeyboardKey.KeyPlus, com.example.domain.model.KeyboardKey.KeyMinus, com.example.domain.model.KeyboardKey.KeyDivide, com.example.domain.model.KeyboardKey.KeyMultiply -> {
                    if(canAddOperation){
                        workingsTextView.append("${key.keyValue}")
                        canAddOperation = false
                        canAddDecimal = true
                    }
                }
                com.example.domain.model.KeyboardKey.KeyDot -> {
                    if(canAddDecimal)
                        workingsTextView.append("${key.keyValue}")
                    canAddParenthesis = false
                    canAddDecimal = false
                    canAddOperation = false
                }
                com.example.domain.model.KeyboardKey.KeyLeftP, com.example.domain.model.KeyboardKey.KeyRightP -> {
                    if(canAddParenthesis) {
                        workingsTextView.append("${key.keyValue}")
                    }
                }
                else -> {
                    workingsTextView.append("${key.keyValue}")
                    canAddOperation = true
                    canAddParenthesis = true
                }

            }
        }
    }
}