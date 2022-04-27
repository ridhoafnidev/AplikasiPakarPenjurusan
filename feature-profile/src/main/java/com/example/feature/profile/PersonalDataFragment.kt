package com.example.feature.profile

import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.core_data.APP_SISWA_IMAGES_URL
import com.example.core_data.api.ApiEvent
import com.example.core_data.domain.User
import com.example.core_data.domain.isGuru
import com.example.core_data.domain.isSiswa
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.core_util.gone
import com.example.core_util.toLocalDate
import com.example.core_util.visible
import com.example.core_util.withFormat
import com.example.feature.auth.AuthViewModel
import com.example.feature.profile.databinding.FragmentPersonalDataBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class PersonalDataFragment : BaseFragment<FragmentPersonalDataBinding>(
    FragmentPersonalDataBinding::inflate,
    R.string.data_pribadi
), ModuleNavigator {

    private val status by lazy { (activity as ProfileActivity).status }
    private val idUserSiswaParam by lazy { (activity as ProfileActivity).idUserSiswa }
    private val viewModel by sharedViewModel<AuthViewModel>()
    private var idUser = ""
    private var level = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {
        if (status == "Password") {
            findNavController().navigate(PersonalDataFragmentDirections.actionPersonalDataFragmentToChangePasswordFragment())
        } else if (status == "About") {
            findNavController().navigate(PersonalDataFragmentDirections.actionPersonalDataFragmentToAboutFragment())
        } else if (status == "Help") {
            findNavController().navigate(PersonalDataFragmentDirections.actionPersonalDataFragmentToHelpFragment())
        } else if (status == "Data Siswa") {
            findNavController().navigate(PersonalDataFragmentDirections.actionPersonalDataFragmentToStudentDataFragment())
        }

        if (status == "Personal Data From Guru") {
            binding.fabEditPersonalData.gone()
        } else {
            binding.fabEditPersonalData.visible()
        }

        binding.fabEditPersonalData.setOnClickListener {
            findNavController().navigate(
                PersonalDataFragmentDirections.actionPersonalDataFragmentToEditPersonalDataFragment(
                    level,
                    idUser
                )
            )
        }
        observeAuth()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeAuth() {
        viewModel.auth.observe(viewLifecycleOwner) { data ->
            if (data?.idUser.toString().isNotEmpty()) {
                idUser = data?.idUser.toString()
                viewModel.idUser = idUser
                level = data?.level.toString()

                if (idUserSiswaParam.isNotEmpty()) {
                    viewModel.idUserSiswa = idUserSiswaParam
                }

                observeDetail(data)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeDetail(data: User?) {
        if (data != null) {
            Log.d("dadsd", "sdsds 1 $status")
            if (status == "Personal Data From Guru") {
                Log.d("dadsd", "sdsds $status")
                viewModel.getSiswaById(viewModel.idUserSiswa.toInt())
                viewModel.siswaRequest.observe(viewLifecycleOwner) { siswa ->
//                    Log.d("sdsdsd", "cureesdsd ${siswa!!.alamat}")
                    when (siswa) {
                        is ApiEvent.OnProgress -> {
                            Timber.d("progress ${siswa.currentResult}")
                        }
                        is ApiEvent.OnSuccess -> {
                            siswa.getData()?.let {
                                with(binding) {
                                    rowPersonalDataSiswa.root.visibility = View.VISIBLE
                                    rowPersonalDataGuru.root.visibility = View.GONE

                                    with(rowPersonalDataSiswa) {
                                        tvAgama.text = it.agama
                                        tvAgamaAyah.text = it.agamaAyah
                                        tvAgamaIbu.text = it.agamaIbu
                                        tvAlamat.text = it.alamat
                                        tvAsalSekolah.text = it.asalSekolah
                                        tvKelas.text = it.kelas
                                        tvNama.text = it.nama
                                        tvNamaAyah.text = it.namaAyah
                                        tvNamaIbu.text = it.namaIbu
                                        tvNisn.text = it.nisn
                                        tvPekerjaanAyah.text = it.pekerjaanAyah
                                        tvPekerjaanIbu.text = it.pekerjaanIbu
                                        tvPendidikanTerakhirIbu.text = it.pendidikanTerakhirIbu
                                        tvPendidikanTerakhirAyah.text = it.pendidikanTerakhirAyah
                                        tvStatusAsalSekolah.text = it.statusAsalSekolah
                                        tvTanggalLahir.text =
                                            it.tanggalLahir.toLocalDate() withFormat getString(R.string.date_format_d_MMM_yyyy)
                                        tvTempatLahir.text = it.tempatLahir
                                        tvUmurAyah.text = it.umurAyah
                                        tvUmurIbu.text = it.umurAyah
                                    }
                                }
                            }
                        }
                        is ApiEvent.OnFailed -> {
                            Snackbar.make(
                                requireContext(), requireView(),
                                siswa.getException().toString(), Snackbar.LENGTH_SHORT
                            ).show()
                            Log.d("sdsdsd", "cureesdsd ${siswa.getException().toString()}")
                        }
                    }
                }
            } else {
                if (data.isSiswa) {
                    viewModel.siswaDetail.observe(viewLifecycleOwner) { siswa ->
//                    Log.d("sdsdsd", "cureesdsd ${siswa!!.alamat}")
                        siswa?.let {
                            with(binding) {
                                rowPersonalDataSiswa.root.visibility = View.VISIBLE
                                rowPersonalDataGuru.root.visibility = View.GONE
                                with(rowPersonalDataSiswa) {
                                    tvAgama.text = it.agama
                                    tvAgamaAyah.text = it.agamaAyah
                                    tvAgamaIbu.text = it.agamaIbu
                                    tvAlamat.text = it.alamat
                                    tvAsalSekolah.text = it.asalSekolah
                                    tvKelas.text = it.kelas
                                    tvNama.text = it.nama
                                    tvNamaAyah.text = it.namaAyah
                                    tvNamaIbu.text = it.namaIbu
                                    tvNisn.text = it.nisn
                                    tvPekerjaanAyah.text = it.pekerjaanAyah
                                    tvPekerjaanIbu.text = it.pekerjaanIbu
                                    tvPendidikanTerakhirIbu.text = it.pendidikanTerakhirIbu
                                    tvPendidikanTerakhirAyah.text = it.pendidikanTerakhirAyah
                                    tvStatusAsalSekolah.text = it.statusAsalSekolah
                                    tvTanggalLahir.text =
                                        it.tanggalLahir.toLocalDate() withFormat getString(R.string.date_format_d_MMM_yyyy)
                                    tvTempatLahir.text = it.tempatLahir
                                    tvUmurAyah.text = it.umurAyah
                                    tvUmurIbu.text = it.umurAyah
                                }
                            }
                        }
                    }
                } else if (data.isGuru) {
                    viewModel.guruDetail.observe(viewLifecycleOwner) { guru ->
                        guru?.let {
                            with(binding) {
                                rowPersonalDataSiswa.root.visibility = View.GONE
                                rowPersonalDataGuru.root.visibility = View.VISIBLE
                                with(rowPersonalDataGuru) {
                                    tvAlamat.text = it.alamat
                                    tvEmail.text = it.email
                                    tvNama.text = it.nama
                                    tvNip.text = it.nip
                                }
                            }
                        }
                        Log.d("sdsdsd", "cureesdsd ${guru!!.alamat}")
                    }
                }
            }

        }
    }

    override fun initListener() {

    }
}