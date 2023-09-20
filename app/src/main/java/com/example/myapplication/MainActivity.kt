package com.example.myapplication

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.example.myapplication.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {

    private var canAddOperation = false
    private var canAddDecimal = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }


    fun numberAction(view: View){
        binding.apply {
            if(view is Button){
            if(view.text=="."){
                if(canAddDecimal)
                    WorkingsTV.append(view.text)

                canAddDecimal = false
            }
            else
                WorkingsTV.append(view.text)

            canAddOperation = true
            //getting the text from whichever button pressed, adding to workings

        } }

    }

    fun operationAction(view: View){
        if(view is Button && canAddOperation){
            binding.WorkingsTV.append(view.text)
            canAddOperation = false
            //getting the text from whichever button pressed, adding to workings

        }
    }


    fun allClearAction(view: View) {
        binding.WorkingsTV.text=""
        binding.ResultTV.text=""
    }
    fun backSpaceAction(view: View) {
        val length = binding.WorkingsTV.length();
        if(length>0){
            binding.WorkingsTV.text = binding.WorkingsTV.text.subSequence(0, length - 1)
        }
    }
    fun equalsAction(view: View) {
        binding.ResultTV.text = calculateResults();
    }

    private fun calculateResults(): String {
        val digitsOperators = digitsOperators() 
        if(digitsOperators.isEmpty()) return ""
        
        val timesDivision = timesDivisionCalculate(digitsOperators)
        if(timesDivision.isEmpty()) return ""

        val result = addSubtractCalculate(timesDivision)
        return result.toString()

    }

    private fun addSubtractCalculate(passedList: MutableList<Any>): Float {
        var result = passedList[0] as Float

        for(i in passedList.indices){
            if(passedList[i] is Char && i != passedList.lastIndex)
            {
                val operator = passedList[i]
                val nextDigit = passedList[i + 1] as Float
                if (operator == '+')
                    result += nextDigit
                if (operator == '-')
                    result -= nextDigit
            }
        }
        return result
    }

    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any>
    {
        var list = passedList
        while(list.contains('x') || list.contains('/'))
        {
            list = calcTimesDiv(list)
        }
        return list
    }

    private fun calcTimesDiv(passedList: MutableList<Any>): MutableList<Any> {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size
        for(i in passedList.indices){
            if(passedList[i] is Char && i != passedList.lastIndex && i < restartIndex){
                val operator = passedList[i]
                val prevDigit = passedList[i - 1] as Float
                val nextDigit = passedList[i + 1] as Float
                when(operator){
                    'x' ->
                    {
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }
                    '/' ->
                    {
                        newList.add(prevDigit / nextDigit)
                        restartIndex = i + 1
                    }
                    else ->
                    {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }
            if(i > restartIndex)
                newList.add(passedList[i])
        }
        return newList
    }

    private fun digitsOperators(): MutableList<Any>{
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for (character in binding.WorkingsTV.text){
            //
            if(character.isDigit() || character == '.'){
                currentDigit+= character
            }
            else{
                list.add(currentDigit.toFloat())
                currentDigit = ""
                list.add(character)
            }
        }
        if(currentDigit != "")
            list.add(currentDigit.toFloat())

        return list
    }
}