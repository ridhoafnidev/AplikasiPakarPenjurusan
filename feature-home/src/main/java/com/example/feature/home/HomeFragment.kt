package com.example.feature.home

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core_data.api.ApiEvent
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
    private var userId = ""

    override fun initView() {
        observeAuth()
    }

    override fun initListener() {
        with(binding){
            ivPakar.setOnClickListener {
                viewModel.getLastResult(userId.toInt())
                viewModel.lastResultAll.observe(viewLifecycleOwner) { hasilAngket ->
                    when (hasilAngket) {
                        is ApiEvent.OnSuccess -> {
                            hasilAngket.getData()?.let {
                                Toast.makeText(requireContext(), "Anda sudah melakukan pengisian data sebelumnya", Toast.LENGTH_SHORT).show()
                            } ?: navigateToPakarActivity()

                        }
                    }
                }
            }
            ivHasilAngket.setOnClickListener {
                navigateToHasilAngketActivity()
            }

            ivNilaiSiswa.setOnClickListener {
                navigateToNilaiSiswaActivity(level = level)
            }
            ivHasilPenjurusan.setOnClickListener {
                navigateToNilaiPenjurusanActivity(level = level, userId = userId)
            }

        }
    }

    private fun observeAuth() {
        viewModel.auth.observe(viewLifecycleOwner) { data ->
            if (!data?.idUser.toString().isEmpty()) {
                level = data?.level.toString()
                userId = data?.idUser.toString()

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