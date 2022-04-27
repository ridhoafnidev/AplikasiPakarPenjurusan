package com.example.feature.profile

import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.recyclical.datasource.dataSourceTypedOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.example.core_data.api.ApiEvent
import com.example.core_data.domain.LastResult
import com.example.core_data.domain.Siswa
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.feature.auth.SiswaViewModel
import com.example.feature.profile.databinding.FragmentStudentDataBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class StudentDataFragment : BaseFragment<FragmentStudentDataBinding>(
    FragmentStudentDataBinding::inflate
),
    ModuleNavigator {

    private val siswaViewModel by sharedViewModel<SiswaViewModel>()

    override fun initView() {
        requireActivity().onBackPressedDispatcher.addCallback(
            requireActivity(),
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )

        siswaViewModel.getSiswaAll()
        siswaViewModel.siswaGetAll.observe(viewLifecycleOwner, { siswa ->
            when (siswa) {
                is ApiEvent.OnProgress -> {
                    Timber.d("progress h${siswa.currentResult}")
                }
                is ApiEvent.OnSuccess -> {
                    Log.d("dfdf", "pfgfofhjghjighdsdsrogressprogressprogress ${siswa.getData()!!}")
                    setupData(siswa.getData()!!)
                }
                is ApiEvent.OnFailed -> {
                    Timber.d("progress ${siswa.getException()}")
                }
            }
        })
    }

    private fun setupData(data: List<Siswa>) {
        with(binding) {
            rvStudent.setup {
                withLayoutManager(LinearLayoutManager(context))
                withDataSource(dataSourceTypedOf(data))
                withItem<Siswa, StudentViewHolder>(R.layout.item_student) {
                    onBind(::StudentViewHolder) { index, siswa ->
                        tvName.text = siswa.nama
                    }

                    onClick {
                        navigateToProfileActivity(
                            status = "Personal Data From Guru",
                            idUserSiswa = item.idUser.toString()
                        )
                    }
                }
            }
        }
    }

    override fun initListener() {

    }

}