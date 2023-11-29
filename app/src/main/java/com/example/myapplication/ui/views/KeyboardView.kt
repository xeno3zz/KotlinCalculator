package com.example.myapplication.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapplication.R
import com.example.domain.model.KeyboardKey
class KeyboardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val button1 by lazy {findViewById<View>(R.id.button_13)}
    private val button2 by lazy {findViewById<View>(R.id.button_14)}
    private val button3 by lazy {findViewById<View>(R.id.button_15)}
    private val button4 by lazy {findViewById<View>(R.id.button9)}
    private val button5 by lazy {findViewById<View>(R.id.button10)}
    private val button6 by lazy {findViewById<View>(R.id.button11)}
    private val button7 by lazy {findViewById<View>(R.id.button5)}
    private val button8 by lazy {findViewById<View>(R.id.button6)}
    private val button9 by lazy {findViewById<View>(R.id.button7)}
    private val button0 by lazy {findViewById<View>(R.id.button18)}
    private val buttonClear by lazy {findViewById<View>(R.id.button1)}
    private val buttonBackSpace by lazy {findViewById<View>(R.id.button2)}
    private val buttonDot by lazy {findViewById<View>(R.id.button3)}
    private val buttonDivide by lazy {findViewById<View>(R.id.button4)}
    private val buttonMultiply by lazy {findViewById<View>(R.id.button8)}
    private val buttonMinus by lazy {findViewById<View>(R.id.button12)}
    private val buttonPlus by lazy {findViewById<View>(R.id.button16)}
    private val buttonEquals by lazy {findViewById<View>(R.id.button20)}
    private val buttonLeftP by lazy {findViewById<View>(R.id.button17)}
    private val buttonRightP by lazy {findViewById<View>(R.id.button19)}
    var onKeyPressed: (com.example.domain.model.KeyboardKey) -> Unit = {}
    init {
        inflate(context, R.layout.custom_keyboard_layout, this)
    }
    override fun onFinishInflate() {
        super.onFinishInflate()
        button1.setOnClickListener { onKeyPressed(com.example.domain.model.KeyboardKey.Key1) }
        button2.setOnClickListener { onKeyPressed(com.example.domain.model.KeyboardKey.Key2) }
        button3.setOnClickListener { onKeyPressed(com.example.domain.model.KeyboardKey.Key3) }
        button4.setOnClickListener { onKeyPressed(com.example.domain.model.KeyboardKey.Key4) }
        button5.setOnClickListener { onKeyPressed(com.example.domain.model.KeyboardKey.Key5) }
        button6.setOnClickListener{ onKeyPressed(com.example.domain.model.KeyboardKey.Key6) }
        button7.setOnClickListener{ onKeyPressed(com.example.domain.model.KeyboardKey.Key7) }
        button8.setOnClickListener{ onKeyPressed(com.example.domain.model.KeyboardKey.Key8) }
        button9.setOnClickListener{ onKeyPressed(com.example.domain.model.KeyboardKey.Key9) }
        button0.setOnClickListener{ onKeyPressed(com.example.domain.model.KeyboardKey.Key0) }
        buttonClear.setOnClickListener{ onKeyPressed(com.example.domain.model.KeyboardKey.KeyClear) }
        buttonBackSpace.setOnClickListener{ onKeyPressed(com.example.domain.model.KeyboardKey.KeyBackspace) }
        buttonDot.setOnClickListener { onKeyPressed(com.example.domain.model.KeyboardKey.KeyDot) }
        buttonDivide.setOnClickListener { onKeyPressed(com.example.domain.model.KeyboardKey.KeyDivide) }
        buttonMultiply.setOnClickListener { onKeyPressed(com.example.domain.model.KeyboardKey.KeyMultiply) }
        buttonMinus.setOnClickListener { onKeyPressed(com.example.domain.model.KeyboardKey.KeyMinus) }
        buttonPlus.setOnClickListener { onKeyPressed(com.example.domain.model.KeyboardKey.KeyPlus) }
        buttonLeftP.setOnClickListener { onKeyPressed(com.example.domain.model.KeyboardKey.KeyLeftP) }
        buttonRightP.setOnClickListener { onKeyPressed(com.example.domain.model.KeyboardKey.KeyRightP) }
        buttonEquals.setOnClickListener { onKeyPressed(com.example.domain.model.KeyboardKey.KeyEquals) }
    }
}
