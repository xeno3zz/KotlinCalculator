package com.example.myapplication.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding


class KeyboardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    var onKeyPressed: (KeyboardKey) -> Unit = {}
    init{
        inflate(context, R.layout.custom_keyboard_layout, this)
    }
}


enum class KeyboardKey(var keyValue: Char){
    KeyDot('.'),
    Key0('0'),
    Key1('1'),
    Key2('2'),
    Key3('3'),
    Key4('4'),
    Key5('5'),
    Key6('6'),
    Key7('7'),
    Key8('8'),
    Key9('9'),

    KeyLeftP('('),
    KeyRightP(')'),
    KeyPlus('+'),
    KeyMinus('-'),
    KeyDivide('/'),
    KeyMultiply('x'),
    KeyEquals('='),
    KeyClear('C'),
    KeyBackspace('⌫')
}
