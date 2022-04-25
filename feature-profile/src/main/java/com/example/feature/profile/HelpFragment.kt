package com.example.feature.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.feature.profile.databinding.FragmentHelpBinding
import com.example.feature.profile.databinding.FragmentPersonalDataBinding


class HelpFragment : BaseFragment<FragmentHelpBinding>(
    FragmentHelpBinding::inflate,
    R.string.bantuan
), ModuleNavigator {

    override fun initView() {
        requireActivity().onBackPressedDispatcher.addCallback(
            requireActivity(),
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )
    }

    override fun initListener() {

    }

}