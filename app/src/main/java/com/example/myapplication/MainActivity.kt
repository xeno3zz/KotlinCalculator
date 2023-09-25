package com.example.myapplication

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.core.text.isDigitsOnly
import com.example.myapplication.databinding.ActivityMainBinding

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
    }

    fun numberAction(view: View){
        binding.apply {
            if(view is Button){
//                if(binding.WorkingsTV.text.toString().last().isDigit()){
//                    canAddDecimal = false
//                }
            if(view.text=="."){
                if(canAddDecimal)
                    WorkingsTV.append(view.text)
                canAddParenthesis = false
                canAddDecimal = false
                canAddOperation = false
            }

            else {
                WorkingsTV.append(view.text)
                canAddParenthesis = false
                canAddOperation = true


            }


            //getting the text from whichever button pressed, adding to workings
        } }
    }



    fun operationAction(view: View){
        if(view is Button && canAddOperation){
            binding.WorkingsTV.append(view.text)
            canAddOperation = false
            canAddDecimal = true
            //getting the text from whichever button pressed, adding to workings
        }
    }
    fun parenthesisAction(view: View){
        if(view is Button && canAddParenthesis){
            binding.WorkingsTV.append(view.text)
            canAddOperation = false
        }
    }
    fun allClearAction(view: View) {
        binding.WorkingsTV.text=""
        binding.ResultsTV.text=""
    }
    fun backSpaceAction(view: View) {
        val length = binding.WorkingsTV.length();
        if(length>0){
            binding.WorkingsTV.text = binding.WorkingsTV.text.subSequence(0, length - 1)
        }

        if(!canAddDecimal)
            canAddDecimal = true
        if(!canAddOperation)
            canAddOperation = true
    }
    fun equalsAction(view: View) {
        binding.ResultsTV.text = calculateResults();
    }

    private fun <T> MutableList<T>.removeLast(): T = if(isEmpty()) throw NoSuchElementException("List is empty.") else removeAt(lastIndex)

    fun evalRPN(tokens: MutableList<Any>):Float {
        val resultList = mutableListOf<Float>() //выходной лист
        for(token in tokens){
            resultList.add(
                when(token) {
                    '+' -> {

                        resultList.removeLast() + resultList.removeLast()

                    }
                    'x' -> {

                        resultList.removeLast() * resultList.removeLast()

                    }
                    '-' -> {
                        val prev = resultList.removeLast()
                        val dprev = resultList.removeLast()
                        dprev - prev

                    }
                    '/' -> {
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


//        val timesDivision = timesDivisionCalculate(digitsOperators)
//        if(timesDivision.isEmpty()) return ""
//
//        val result = addSubtractCalculate(timesDivision)
//        return result.toString()

    }

//    private fun addSubtractCalculate(passedList: MutableList<Any>): Float {
//        var result = passedList[0] as Float
//
//        for(i in passedList.indices){
//            if(passedList[i] is Char && i != passedList.lastIndex)
//            {
//                val operator = passedList[i]
//                val nextDigit = passedList[i + 1] as Float
//                if (operator == '+')
//                    result += nextDigit
//                if (operator == '-')
//                    result -= nextDigit
//            }
//        }
//        return result
//    }
//
//    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any>
//    {
//        var list = passedList
//        while(list.contains('x') || list.contains('/'))
//        {
//            list = calcTimesDiv(list)
//        }
//        return list
//    }
//
//    private fun calcTimesDiv(passedList: MutableList<Any>): MutableList<Any> {
//        val newList = mutableListOf<Any>()
//        var restartIndex = passedList.size
//        for(i in passedList.indices){
//            if(passedList[i] is Char && i != passedList.lastIndex && i < restartIndex){
//                val operator = passedList[i]
//                val prevDigit = passedList[i - 1] as Float
//                val nextDigit = passedList[i + 1] as Float
//                when(operator){
//                    'x' ->
//                    {
//                        newList.add(prevDigit * nextDigit)
//                        restartIndex = i + 1
//                    }
//                    '/' ->
//                    {
//                        newList.add(prevDigit / nextDigit)
//                        restartIndex = i + 1
//                    }
//                    else ->
//                    {
//                        newList.add(prevDigit)
//                        newList.add(operator)
//                    }
//                }
//            }
//            if(i > restartIndex)
//                newList.add(passedList[i])
//        }
//        return newList
//    }

    private fun digitsOperators(): MutableList<Any>{
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for (character in binding.WorkingsTV.text){
            //
            if(character.isDigit() || character == '.'){
                currentDigit+= character
            }
            else{
                if(!currentDigit.isEmpty()){
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
            if(token.toString().isDigitsOnly() || token.toString().contains('.')){
                resultList.add(token)
            }
            if(token == '+' || token == '-' || token == 'x' || token == '/' || token == '('){
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
            if(token == ')') {
                while(stackList.last() != '('){
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