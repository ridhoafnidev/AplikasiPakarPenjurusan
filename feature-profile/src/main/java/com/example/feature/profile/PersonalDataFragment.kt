package com.example.feature.profile

import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.core_data.domain.User
import com.example.core_data.domain.isGuru
import com.example.core_data.domain.isSiswa
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.feature.auth.AuthViewModel
import com.example.feature.profile.databinding.FragmentPersonalDataBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PersonalDataFragment : BaseFragment<FragmentPersonalDataBinding>(
    FragmentPersonalDataBinding::inflate
), ModuleNavigator {

    private val status by lazy { (activity as ProfileActivity).status }
    private val viewModel by sharedViewModel<AuthViewModel>()
    private var idUser = ""

    override fun initView() {
        if (status == "Password") {
            findNavController().navigate(PersonalDataFragmentDirections.actionPersonalDataFragmentToChangePasswordFragment())
        } else if (status == "About") {
            findNavController().navigate(PersonalDataFragmentDirections.actionPersonalDataFragmentToAboutFragment())
        }

        binding.fabEditPersonalData.setOnClickListener {
            findNavController().navigate(PersonalDataFragmentDirections.actionPersonalDataFragmentToEditPersonalDataFragment())
        }

        observeAuth()
    }

    private fun observeAuth() {
        viewModel.auth.observe(viewLifecycleOwner) { data ->
            if (!data?.idUser.toString().isEmpty()) {
                idUser = data?.idUser.toString()
                viewModel.idUser = idUser

                observeDetail(data)
            }
        }
    }

    private fun observeDetail(data: User?) {
        if (data != null) {
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
                                tvAlamat.text = it.alamat
                                tvAsalSekolah.text = it.asalSekolah
                                tvKelas.text = it.kelas
                                tvNama.text = it.nama
                                tvNamaAyah.text = it.namaAyah
                                tvNamaIbu.text = it.namaIbu
                                tvNisn.text = it.nisn
                                tvPekerjaanAyah.text = it.pekerjaanAyah
                                tvPendidikanIbu.text = it.pendidikanIbu
                                tvPendidikanTerakhirAyah.text = it.pendidikanTerakhirAyah
                                tvStatusAsalSekolah.text = it.statusAsalSekolah
                                tvTanggalLahir.text = it.tanggalLahir
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

    override fun initListener() {

    }
}