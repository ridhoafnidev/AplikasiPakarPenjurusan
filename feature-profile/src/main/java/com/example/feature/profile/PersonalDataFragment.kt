package com.example.feature.profile

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.feature.profile.databinding.FragmentPersonalDataBinding

class PersonalDataFragment : BaseFragment<FragmentPersonalDataBinding>(
    FragmentPersonalDataBinding::inflate,
    R.string.app_name
), ModuleNavigator {

    private val status by lazy { (activity as ProfileActivity).status }

    override fun initView() {
        Log.d("dfdf", "statusnya $status")
        if (status == "Password") {
            Log.d("dfdf", "jalankan")
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