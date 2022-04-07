package com.example.subfeature.nilai_penjurusan

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core_data.api.ApiEvent
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.feature.auth.SiswaViewModel
import com.example.subfeature.nilai_penjurusan.databinding.FragmentNilaiPenjurusanBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class NilaiPenjurusanFragment : BaseFragment<FragmentNilaiPenjurusanBinding>(
    FragmentNilaiPenjurusanBinding::inflate
), ModuleNavigator{
    private val level by lazy { (activity as NilaiPenjurusanActivity).level }
    private val nilaiSiswaViewModel by sharedViewModel<NilaiPenjurusanViewModel>()
    private val siswaViewModel by sharedViewModel<SiswaViewModel>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView() {
        if (level == "guru") {

            siswaViewModel.siswaGetAll.observe(viewLifecycleOwner, { siswa ->
                when (siswa) {
                    is ApiEvent.OnProgress -> {
                        Timber.d("progress h${siswa.currentResult}")
                    }
                    is ApiEvent.OnSuccess -> {

                        nilaiSiswaViewModel.nilaiSiswaGetAll.observe(
                            viewLifecycleOwner,
                            { nilaiSiswa ->
                                when (nilaiSiswa) {
                                    is ApiEvent.OnProgress -> {
                                        Timber.d("progress ${nilaiSiswa.currentResult}")
                                    }
                                    is ApiEvent.OnSuccess -> {

                                        nilaiSiswaViewModel.lastResultAll.observe(viewLifecycleOwner) { hasilAngket ->
                                            when(hasilAngket) {
                                                is ApiEvent.OnSuccess -> {
                                                    binding.tableNilaiSiswa.rvNilaiSiswa.layoutManager =
                                                        LinearLayoutManager(requireActivity())
                                                    val nilaiSiswadapter = NilaiPenjurusanAdapter(
                                                        nilaiSiswa.getData()!!,
                                                        siswa.getData()!!,
                                                        hasilAngket.getData()!!
                                                    )
                                                    binding.tableNilaiSiswa.rvNilaiSiswa.adapter =
                                                        nilaiSiswadapter
                                                }
                                            }
                                        }
                                    }
                                    is ApiEvent.OnFailed -> {
                                        Timber.d("progress ${nilaiSiswa.getException()}")
                                    }
                                }
                            })
                    }
                    is ApiEvent.OnFailed -> {
                        Timber.d("progress ${siswa.getException()}")
                    }
                }
            })
        }
    }

    override fun initListener() {
    }
}