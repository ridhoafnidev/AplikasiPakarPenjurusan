package com.example.core_resource.components.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.core_resource.databinding.ComponentToolbarBinding
import java.lang.IllegalArgumentException

abstract class BaseFragment<VB: ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB,
    @StringRes var titleDefault:Int? = null
) : Fragment() {

    private var _binding: VB? = null

    var baseActivity: BaseActivity<*>? = null

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
        if (activity is BaseActivity<*>){
            baseActivity = activity as BaseActivity<*>
        }
        initView()
        initListener()
    }

//    fun setTitle(title: String? = null, line: Boolean = true, gravity: Int) {
//        baseActivity?.let { activity ->
//            activity.setTitleGravity(gravity)
//            title?.let { activity.setPageName(it, line) } ?: run {
//                titleDefault?.let { default ->
//                    activity.setPageName(activity.getString(default), line)
//                }
//            }
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}