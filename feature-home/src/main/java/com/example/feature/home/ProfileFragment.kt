package com.example.feature.home

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.core_data.clearAppData
import com.example.core_data.domain.User
import com.example.core_data.domain.isGuru
import com.example.core_data.domain.isSiswa
import com.example.core_data.removeAll
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.feature.auth.AuthViewModel
import com.example.home.R
import com.example.home.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.net.CookieHandler

class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate,
    R.string.title_profile
), ModuleNavigator {

    private val cookieHandler: CookieHandler by inject()

    private val viewModel by sharedViewModel<AuthViewModel>()
    private var idUser = ""
    var photo = ""
    var name = ""
    var nipNisn = ""
    var alamat = ""
    var email = ""
    var asalSekolah = ""

    override fun initView() {
        observeAuth()

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

    private fun observeDetail(data: User?) {
        if (data != null) {
            if (data.isSiswa) {
                viewModel.siswaDetail.observe(viewLifecycleOwner) { siswa ->
//                    Log.d("sdsdsd", "cureesdsd ${siswa!!.alamat}")
                    siswa?.let {
                        name = it.nama
                        alamat = it.alamat
                        nipNisn = it.nisn
                        photo = it.foto
                        asalSekolah = it.asalSekolah

                        with(binding) {
                            rowProfileHeader.tvName.text = name
                            rowProfileHeader.tvAlamat.text = name
                            rowProfileHeader.tvNipNisn.text = "NISN : $name"
                            rowProfileHeader.tvAsalSekolah.text = name
                        }
                    }
                }
            } else if (data.isGuru) {
                viewModel.guruDetail.observe(viewLifecycleOwner) { guru ->
                    guru?.let {
                        name = it.nama
                        alamat = it.alamat
                        nipNisn = it.nip
                        photo = it.foto
                        email = it.email

                        with(binding) {
                            rowProfileHeader.tvName.text = name
                            rowProfileHeader.tvAlamat.text = name
                            rowProfileHeader.tvNipNisn.text = "NIP : $name"
                            rowProfileHeader.tvAsalSekolah.text = email
                        }
                    }

                    Log.d("sdsdsd", "cureesdsd ${guru!!.alamat}")
                }
            }
        }
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

    override fun initListener() {
    }
}