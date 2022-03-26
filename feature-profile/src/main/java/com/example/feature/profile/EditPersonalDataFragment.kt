package com.example.feature.profile

import android.util.Log
import android.view.View
import com.example.core_data.domain.User
import com.example.core_data.domain.isGuru
import com.example.core_data.domain.isSiswa
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.feature.auth.AuthViewModel
import com.example.feature.profile.databinding.FragmentEditPersonalDataBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EditPersonalDataFragment : BaseFragment<FragmentEditPersonalDataBinding>(
    FragmentEditPersonalDataBinding::inflate
),
    ModuleNavigator {

    private val viewModel by sharedViewModel<AuthViewModel>()
    private var idUser = ""

    override fun initView() {

        observeAuth()
    }

    override fun initListener() {

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
                            rowPersonalDataSiswaEdit.root.visibility = View.VISIBLE
                            rowPersonalDataGuruEdit.root.visibility = View.GONE
                            with(rowPersonalDataSiswaEdit) {
//                                tvAgama.text = it.agama
                            }
                        }
                    }
                }
            } else if (data.isGuru) {
                viewModel.guruDetail.observe(viewLifecycleOwner) { guru ->
                    guru?.let {
                        with(binding) {
                            rowPersonalDataSiswaEdit.root.visibility = View.GONE
                            rowPersonalDataGuruEdit.root.visibility = View.VISIBLE
                            with(rowPersonalDataGuruEdit) {
//                                tvAlamat.text = it.alamat
                            }
                        }
                    }

                    Log.d("sdsdsd", "cureesdsd ${guru!!.alamat}")
                }
            }
        }
    }
}