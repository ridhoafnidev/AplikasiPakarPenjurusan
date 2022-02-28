package com.example.feature.home

import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.home.R
import com.example.home.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate,
    R.string.title_profile
), ModuleNavigator {

    override fun initView() {
        binding.rowMenuProfile.rowDataPribadi.idRowDataPribadi.setOnClickListener {
            navigateToProfileActivity(status = "Personal Data")
        }

        binding.rowMenuProfile.rowGantiPassword.idGantiPassword.setOnClickListener {
            navigateToProfileActivity(status = "Password")
        }

        binding.rowMenuProfile.rowTentangAplikasi.idRowTentangAplikasi.setOnClickListener {
            navigateToProfileActivity(status = "About")
        }
    }

    override fun initListener() {
    }
}