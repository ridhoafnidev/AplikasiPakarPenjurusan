package com.example.subfeature.nilai_penjurusan

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core_data.api.ApiEvent
import com.example.core_data.domain.IPA
import com.example.core_data.domain.IPC
import com.example.core_data.domain.IPS
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.core_util.gone
import com.example.core_util.visible
import com.example.feature.auth.SiswaViewModel
import com.example.subfeature.nilai_penjurusan.databinding.FragmentNilaiPenjurusanBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class NilaiPenjurusanFragment : BaseFragment<FragmentNilaiPenjurusanBinding>(
    FragmentNilaiPenjurusanBinding::inflate
), ModuleNavigator {
    private val isGuru by lazy { (activity as NilaiPenjurusanActivity).isGuru }
    private val userId by lazy { (activity as NilaiPenjurusanActivity).userId }
    private val nilaiSiswaViewModel by sharedViewModel<NilaiPenjurusanViewModel>()
    private val siswaViewModel by sharedViewModel<SiswaViewModel>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView() {
        siswaViewModel.siswaGetAll.observe(viewLifecycleOwner) { siswa ->
            when (siswa) {
                is ApiEvent.OnProgress -> {
                    Timber.d("progress h${siswa.currentResult}")
                }
                is ApiEvent.OnSuccess -> {

                    nilaiSiswaViewModel.nilaiSiswaGetAll.observe(
                        viewLifecycleOwner
                    ) { nilaiSiswa ->
                        when (nilaiSiswa) {
                            is ApiEvent.OnProgress -> {
                                Timber.d("progress ${nilaiSiswa.currentResult}")
                            }
                            is ApiEvent.OnSuccess -> {
                                nilaiSiswaViewModel.lastResultAll.observe(viewLifecycleOwner) { hasilAngket ->
                                    when (hasilAngket) {
                                        is ApiEvent.OnSuccess -> {
                                            if (isGuru) {
                                                binding.layoutMagorStudent.root.gone()
                                                binding.tableNilaiSiswa.root.visible()
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
                                            else {
                                                with(binding){
                                                    binding.layoutMagorStudent.root.visible()
                                                    binding.tableNilaiSiswa.root.gone()
                                                    layoutMagorStudent.apply {
                                                        root.visible()
                                                        val averageValue = nilaiSiswa.getData()?.filter { it.user_id == userId.toLong() }?.first()?.rata_akhir.toString()
                                                        val qaValue =  hasilAngket.getData()?.filter { it.userId == userId.toInt() }?.first()?.hasilAkhir.toString()
                                                        tvAverageValue.text = "$averageValue (${averageValue.toInt().ipaIps})"
                                                        tvQaValue.text = qaValue
                                                        tvMagorValue.text = nilaiSiswaViewModel getMagorValue TripleData(averageValue, averageValue.toInt().ipaIps, qaValue)
                                                        if (tvMagorValue.text.isEmpty()) tvWarning.visible() else tvWarning.gone()
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            is ApiEvent.OnFailed -> {
                                Timber.d("progress ${nilaiSiswa.getException()}")
                            }
                        }
                    }
                }
                is ApiEvent.OnFailed -> {
                    Timber.d("progress ${siswa.getException()}")
                }
            }
        }
    }

    override fun initListener() {
    }

}

private val Int.ipaIps: String
    get() = if (this >= 70) IPA else IPS

typealias TripleData = Triple<String, String, String>