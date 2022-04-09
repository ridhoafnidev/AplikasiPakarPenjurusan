package com.example.subfeature.nilai_siswa

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.afollestad.vvalidator.form
import com.example.core_data.api.ApiEvent
import com.example.core_data.domain.ListSiswa
import com.example.core_data.domain.Siswa
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.core_util.dismissKeyboard
import com.example.core_util.hideProgress
import com.example.core_util.showProgress
import com.example.feature.auth.SiswaViewModel
import com.example.subfeature.nilai_siswa.databinding.FragmentAddNilaiSiswaBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.util.ArrayList

class AddNilaiSiswaFragment : BaseFragment<FragmentAddNilaiSiswaBinding>(
    FragmentAddNilaiSiswaBinding::inflate
), ModuleNavigator {

    private val siswaViewModel by sharedViewModel<SiswaViewModel>()
    private val nilaiSiswaViewModel by sharedViewModel<NilaiSiswaViewModel>()

    private var namaList = ArrayList<String>()
    private var idUserList = ArrayList<String>()
    private val textBtnSubmit by lazy {
        "Submit";
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView() {
        siswaViewModel.getSiswaAll()
        setupInput()

        siswaViewModel.siswaGetAll.observe(viewLifecycleOwner, { siswa ->
            when (siswa) {
                is ApiEvent.OnProgress -> {
                    Timber.d("progress h${siswa.currentResult}")
                }
                is ApiEvent.OnSuccess -> {
                    Log.d("dfdf", "pfgfofhjghjighdsdsrogressprogressprogress ${siswa.getData()!!}")
                    initNama(siswa.getData()!!)
                }
                is ApiEvent.OnFailed -> {
                    Timber.d("progress ${siswa.getException()}")
                }
            }
        })

        nilaiSiswaViewModel.addNilaiSiswa.observe(viewLifecycleOwner, { addNilaiSiswa ->
            when (addNilaiSiswa) {
                is ApiEvent.OnProgress -> {
                    showProgress()
                    Timber.d("progress ${addNilaiSiswa.currentResult}")
                }
                is ApiEvent.OnSuccess -> {
                    hideProgress(true)
                    val snackBar = Snackbar.make(
                        requireView(),
                        "Data Berhasil ditambah",
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
                        findNavController().navigateUp()
                    }
                    snackBar.show()

                    Handler(Looper.getMainLooper()).postDelayed({
                        snackBar.dismiss()
                        findNavController().navigateUp()
                    }, 1000)
                }
                is ApiEvent.OnFailed -> {
                    Log.d("sdsdsd", "cureesdsd ${addNilaiSiswa.getException().toString()}")
                    hideProgress(true)
                    Snackbar.make(
                        requireContext(), requireView(),
                        addNilaiSiswa.getException().toString(), Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    override fun initListener() {

    }

    private fun setupInput() {
        with(binding) {
            form {
                useRealTimeValidation(disableSubmit = true)
                inputLayout(R.id.til_rata_ipa) {
                    isNotEmpty().description("Rata-Rata IPA harus diisi")
                }
                inputLayout(R.id.til_rata_ips) {
                    isNotEmpty().description("Rata-Rata IPS harus diisi")
                }

                submitWith(R.id.btn_submit_add_nilai) {
                    dismissKeyboard()

                    val rata_ipa = edtRataIpa.text.toString()
                    val rata_ips = edtRataIps.text.toString()

                    nilaiSiswaViewModel.addNilaiSiswa(
                        rata_ipa,
                        rata_ips
                    )
                }
            }
        }
    }

    private fun initNama(data: ListSiswa) {

        if (namaList.isEmpty()){
            if (data.isNotEmpty()){
                nilaiSiswaViewModel.nama = data[0].nama
            }
        }

        data.map {
            namaList.add(it.nama)
            idUserList.add(it.idUser.toString())
        }

        binding.spinnerNama.apply {
            item = namaList as List<Any>?

            var index = -1

            for ((i, v) in data.withIndex()) {
                if (v.nama == nilaiSiswaViewModel.nama) {
                    index = i
                }
            }

            setSelection(index)

            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
//                    Toast.makeText(context, agamaList[position], Toast.LENGTH_SHORT).show()
                    Log.d("dfdfdf", "agamaList ${namaList[position]}")
                    nilaiSiswaViewModel.nama = namaList[position]
                    nilaiSiswaViewModel.userId =
                        data.find { it.nama == namaList[position] && it.idUser.toString() == idUserList[position]}?.idUser.toString()

                    Log.d("dfdfdf", "agamaList ${namaList[position]} dan ${nilaiSiswaViewModel.userId}")
                }

                override fun onNothingSelected(adapterView: AdapterView<*>) {}
            }
        }
    }

    private fun showProgress() = with(binding) {

        listOf(
            btnSubmitAddNilai, tilRataIpa, edtRataIpa, tilRataIps, edtRataIps
        ).forEach { it.isEnabled = false }

        btnSubmitAddNilai.showProgress()
    }

    private fun hideProgress(isEnable: Boolean) = with(binding) {
        btnSubmitAddNilai.postDelayed(
            {
                listOf(
                    btnSubmitAddNilai,
                    tilRataIpa, edtRataIpa, tilRataIps, edtRataIps, spinnerNama
                ).forEach { it.isEnabled = true }
            }, 1000L
        )

        btnSubmitAddNilai.hideProgress(textBtnSubmit) {
            isEnable && with(binding) {
                "${edtRataIpa.text}".isNotBlank() && "${edtRataIps.text}".isNotBlank()
            }
        }
    }

}