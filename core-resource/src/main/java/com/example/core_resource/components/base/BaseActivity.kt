package com.example.core_resource.components.base

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.example.core_resource.R
import kotlinx.android.synthetic.main.component_toolbar.*

abstract class BaseActivity<VB: ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : AppCompatActivity() {

    val binding: VB by lazy {
        bindingInflater(layoutInflater)
    }

    /*
    val bindingToolbar by lazy {
        ComponentToolbarBinding.inflate(layoutInflater)
    }
     */

    protected abstract fun initView()

    protected abstract fun initListener()

    fun initToolbar(
        back: Boolean = false,
        pageName: String = "",
        primary: Boolean = false
    ){
        setSupportActionBar(toolbar)

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
                toolbar.overflowIcon?.colorFilter = iconColor
            }

            actionBar.setHomeAsUpIndicator(backIcon)

        }
    }

    fun setTitleGravity(gravity: Int){
        val layoutParams = toolbarTitle.layoutParams as Toolbar.LayoutParams
        layoutParams.gravity = gravity
        toolbarTitle.layoutParams = layoutParams
    }

    fun setPageName(pageName: String, line: Boolean = true){
        toolbarTitle.text = pageName
        lineDivider.setBackgroundColor(ContextCompat.getColor(applicationContext,
            if (line) R.color.colorDivider else R.color.colorInputPrimaryDisabled))
    }

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        initListener()
    }

    fun setToolbarSearch(state: Boolean){
        toolbarSearch.visibility = if(state) View.VISIBLE else View.GONE
    }

}