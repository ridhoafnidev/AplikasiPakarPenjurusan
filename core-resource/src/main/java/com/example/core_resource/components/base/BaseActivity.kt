package com.example.core_resource.components.base

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import com.example.core_resource.databinding.ComponentToolbarBinding
import com.example.core_resource.R

abstract class BaseActivity<VB: ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : AppCompatActivity() {

    val binding by lazy {
        bindingInflater(layoutInflater)
    }

    val bindingToolbar by lazy {
        ComponentToolbarBinding.inflate(layoutInflater)
    }

    protected abstract fun initView()

    protected abstract fun initListener()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(binding.root)
        initView()
        initListener()
    }

    fun initToolbar(
        back: Boolean = false,
        pageName: String = "",
        primary: Boolean = false
    ){
        setSupportActionBar(bindingToolbar.toolbar)

        setPageName(pageName)

        supportActionBar?.let { actionBar ->
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setDisplayHomeAsUpEnabled(back)

            val backIcon = ContextCompat.getDrawable(this, R.drawable.ic_back)
            val iconColor = PorterDuffColorFilter(
                ContextCompat.getColor(this,
                if (primary) R.color.colorTextAccent else R.color.colorTextPrimary), PorterDuff.Mode.MULTIPLY)

            backIcon?.let { drawable ->
                drawable.colorFilter = iconColor
                bindingToolbar.toolbar.overflowIcon?.colorFilter = iconColor
            }

            actionBar.setHomeAsUpIndicator(backIcon)

        }
    }

    fun setTitleGravity(gravity: Int){
        with(bindingToolbar){
            val layoutParams = toolbarTitle.layoutParams as Toolbar.LayoutParams
            layoutParams.gravity = gravity
            toolbarTitle.layoutParams = layoutParams
        }
    }

    fun setPageName(pageName: String, line: Boolean = true){
        with(bindingToolbar){
            toolbarTitle.text = pageName
            lineDivider.setBackgroundColor(ContextCompat.getColor(applicationContext,
                if (line) R.color.colorDivider else R.color.colorInputPrimaryDisabled))
        }
    }

    fun setToolbarSearch(state: Boolean){
        bindingToolbar.toolbarSearch.isVisible = state
    }

}