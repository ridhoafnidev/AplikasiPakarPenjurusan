package com.example.subfeature.nilai_siswa

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core_data.api.ApiEvent
import com.example.core_data.domain.NilaiSiswa
import com.example.core_data.domain.Siswa
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.feature.auth.AuthViewModel
import com.example.feature.auth.SiswaViewModel
import com.example.subfeature.nilai_siswa.databinding.FragmentNilaiSiswaBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class NilaiSiswaFragment : BaseFragment<FragmentNilaiSiswaBinding>(
    FragmentNilaiSiswaBinding::inflate
), ModuleNavigator, NilaiSiswaInterface {
    private val level by lazy { (activity as NilaiSiswaActivity).level }
    private val nilaiSiswaViewModel by sharedViewModel<NilaiSiswaViewModel>()
    private val siswaViewModel by sharedViewModel<SiswaViewModel>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView() {
        if (level == "guru") {
            siswaViewModel.getSiswaAll()
            nilaiSiswaViewModel.getNilaiSiswaAll()

            siswaViewModel.siswaGetAll.observe(viewLifecycleOwner, { siswa ->
                when (siswa) {
                    is ApiEvent.OnProgress -> {
                        Timber.d("progress ${siswa.currentResult}")
                    }
                    is ApiEvent.OnSuccess -> {
                        Log.d("dfdf", "progressprogressprogress ${siswa.getData()!!}")
//
                        nilaiSiswaViewModel.nilaiSiswaGetAll.observe(
                            viewLifecycleOwner,
                            { nilaiSiswa ->
                                when (nilaiSiswa) {
                                    is ApiEvent.OnProgress -> {
                                        Timber.d("progress ${nilaiSiswa.currentResult}")
                                    }
                                    is ApiEvent.OnSuccess -> {

//                                    nilaiSiswa.getData().let { nilaiSiswaList.addAll(it!!) }
                                        Log.d("ffdf", "progress gfgf ${nilaiSiswa.getData()!!}")

                                        binding.tableNilaiSiswa.rvNilaiSiswa.layoutManager =
                                            LinearLayoutManager(requireActivity())
                                        val nilaiSiswadapter = NilaiSiswaAdapter(
                                            nilaiSiswa.getData()!!,
                                            siswa.getData()!!,
                                            this
                                        )
                                        binding.tableNilaiSiswa.rvNilaiSiswa.adapter =
                                            nilaiSiswadapter
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

            nilaiSiswaViewModel.deleteSiswa.observe(
                viewLifecycleOwner,
                { nilaiSiswa ->
                    when (nilaiSiswa) {
                        is ApiEvent.OnProgress -> {
                            Timber.d("progress ${nilaiSiswa.currentResult}")
                        }
                        is ApiEvent.OnSuccess -> {
                            if (nilaiSiswa.hasNotBeenConsumed) {
//                                    nilaiSiswa.getData().let { nilaiSiswaList.addAll(it!!) }
                                Log.d("ffdf", "progress gfgf ${nilaiSiswa.getData()!!}")
                                nilaiSiswa.getData(true)

                                val snackBar = Snackbar.make(
                                    requireView(),
                                    "Data Berhasil dihapus",
                                    Snackbar.LENGTH_INDEFINITE
                                )
                                    .setActionTextColor(
                                        (activity as AppCompatActivity).getColorStateList(R.color.white)
                                    )
                                    .setBackgroundTint(
                                        (activity as AppCompatActivity).getColor(R.color.colorBlackGrade)
                                    ).setActionTextColor(
                                        (activity as AppCompatActivity).getColorStateList(R.color.colorSecondaryBase)
                                    )
                                snackBar.setAction("OK") {
                                    snackBar.dismiss()
                                    requireActivity().finish()
                                }
                                snackBar.show()

                                Handler(Looper.getMainLooper()).postDelayed({
                                    snackBar.dismiss()
                                    requireActivity().finish()
                                }, 1000)
                            }
                        }
                        is ApiEvent.OnFailed -> {
                            Timber.d("progress ${nilaiSiswa.getException()}")
                        }
                    }
                })
        }
    }

    override fun initListener() {

    }

    override fun onDeleteNilaiSiswa(idUser: String) {
        nilaiSiswaViewModel.deleteSiswa(idUser.toInt())
    }
}