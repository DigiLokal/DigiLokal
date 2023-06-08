package com.digilokal.dilo.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class PasswordEditText : AppCompatEditText {

    interface PasswordValidationListener {
        fun onPasswordValidated(isValid: Boolean)
    }

    private var passwordValidationListener: PasswordValidationListener? = null

    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length < MIN_PASSWORD_LENGTH) {
                    passwordValidationListener?.onPasswordValidated(false)
                } else {
                    passwordValidationListener?.onPasswordValidated(true)
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }

    fun setPasswordValidationListener(listener: PasswordValidationListener) {
        passwordValidationListener = listener
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}