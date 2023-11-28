package com.example.domain.usecase

class CalculateResults() {
    fun execute(passedList:String):String{
        val digitsOperators = digitsOperators(passedList)
        if(digitsOperators.isEmpty()) return ""
        val infixToPostfix = infixToPostfix(digitsOperators)
        return evalRPN(infixToPostfix).toString()
    }
    fun precedence(input: Any):Int {
        when(input) {
            '+' -> return 2
            '-' -> return 2
            'x' -> return 3
            '/' -> return 3
            '(' -> return 0
        }
        return -1
    }
    fun String.isDigitsOnly() = all(Char::isDigit) && isNotEmpty()

    fun evalRPN(tokens: MutableList<Any>):Float {
        val resultList = mutableListOf<Float>()
        for(token in tokens){
            resultList.add(
                when(token) {
                    com.example.domain.model.KeyboardKey.KeyPlus.keyValue -> {

                        resultList.removeLast() + resultList.removeLast()

                    }
                    com.example.domain.model.KeyboardKey.KeyMultiply.keyValue -> {

                        resultList.removeLast() * resultList.removeLast()

                    }
                    com.example.domain.model.KeyboardKey.KeyMinus.keyValue -> {

                        val prev = resultList.removeLast()
                        val dPrev = resultList.removeLast()
                        dPrev - prev

                    }
                    com.example.domain.model.KeyboardKey.KeyDivide.keyValue -> {
                        val prev = resultList.removeLast()
                        val dPrev = resultList.removeLast()
                        dPrev / prev
                    }

                    else -> token.toString().toFloat()
                }
            )
        }
        return resultList.last()
    }

    fun infixToPostfix(passedList: MutableList<Any>): MutableList<Any>{
        val resultList = mutableListOf<Any>()
        val stackList = ArrayDeque<Any>()
//        var token = ""
        for (token in passedList){
            if(token.toString().isDigitsOnly() || token.toString().contains(com.example.domain.model.KeyboardKey.KeyDot.keyValue) ){
                resultList.add(token)
            }
            if(token == com.example.domain.model.KeyboardKey.KeyPlus.keyValue || token == com.example.domain.model.KeyboardKey.KeyMinus.keyValue || token == com.example.domain.model.KeyboardKey.KeyMultiply.keyValue || token == com.example.domain.model.KeyboardKey.KeyDivide.keyValue || token == com.example.domain.model.KeyboardKey.KeyLeftP.keyValue){
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
            if(token == com.example.domain.model.KeyboardKey.KeyRightP.keyValue) {
                while(stackList.last() != com.example.domain.model.KeyboardKey.KeyLeftP.keyValue){
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

    fun digitsOperators(passedList:String): MutableList<Any>{
        val list = mutableListOf<Any>()

        var currentDigit = ""
        for (character in passedList){
            if(character.isDigit() || character == com.example.domain.model.KeyboardKey.KeyDot.keyValue){
                currentDigit+= character
            }
            else{
                if(currentDigit.isNotEmpty()) {
                    list.add(currentDigit.toFloat())
                    currentDigit = ""
                    list.add(character)
                }
                else if (character == com.example.domain.model.KeyboardKey.KeyMinus.keyValue) {
                    list.add(0.0)
                    currentDigit = ""
                    list.add(character)
                }
                else {
                    list.add(character)
                }
            }
        }
        if(currentDigit != "")
            list.add(currentDigit.toFloat())
        return list
    }
}
