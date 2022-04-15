package com.kemenag_inhu.absensi.feature.home.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kemenag_inhu.absensi.core.analytics.Analytics
import com.kemenag_inhu.absensi.core_data.clearAppData
import com.kemenag_inhu.absensi.core_data.data.PegawaiProfileRepository
import com.kemenag_inhu.absensi.core_data.data.UserRepository
import com.kemenag_inhu.absensi.core_data.data.remote.api.ApiEvent
import com.kemenag_inhu.absensi.core_data.data.remote.api.orCopyIfFailedFrom
import com.kemenag_inhu.absensi.core_data.domain.ListEvents
import com.kemenag_inhu.absensi.core_domain.model.PegawaiProfile
import com.kemenag_inhu.absensi.core_domain.usecase.EventUseCase
import com.kemenag_inhu.absensi.core_util.utility.executeWithDelayAndTimeOut
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val profileRepository: PegawaiProfileRepository,
    private val analytics: Analytics,

) : ViewModel() {

    private val _profile = MutableLiveData<ApiEvent<PegawaiProfile?>>()
    val profile: LiveData<ApiEvent<PegawaiProfile?>> = _profile

    private val _logoutEvent = MutableLiveData<Boolean?>()
    val logoutEvent: LiveData<Boolean?> = _logoutEvent

    init {
        loadProfile()
        getCurrentProfile()
    }

    fun getCurrentProfile() {
        viewModelScope.launch {
            userRepository.getCurrentUserAsFlow()
                .map {
                    it.getData(false)?.idUser
                }
                .flatMapConcat {
                    profileRepository.getProfileById(it ?: 0)
                }
                .collectIndexed { index, value ->
                    if (index == 0) delay(MINIMUM_LOADING_DELAY)
                    _profile.value = value.copy(cacheIndex = index)
                }
        }
    }

    fun loadProfile() {
        viewModelScope.launch {
            profileRepository.loadProfile()
                .collect { _profile.value = it orCopyIfFailedFrom profile}
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getCurrentUserAsFlow()
                .mapNotNull { it.getData() }
                .collect {
                    executeWithDelayAndTimeOut(MINIMUM_LOADING_DELAY, LOGOUT_TIME_OUT) {
                        true
                    }.let { isSuccess ->
                        if (isSuccess) {
                            KoinJavaComponent.getKoin().clearAppData()
                            analytics.removeUser()
                        }
                        _logoutEvent.postValue(isSuccess)
                    }
                }
        }
    }

    fun resetLogoutEvent() {
        _logoutEvent.value = null
    }

}

private const val MINIMUM_LOADING_DELAY = 1000L
private const val LOGOUT_TIME_OUT       = 5000L