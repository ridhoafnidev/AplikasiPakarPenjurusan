package com.example.feature.home

import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.core_util.gone
import com.example.core_util.visible
import com.example.feature.auth.AuthViewModel
import com.example.home.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    ModuleNavigator {

    private val viewModel by sharedViewModel<AuthViewModel>()
    private var level = ""

    override fun initView() {
        observeAuth()
    }

    override fun initListener() {
        with(binding){
            cvPakar.setOnClickListener {
                navigateToPakarActivity()
            }
            cvHasilAngket.setOnClickListener {
                navigateToHasilAngketActivity()
            }
        }

        binding.cvNilaiSiswa.setOnClickListener {
            navigateToNilaiSiswaActivity(level = level)
        }

        binding.cvHasilAngket.setOnClickListener {
//            navigateToNilaiSiswaActivity()
        }

        binding.cvHasilPenjurusan.setOnClickListener {
//            navigateToNilaiSiswaActivity()
        }
    }

    private fun observeAuth() {
        viewModel.auth.observe(viewLifecycleOwner) { data ->
            if (!data?.idUser.toString().isEmpty()) {
                level = data?.level.toString()

                if (level == "siswa") {
                    binding.cvPakar.visible()
                    binding.cvNilaiSiswa.gone()
                    binding.cvHasilAngket.visible()
                    binding.cvHasilPenjurusan.visible()
                } else {
                    binding.cvPakar.gone()
                    binding.cvNilaiSiswa.visible()
                    binding.cvHasilAngket.visible()
                    binding.cvHasilPenjurusan.visible()
                }
            }
        }

    }
}