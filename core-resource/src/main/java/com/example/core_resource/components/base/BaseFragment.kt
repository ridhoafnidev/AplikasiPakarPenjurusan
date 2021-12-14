package com.example.core_resource.components.base

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.core_resource.R
import com.example.core_resource.databinding.ComponentToolbarBinding
import java.lang.IllegalArgumentException

abstract class BaseFragment<VB: ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : Fragment() {

    private var _binding: VB? = null

    val binding get() = _binding as VB

    val bindingToolbar by lazy {
        ComponentToolbarBinding.inflate(layoutInflater)
    }

    protected abstract fun initView()

    protected abstract fun initListener()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)

        if (_binding == null)
            throw IllegalArgumentException("Binding cannot be null")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun initToolbar(
        back: Boolean = false,
        pageName: String = "",
        primary: Boolean = false
    ){

        if (activity is AppCompatActivity){
            val toolbar = (activity as AppCompatActivity)
            toolbar.setSupportActionBar(bindingToolbar.toolbar)

            setPageName(pageName)

            toolbar.supportActionBar?.let { actionBar ->
                actionBar.setDisplayShowTitleEnabled(false)
                actionBar.setDisplayHomeAsUpEnabled(back)

                val backIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back)
                val iconColor = PorterDuffColorFilter(
                    ContextCompat.getColor(requireContext(),
                        if (primary) R.color.colorTextAccent else R.color.colorTextPrimary), PorterDuff.Mode.MULTIPLY)

                backIcon?.let { drawable ->
                    drawable.colorFilter = iconColor
                    bindingToolbar.toolbar.overflowIcon?.colorFilter = iconColor
                }

                actionBar.setHomeAsUpIndicator(backIcon)

            }
        }
    }

    fun setPageName(pageName: String, line: Boolean = true){
        with(bindingToolbar){
            toolbarTitle.text = pageName
            lineDivider.setBackgroundColor(ContextCompat.getColor(requireContext(),
                if (line) R.color.colorDivider else R.color.colorInputPrimaryDisabled))
        }
    }

    fun setToolbarSearch(state: Boolean){
        bindingToolbar.toolbarSearch.isVisible = state
    }

}