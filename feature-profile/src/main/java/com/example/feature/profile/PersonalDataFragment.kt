package com.example.feature.profile

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.feature.profile.databinding.FragmentPersonalDataBinding

class PersonalDataFragment : BaseFragment<FragmentPersonalDataBinding>(
    FragmentPersonalDataBinding::inflate
), ModuleNavigator {

    private val status by lazy { (activity as ProfileActivity).status }

    override fun initView() {
        if (status == "Password") {
            findNavController().navigate(PersonalDataFragmentDirections.actionPersonalDataFragmentToChangePasswordFragment())
        } else if (status == "About") {
            findNavController().navigate(PersonalDataFragmentDirections.actionPersonalDataFragmentToAboutFragment())
        }

        binding.fabEditPersonalData.setOnClickListener {
            findNavController().navigate(PersonalDataFragmentDirections.actionPersonalDataFragmentToEditPersonalDataFragment())
        }
    }

    override fun initListener() {

    }
}