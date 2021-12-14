package com.example.core_resource.components

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.example.core_util.commons.Constants
import com.example.core_resource.R
import com.google.android.material.button.MaterialButton
import timber.log.Timber

class BaseEditText : MaterialButton {
    private var editTextType: Int = 0
    private var fontStyle: Int = 0
    private var fontName: Int = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setValues(attrs)
        init()
    }

    @SuppressLint("CustomViewStyleable")
    private fun setValues(attrs: AttributeSet?) {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.BaseView)
        try {
            val n = attr.indexCount
            for (i in 0 until n){
                when(val attribute = attr.getIndex(i)){
                    R.styleable.BaseView_base_edit_text_style -> editTextType = attr.getInt(attribute, 0)
                    R.styleable.BaseView_base_font_style -> fontStyle = attr.getInt(attribute, 0)
                    R.styleable.BaseView_base_font_family -> fontName = attr.getInt(attribute, 0)
                    else -> Timber.i("$javaClass: $attribute")
                }
            }
        } finally {
            attr.recycle()
        }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        background = ContextCompat.getDrawable(context, Constants.View.Input[editTextType])
        setFont(fontStyle, fontName)
    }

    private fun setNewTypeFace() {
        val font = Typeface.createFromAsset(context.assets, Constants.View.Name[fontName] + Constants.View.Style[fontStyle] + Constants.View.Type)
        setTypeface(font, Typeface.NORMAL)
    }

    private fun setFont(font: Int, name: Int) {
        fontStyle = font
        fontName = name
    }
}