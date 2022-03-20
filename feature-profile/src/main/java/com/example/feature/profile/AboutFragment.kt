package com.example.feature.profile

import androidx.activity.OnBackPressedCallback
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.feature.profile.databinding.FragmentAboutBinding

class AboutFragment : BaseFragment<FragmentAboutBinding>(FragmentAboutBinding::inflate),
    ModuleNavigator {

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