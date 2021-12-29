package com.example.feature.home

import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.home.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), ModuleNavigator {
    override fun initView() {
    }

    override fun initListener() {
        binding.btnStart.setOnClickListener {
            navigateToPakarActivity()
        }
    }
}