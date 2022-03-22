package com.example.feature.home

import androidx.lifecycle.lifecycleScope
import com.example.core_data.clearAppData
import com.example.core_data.removeAll
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.home.R
import com.example.home.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import java.net.CookieHandler

class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate,
    R.string.title_profile
), ModuleNavigator {

    private val cookieHandler: CookieHandler by inject()

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

        binding.rowKeluar.idRowKeluar.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                cookieHandler.removeAll()
                getKoin().clearAppData()
                navigateToAuthActivity(finnishCurrent = true)
            }
        }
    }

    override fun initListener() {
    }
}