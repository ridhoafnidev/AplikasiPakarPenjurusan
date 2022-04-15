package com.kemenag_inhu.absensi.feature.auth.login

import androidx.lifecycle.*
import com.kemenag_inhu.absensi.core_data.data.AbsensiRepository
import com.kemenag_inhu.absensi.core_data.data.MasterJabatanFungsionalRepository
import com.kemenag_inhu.absensi.core_data.data.MasterJabatanStrukturalRepository
import com.kemenag_inhu.absensi.core_data.data.UserRepository
import com.kemenag_inhu.absensi.core_data.data.remote.api.ApiEvent
import com.kemenag_inhu.absensi.core_domain.model.ListDataAbsensi
import com.kemenag_inhu.absensi.core_domain.model.User
import com.kemenag_inhu.absensi.core_domain.model.isAdmin
import com.kemenag_inhu.absensi.core_domain.model.isEmploy
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel(
    private val mJabatanStrukturalRepository: MasterJabatanStrukturalRepository,
    private val mJabatanFungsionalRepository: MasterJabatanFungsionalRepository,
    private val absensiRepository: AbsensiRepository,
    private val userRepository: UserRepository
//    private val mJabatanStrukturalUseCase: MasterJabatanStrukturalUseCase,
//    private val mJenisTenagaUseCase: MasterJenisTenagaUseCase,
//    private val mLevelUseCase: MasterLevelUseCase,
//    private val mPangkatGolonganUseCase: MasterPangkatGolonganUseCase,
//    private val mPnsNonpnsUseCase: MasterPnsNonpnsUseCase,
//    private val mStatusAbsensiUseCase: MasterStatusAbsensiUseCase,
//    private val mPegawaiUseCase: MasterPegawaiUseCase,
    //private val userUseCase: UserUseCase,
) : ViewModel() {

    private val _login = MutableLiveData<ApiEvent<User?>>()
    val login: LiveData<ApiEvent<User?>> = _login

    val currentUser = userRepository.getCurrentUserAsFlow()
        .map { it.getData() }
        .asLiveData()

    /**
     * Perform login viea Rest API call
     * load master data required before calling actual login API.
     * And load initial data when login is success
     */

    fun login(userName: String, password: String) {
        viewModelScope.launch {
            loadMasterData()
                .flatMapConcat {
                    when(it) {
                        is ApiEvent.OnLoading -> flowOf(ApiEvent.OnLoading<User?>(null))
                        is ApiEvent.OnFailed -> flowOf(it.toOtherFailedEventNullable())
                        is ApiEvent.OnSuccess -> userRepository.login(userName, password)
                    }
                }
                .flatMapConcat { userEvent ->
                    when(userEvent) {
                        is ApiEvent.OnSuccess -> {
                            if (userEvent.getData()?.isAdmin == true) flowOf(userEvent)
                            else loadInitialDataMurid().flatMapConcat {
                                when(it) {
                                    is ApiEvent.OnLoading -> flowOf(ApiEvent.OnLoading<User?>(null))
                                    is ApiEvent.OnFailed -> flowOf(it.toOtherFailedEventNullable<User?>())
                                        .onEach { userRepository.setCurrentUser(null) }
                                    is ApiEvent.OnSuccess -> flowOf(userEvent)
                                }
                            }
                        }
                        else -> flowOf(userEvent)
                    }
                }
                .filterNot { it is ApiEvent.OnLoading }
                .onStart { emit(ApiEvent.OnLoading(null)) }
                .collect { _login.value }
        }
    }

    //region loadinitial data

     private fun loadInitialDataMurid(): Flow<ApiEvent<Any?>> = absensiRepository.loadHistoryAbsensi()

    //endregion


    //region Load Master Data Before Login

    @FlowPreview
    private fun loadMasterData(): Flow<ApiEvent<Any?>> =
        mJabatanStrukturalRepository.getAll()
            .flatMapConcat {
                if (it is ApiEvent.OnSuccess) mJabatanFungsionalRepository.getAll()
                else flowOf(it)
            }
//            .flatMapConcat {
//                if (it is ApiEvent.OnSuccess) mJenisTenagaUseCase.getAll()
//                else flowOf(it)
//            }
//            .flatMapConcat {
//                if (it is ApiEvent.OnSuccess) mJenisTenagaUseCase.getAll()
//                else flowOf(it)
//            }
//            .flatMapConcat {
//                if (it is ApiEvent.OnSuccess) mPangkatGolonganUseCase.getAll()
//                else flowOf(it)
//            }
//            .flatMapConcat {
//                if (it is ApiEvent.OnSuccess) mPnsNonpnsUseCase.getAll()
//                else flowOf(it)
//            }
//            .flatMapConcat {
//                if (it is ApiEvent.OnSuccess) mLevelUseCase.getAll()
//                else flowOf(it)
//            }
//            .flatMapConcat {
//                if (it is ApiEvent.OnSuccess) mStatusAbsensiUseCase.getAll()
//                else flowOf(it)
//            }
//            .flatMapConcat {
//                if (it is ApiEvent.OnSuccess) mPegawaiUseCase.getAll()
//                else flowOf(it)
//            }
//            .flatMapConcat {
//                if (it is ApiEvent.OnSuccess) mPegawaiUseCase.getAll()
//                else flowOf(it)
//            }

    //endregion
}