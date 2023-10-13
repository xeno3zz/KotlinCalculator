package com.example.myapplication.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapplication.R
import com.example.myapplication.ui.model.KeyboardKey
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
    var onKeyPressed: (KeyboardKey) -> Unit = {}
    init {
        inflate(context, R.layout.custom_keyboard_layout, this)
    }
    override fun onFinishInflate() {
        super.onFinishInflate()
        button1.setOnClickListener { onKeyPressed.invoke(KeyboardKey.Key1) }
        button2.setOnClickListener { onKeyPressed(KeyboardKey.Key2) }
        button3.setOnClickListener { onKeyPressed(KeyboardKey.Key3) }
        button4.setOnClickListener { onKeyPressed(KeyboardKey.Key4) }
        button5.setOnClickListener { onKeyPressed(KeyboardKey.Key5) }
        button6.setOnClickListener{ onKeyPressed(KeyboardKey.Key6) }
        button7.setOnClickListener{ onKeyPressed(KeyboardKey.Key7) }
        button8.setOnClickListener{ onKeyPressed(KeyboardKey.Key8) }
        button9.setOnClickListener{ onKeyPressed(KeyboardKey.Key9) }
        button0.setOnClickListener{ onKeyPressed(KeyboardKey.Key0) }
        buttonClear.setOnClickListener{ onKeyPressed(KeyboardKey.KeyClear) }
        buttonBackSpace.setOnClickListener{ onKeyPressed(KeyboardKey.KeyBackspace) }
        buttonDot.setOnClickListener { onKeyPressed(KeyboardKey.KeyDot) }
        buttonDivide.setOnClickListener { onKeyPressed(KeyboardKey.KeyDivide) }
        buttonMultiply.setOnClickListener { onKeyPressed(KeyboardKey.KeyMultiply) }
        buttonMinus.setOnClickListener { onKeyPressed(KeyboardKey.KeyMinus) }
        buttonPlus.setOnClickListener { onKeyPressed(KeyboardKey.KeyPlus) }
        buttonLeftP.setOnClickListener { onKeyPressed(KeyboardKey.KeyLeftP) }
        buttonRightP.setOnClickListener { onKeyPressed(KeyboardKey.KeyRightP) }
        buttonEquals.setOnClickListener { onKeyPressed(KeyboardKey.KeyEquals) }
    }
}
