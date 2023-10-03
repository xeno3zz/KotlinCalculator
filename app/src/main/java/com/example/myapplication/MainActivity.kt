package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.model.KeyboardKey
import com.example.myapplication.ui.views.KeyboardView
import org.w3c.dom.Text

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var canAddOperation = false
    private var canAddDecimal = true
    private var canAddParenthesis = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val workingsTextView = findViewById<TextView>(R.id.WorkingsTV)
        val resultsTextView = findViewById<TextView>(R.id.ResultsTV)
        findViewById<KeyboardView>(R.id.keyboard_view).onKeyPressed = { key ->
            when(key){
                KeyboardKey.KeyEquals -> {
                    resultsTextView.text = calculateResults();
                }
                KeyboardKey.KeyClear ->{
                    workingsTextView.text=""
                    resultsTextView.text=""
                    canAddParenthesis = true
                    canAddDecimal = true
                }
                KeyboardKey.KeyBackspace -> {
                    val length = workingsTextView.length();
                    if(length>0){
                        workingsTextView.text = workingsTextView.text.subSequence(0, length - 1)
                    }
                    if(!canAddDecimal)
                        canAddDecimal = true
                    if(!canAddOperation)
                        canAddOperation = true
                }
                KeyboardKey.KeyPlus, KeyboardKey.KeyMinus, KeyboardKey.KeyDivide, KeyboardKey.KeyMultiply -> {
                    if(canAddOperation){
                        workingsTextView.append("${key.keyValue}")
                        canAddOperation = false
                        canAddDecimal = true
                        //getting the text from whichever button pressed, adding to workings
                    }
                }
                KeyboardKey.KeyDot -> {
                    if(canAddDecimal)
                        workingsTextView.append("${key.keyValue}")
                    canAddParenthesis = false
                    canAddDecimal = false
                    canAddOperation = false
                }
                KeyboardKey.KeyLeftP, KeyboardKey.KeyRightP -> {
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
    private fun <T> MutableList<T>.removeLast(): T = if(isEmpty()) throw NoSuchElementException("List is empty.") else removeAt(lastIndex)
    fun evalRPN(tokens: MutableList<Any>):Float {
        val resultList = mutableListOf<Float>() //выходной лист
        for(token in tokens){
            resultList.add(
                when(token) {
                    KeyboardKey.KeyPlus.keyValue -> {

                        resultList.removeLast() + resultList.removeLast()

                    }
                    KeyboardKey.KeyMultiply.keyValue -> {

                        resultList.removeLast() * resultList.removeLast()

                    }
                    KeyboardKey.KeyMinus.keyValue -> {
                        val prev = resultList.removeLast()
                        val dprev = resultList.removeLast()
                        dprev - prev

                    }
                    KeyboardKey.KeyDivide.keyValue -> {
                        val prev = resultList.removeLast()
                        val dprev = resultList.removeLast()
                        dprev / prev
                    }
                    else -> token.toString().toFloat()
                }
            )
        }
        return resultList.last()
    }
    private fun calculateResults(): String {
        val digitsOperators = digitsOperators()
        if(digitsOperators.isEmpty()) return ""
        val infixToPostfix = InfixToPostfix(digitsOperators)
        return evalRPN(infixToPostfix).toString()
    }
    private fun digitsOperators(): MutableList<Any>{
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for (character in binding.WorkingsTV.text){
            //
            if(character.isDigit() || character == KeyboardKey.KeyDot.keyValue){
                currentDigit+= character
            }
            else{
                if(currentDigit.isNotEmpty()){
                    list.add(currentDigit.toFloat())
                    currentDigit = ""
                    list.add(character)
                }
                else{
                    list.add(character)
                }
            }
        }
        if(currentDigit != "")
            list.add(currentDigit.toFloat())
        return list
    }
    private fun precedence(input: Any):Int {
        when(input) {
            '+' -> return 2
            '-' -> return 2
            'x' -> return 3
            '/' -> return 3
            '(' -> return 0
        }
        return -1
    }
    private fun InfixToPostfix(passedList: MutableList<Any>): MutableList<Any>{
        val resultList = mutableListOf<Any>()
        var stackList = ArrayDeque<Any>()
        var token = ""
        for (token in passedList){
            if(token.toString().isDigitsOnly() || token.toString().contains(KeyboardKey.KeyDot.keyValue)){
                resultList.add(token)
            }
            if(token == KeyboardKey.KeyPlus.keyValue || token == KeyboardKey.KeyMinus.keyValue || token == KeyboardKey.KeyMultiply.keyValue || token == KeyboardKey.KeyDivide.keyValue || token == KeyboardKey.KeyLeftP.keyValue){
                if(stackList.isEmpty()){
                    stackList.add(token)
                }
                else if(precedence(stackList.last()) >= precedence(token) && precedence(token) != 0){
                    resultList.add(stackList.removeLast())
                    stackList.add(token)
                }
                else {
                    stackList.add(token)
                }
            }
            if(token == KeyboardKey.KeyRightP.keyValue) {
                while(stackList.last() != KeyboardKey.KeyLeftP.keyValue){
                    resultList.add(stackList.removeLast())
                }
                stackList.removeLast()
            }
        }
        while(!stackList.isEmpty()){
            resultList.add(stackList.removeLast())
        }
        return resultList
    }
}