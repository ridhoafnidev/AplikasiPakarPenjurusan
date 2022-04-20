package com.example.subfeature.nilai_siswa

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.afollestad.vvalidator.form
import com.example.core_data.api.ApiEvent
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.core_util.dismissKeyboard
import com.example.core_util.hideProgress
import com.example.core_util.showProgress
import com.example.subfeature.nilai_siswa.databinding.FragmentUpdateNilaiSiswaBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class UpdateNilaiSiswaFragment : BaseFragment<FragmentUpdateNilaiSiswaBinding>(
    FragmentUpdateNilaiSiswaBinding::inflate
), ModuleNavigator {

    private val args: UpdateNilaiSiswaFragmentArgs by navArgs()

    private val textBtnEdit by lazy {
        "Edit";
    }

    private val nilaiSiswaViewModel by sharedViewModel<NilaiSiswaViewModel>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView() {
        nilaiSiswaViewModel.userId = args.idUser
        setupInput()
    }

    override fun initListener() {

    }

    @RequiresApi(Build.VERSION_CODES.M)
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

                submitWith(R.id.btn_edit_nilai) {
                    dismissKeyboard()

                    val rata_ipa = edtRataIpa.text.toString()
                    val rata_ips = edtRataIps.text.toString()

                    nilaiSiswaViewModel.updateNilaiSiswa(
                        rata_ipa,
                        rata_ips
                    )

                    nilaiSiswaViewModel.updateNilaiSiswa.observe(viewLifecycleOwner, { updateNilaiSiswa ->
                        when (updateNilaiSiswa) {
                            is ApiEvent.OnProgress -> {
                                showProgress()
                                Timber.d("progress ${updateNilaiSiswa.currentResult}")
                            }
                            is ApiEvent.OnSuccess -> {
                                hideProgress(true)
                                val snackBar = Snackbar.make(
                                    requireView(),
                                    "Data Berhasil diperbarui",
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
                                Log.d("sdsdsd", "cureesdsd ${updateNilaiSiswa.getException().toString()}")
                                hideProgress(true)
                                Snackbar.make(
                                    requireContext(), requireView(),
                                    updateNilaiSiswa.getException().toString(), Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                }
            }
        }
    }

    private fun showProgress() = with(binding) {

        listOf(
            btnEditNilai, tilRataIpa, edtRataIpa, tilRataIps, edtRataIps
        ).forEach { it.isEnabled = false }

        btnEditNilai.showProgress()
    }

    private fun hideProgress(isEnable: Boolean) = with(binding) {
        btnEditNilai.postDelayed(
            {
                listOf(
                    btnEditNilai,
                    tilRataIpa, edtRataIpa, tilRataIps, edtRataIps
                ).forEach { it.isEnabled = true }
            }, 1000L
        )

        btnEditNilai.hideProgress(textBtnEdit) {
            isEnable && with(binding) {
                "${edtRataIpa.text}".isNotBlank() && "${edtRataIps.text}".isNotBlank()
            }
        }
    }

}