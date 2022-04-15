package com.kemenag_inhu.absensi.feature.home.profile

import com.kemenag_inhu.absensi.core_data.data.remote.api.ApiEvent
import com.kemenag_inhu.absensi.core_data.data.remote.api.ApiException
import com.kemenag_inhu.absensi.core_domain.model.PegawaiProfile
import com.kemenag_inhu.absensi.core_navigation.ModuleNavigator
import com.kemenag_inhu.absensi.core_resource.components.base.BaseFragment
import com.kemenag_inhu.absensi.core_resource.components.hideProgressDialog
import com.kemenag_inhu.absensi.core_resource.components.showProgressDialog
import com.kemenag_inhu.absensi.subfeature.dialog.apifailed.showApiFailedDialog
import com.kemenag_inhu.home.R
import com.kemenag_inhu.home.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate),
    ModuleNavigator {

    //region View Model

    private val viewModel: ProfileViewModel by sharedViewModel()

    //endregion

    private val textEmpty by lazy { getString(R.string.profile_empty_field) }

    override fun initView() {

        //region start data observer

        observerLogout()
        observePegawaiProfile()

        //endregion
    }

    private fun observerLogout() {
        viewModel.logoutEvent.observe(viewLifecycleOwner) {
            it?.let { isSuccess ->
                viewModel.resetLogoutEvent()
                hideProgressDialog()
                if (isSuccess) {
                    navigateToAuthActivity(true)
                }
                else {
                    showApiFailedDialog()
                    observePegawaiProfile()
                }
            }
        }
    }

    private fun observePegawaiProfile() {
        viewModel.profile.observe(viewLifecycleOwner, { event ->
            when (event) {
                is ApiEvent.OnLoading -> {
                    event.getCurrentData()?.let { profile ->
                        onDataLoaded(profile)
                    } ?: onLoadingData()
                }
                is ApiEvent.OnSuccess -> if (event.isFromCache) {
                        event.getData()?.let { profile ->
                        //binding.swipeRefresh.isRefreshing = false
                        onDataLoaded(profile)
                    } ?: if (!event.isInitialCache) {
                        //binding.swipeRefresh.isRefreshing = false
                        onLoadingDataFailed(ApiException.Unknown)
                    }
                }
                is ApiEvent.OnFailed -> if (!event.hasBeenConsumed) {
                    //binding.swipeRefresh.isRefreshing = false
                    onLoadingDataFailed(event.getException())
                }
            }
        })

    }

    private fun onLoadingDataFailed(exception: ApiException) {

        with(binding.rowProfileHeader) {
            tvName.text = textEmpty
            tvNip.text = textEmpty
            tvJabatanStruktural.text = textEmpty
            tvUnitKerja.text = textEmpty
        }

        showApiFailedDialog(exception)
    }

    private fun onLoadingData() {
        with(binding.rowProfileHeader) {
            tvName.text = textEmpty
            tvNip.text = textEmpty
            tvJabatanStruktural.text = textEmpty
            tvUnitKerja.text = textEmpty
        }
    }

    private fun onDataLoaded(profile: PegawaiProfile) {

        with(binding.rowProfileHeader) {

//            if (profile.url.isNotEmpty()) ivProfile.load(profile.url) {
//                crossfade(true)
//                transformations(CircleCropTransformation())
//            } else ivAvatar.setText(profile.userName)

            tvName.text = profile.namaLengkap
            tvNip.text = "NIP: ${profile.nip}"
            tvJabatanStruktural.text = profile.jabatanStruktural
            tvUnitKerja.text = profile.unitKerja
        }
    }

    override fun initListener() {
        with(binding){
//            swipeRefresh.setOnRefreshListener {
//                viewModel.loadProfile()
//            }
            rowKeluar.idRowKeluar.setOnClickListener {
                showProgressDialog()
                viewModel.profile.removeObservers(viewLifecycleOwner)
                viewModel.logout()
            }
        }
    }

}