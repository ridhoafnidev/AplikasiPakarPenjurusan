package com.example.feature.auth.register

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.afollestad.vvalidator.form
import com.example.core_data.api.ApiEvent
import com.example.core_data.api.request.guru.RegisterGuruRequest
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.core_util.*
import com.example.feature.auth.AuthViewModel
import com.example.feature_auth.R
import com.example.feature_auth.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.time.LocalDate
import java.util.*


class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate),
    ModuleNavigator {

    private val viewModel by sharedViewModel<AuthViewModel>()

    private var agamaList = ArrayList<String>()
    private var agamaAyahList = ArrayList<String>()
    private var agamaIbuList = ArrayList<String>()
    private var kelasList = ArrayList<String>()
    private var pendidikanAyahList = ArrayList<String>()
    private var pendidikanIbuList = ArrayList<String>()

    lateinit var datePicker: DatePickerHelper

    private val args: RegisterFragmentArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.O)
    private var datePickerValue = LocalDate.MIN

    private val textBtnSubmit by lazy {
        "Submit";
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {

        binding.registerToolbar.toolbar.title = "Register"
        setupInput()
        setupDefaultValue()

        viewModel.registerSiswaRequest.observe(viewLifecycleOwner, { registerSiswa ->
            when (registerSiswa) {
                is ApiEvent.OnProgress -> {
                    showProgress()
                    Timber.d("progress ${registerSiswa.currentResult}")
                }
                is ApiEvent.OnSuccess -> {
                    if (registerSiswa.hasNotBeenConsumed){
                        registerSiswa.getData(true)
                        hideProgress(true)
                        val snackBar = Snackbar.make(
                            requireView(),
                            "Pendaftaran Anda Berhasil, Silahkan login kembali",
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
                            requireActivity().finish()
                        }
                        snackBar.show()

                        Handler(Looper.getMainLooper()).postDelayed({
                            snackBar.dismiss()
                            findNavController().popBackStack(R.id.loginFragment, true)
                        }, 1000)
                    }
                }
                is ApiEvent.OnFailed -> {
                    Log.d("sdsdsd", "cureesdsd ${registerSiswa.getException().toString()}")
                    hideProgress(true)
                    Snackbar.make(
                        requireContext(), requireView(),
                        registerSiswa.getException().toString(), Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        })

        viewModel.registerGuruRequest.observe(viewLifecycleOwner, { registerGuru ->
            when (registerGuru) {
                is ApiEvent.OnProgress -> {
                    showProgress()
                    Timber.d("progress ${registerGuru.currentResult}")
                }
                is ApiEvent.OnSuccess -> {
                    if (registerGuru.hasNotBeenConsumed){
                        registerGuru.getData(true)
                        hideProgress(true)
                        val snackBar = Snackbar.make(
                            requireView(),
                            "Pendaftaran Anda Berhasil, Silahkan login kembali",
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
                            requireActivity().finish()
                        }
                        snackBar.show()

                        Handler(Looper.getMainLooper()).postDelayed({
                            snackBar.dismiss()
                            findNavController().popBackStack(R.id.loginFragment, true)
                        }, 1000)
                    }
                }
                is ApiEvent.OnFailed -> {
                    Log.d("sdsdsd", "cureesdsd ${registerGuru.getException().toString()}")
                    hideProgress(true)
                    Snackbar.make(
                        requireContext(), requireView(),
                        registerGuru.getException().toString(), Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupDefaultValue() {
        if (args.level == "siswa") {
            with(binding) {
                rowRegisterSiswa.root.visibility = View.VISIBLE
                rowRegisterGuru.root.visibility = View.GONE

                with(rowRegisterSiswa) {
                    edtTanggalLahirRegister.setOnClickListener {
                        datePickerShow()
                    }
                    viewModel.agamaId = "Islam"
                    viewModel.agamaAyahId = "Islam"
                    viewModel.agamaIbuId = "Islam"
                    viewModel.kelas = "I IPA"
                    viewModel.pendidikanAyah = "D3/S1"
                    viewModel.pendidikanIbu = "D3/S1"
                }

                initSpinnerAgama()
                initSpinnerAgamaAyah()
                initSpinnerAgamaIbu()
                initSpinnerKelas()
                initSpinnerPendidikanAyah()
                initSpinnerPendidikanIbu()
            }
        } else if (args.level == "guru") {
            with(binding) {
                rowRegisterSiswa.root.visibility = View.GONE
                rowRegisterGuru.root.visibility = View.VISIBLE
            }
        }
    }

    override fun initListener() {

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun datePickerShow() {
        datePicker = DatePickerHelper(requireContext(), true)

        val cal = Calendar.getInstance()
        var d = cal.get(Calendar.DAY_OF_MONTH)
        var m = cal.get(Calendar.MONTH)
        var y = cal.get(Calendar.YEAR)

        if (datePickerValue != LocalDate.MIN) {
            d = datePickerValue.dayOfMonth
            m = datePickerValue.monthValue - 1
            y = datePickerValue.year
        }

        datePicker.showDialog(d, m, y, object : DatePickerHelper.Callback {
            @Suppress("MagicNumber")
            override fun onDateSelected(dayofMonth: Int, month: Int, year: Int) {
                val dayStr = if (dayofMonth < 10) "0${dayofMonth}" else "$dayofMonth"

                val mon = month + 1
                val monthStr = if (mon < 10) "0${mon}" else "$mon"

                val date = "${year}-${monthStr}-${dayStr}"
                datePickerValue = date.toLocalDate()

                with(binding.rowRegisterSiswa) {
                    viewModel.tanggalLahir =
                        datePickerValue withFormat getString(R.string.date_format_yyyy_MM_dd)
                    edtTanggalLahirRegister.setText(datePickerValue withFormat getString(R.string.date_format_d_MMM_yyyy))
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupInput() {
        Log.d("sdsdsd", "cureesdsd level nya adalah ${args.level}")
        with(binding) {
            if (args.level == "siswa") {
                with(rowRegisterSiswa) {
                    form {
                        useRealTimeValidation(disableSubmit = true)
                        inputLayout(R.id.til_username_register) {
                            isNotEmpty().description("Username harus diisi")
                        }
                        inputLayout(R.id.til_password_register) {
                            isNotEmpty().description("Password harus diisi")
                        }
                        inputLayout(R.id.til_nama_register) {
                            isNotEmpty().description("Nama harus diisi")
                        }
                        inputLayout(R.id.til_nisn_register) {
                            isNotEmpty().description("NISN harus diisi")
                        }
                        inputLayout(R.id.til_alamat_register) {
                            isNotEmpty().description("Alamat harus diisi")
                        }
                        spinner(R.id.spinner_kelas_register) {
                            selection()
                                .greaterThan(0)
                                .description("Kelas Harus diisi")
                        }
                        inputLayout(R.id.til_tempat_lahir_register) {
                            isNotEmpty().description("Tempat lahir harus diisi")
                        }
                        inputLayout(R.id.til_tanggal_lahir_register) {
                            isNotEmpty().description("Tanggal lahir harus diisi")
                        }
                        inputLayout(R.id.til_asal_sekolah_register) {
                            isNotEmpty().description("Asal sekolah harus diisi")
                        }
                        inputLayout(R.id.til_status_asal_sekolah_register) {
                            isNotEmpty().description("Status asal sekolah harus diisi")
                        }
                        inputLayout(R.id.til_nama_ayah_register) {
                            isNotEmpty().description("Nama ayah harus diisi")
                        }
                        inputLayout(R.id.til_umur_ayah_register) {
                            isNotEmpty().description("Umur harus diisi")
                        }
                        inputLayout(R.id.til_pekerjaan_ayah_register) {
                            isNotEmpty().description("Pekerjaan ayah harus diisi")
                        }
                        inputLayout(R.id.til_nama_ibu_register) {
                            isNotEmpty().description("Nama ibu harus diisi")
                        }
                        inputLayout(R.id.til_umur_ibu_register) {
                            isNotEmpty().description("Umur ibu harus diisi")
                        }
                        inputLayout(R.id.til_pekerjaan_ibu_register) {
                            isNotEmpty().description("Pekerjaan ibu harus diisi")
                        }

                        Log.d("sdsdsd", "cureesdsd bjhjkjkk kkmkm")

                        submitWith(R.id.btn_submit_register) {
                            dismissKeyboard()

                            val username = edtUsernameRegister.text.toString()
                            val password = edtPasswordRegister.text.toString()
                            val nama = edtNamaRegister.text.toString()
                            val nisn = edtNisnRegister.text.toString()
                            val alamat = edtAlamatRegister.text.toString()
                            val tempatLahir = edtTempatLahirRegister.text.toString()
                            val asalSekolah = edtAsalSekolahRegister.text.toString()
                            val statusAsalSekolah = edtStatusAsalSekolahRegister.text.toString()
                            val namaAyah = edtNamaAyahRegister.text.toString()
                            val umurAyah = edtUmurAyahRegister.text.toString()
                            val pekerjaanAyah = edtPekerjaanAyahRegister.text.toString()
                            val namaIbu = edtNamaIbuRegister.text.toString()
                            val umurIbu = edtUmurIbuRegister.text.toString()
                            val pekerjaanIbu = edtPekerjaanIbuRegister.text.toString()

                            Log.d("sdsdsd", "jalankan murid  ")

                            viewModel.registerSiswa(
                                level = args.level,
                                usernames = username,
                                passwords = password,
                                nama = nama,
                                nisn = nisn,
                                alamat = alamat,
                                asalSekolah = asalSekolah,
                                statusAsalSekolah = statusAsalSekolah,
                                namaAyah = namaAyah,
                                pekerjaanAyah = pekerjaanAyah,
                                namaIbu = namaIbu,
                                umurIbu = umurIbu,
                                pekerjaanIbu = pekerjaanIbu,
                                tempatLahir = tempatLahir
                            )
                        }
                    }
                }
            } else if (args.level == "guru") {
                with(rowRegisterGuru) {
                    form {
                        useRealTimeValidation(disableSubmit = true)
                        inputLayout(R.id.til_username_register_guru) {
                            isNotEmpty().description("Username harus diisi")
                        }
                        inputLayout(R.id.til_password_register_guru) {
                            isNotEmpty().description("Password harus diisi")
                        }
                        inputLayout(R.id.til_nama_register_guru) {
                            isNotEmpty().description("Nama harus diisi")
                        }
                        inputLayout(R.id.til_nip_register_guru) {
                            isNotEmpty().description("NIP harus diisi")
                        }
                        inputLayout(R.id.til_alamat_register_guru) {
                            isNotEmpty().description("Alamat harus diisi")
                        }
                        inputLayout(R.id.til_email_register_guru) {
                            isNotEmpty().description("Email harus diisi")
                        }

                        Log.d("sdsdsd", "cureesdsd bjhjkjkk ghghgh")

                        submitWith(R.id.btn_submit_register) {
                            dismissKeyboard()

                            Log.d("sdsdsd", "jalankan guru  ")

                            val username = edtUsernameRegisterGuru.text.toString()
                            val password = edtNamaRegisterGuru.text.toString()
                            val nama = edtNamaRegisterGuru.text.toString()
                            val nip = edtNipRegisterGuru.text.toString()
                            val alamat = edtAlamatRegisterGuru.text.toString()
                            val email = edtEmailRegisterGuru.text.toString()

                            viewModel.registerGuru(
                                RegisterGuruRequest(
                                    level = args.level,
                                    username = username,
                                    password = password,
                                    nama = nama,
                                    nip = nip,
                                    alamat = alamat,
                                    email = email,
                                )
                            )
                        }
                    }
                }
            }

            btnSubmitRegister.bindLifecycle(viewLifecycleOwner)
        }
    }

    private fun showProgress() = with(binding) {
        if (args.level == "siswa") {
            with(rowRegisterSiswa) {
                listOf(
                    btnSubmitRegister,
                    tilUsernameRegister,
                    edtUsernameRegister,
                    tilPasswordRegister,
                    edtPasswordRegister,
                    tilNamaRegister,
                    edtNamaRegister,
                    tilNisnRegister,
                    edtNisnRegister,
                    tilAlamatRegister,
                    edtAlamatRegister,
                    spinnerAgamaRegister,
                    spinnerAgamaAyahRegister,
                    spinnerAgamaIbuRegister,
                    spinnerKelasRegister,
                    spinnerPendidikanTerakhirAyahRegister,
                    spinnerPendidikanTerakhirIbuRegister,
                    tilTempatLahirRegister,
                    edtTempatLahirRegister,
                    tilTanggalLahirRegister,
                    edtTanggalLahirRegister,
                    tilAsalSekolahRegister,
                    edtAsalSekolahRegister,
                    tilStatusAsalSekolahRegister,
                    edtStatusAsalSekolahRegister,
                    tilNamaAyahRegister,
                    edtNamaAyahRegister,
                    tilUmurAyahRegister,
                    edtUmurAyahRegister,
                    tilPekerjaanAyahRegister,
                    edtPekerjaanAyahRegister,
                    tilNamaIbuRegister,
                    edtNamaIbuRegister,
                    tilUmurIbuRegister,
                    edtUmurIbuRegister
                ).forEach { it.isEnabled = false }

                btnSubmitRegister.showProgress()
            }
        } else {
            with(rowRegisterGuru) {
                listOf(
                    btnSubmitRegister,
                    tilUsernameRegisterGuru,
                    edtUsernameRegisterGuru,
                    tilPasswordRegisterGuru,
                    edtPasswordRegisterGuru,
                    tilPasswordRegisterGuru,
                    edtPasswordRegisterGuru,
                    tilNipRegisterGuru,
                    edtNipRegisterGuru,
                    tilAlamatRegisterGuru,
                    edtAlamatRegisterGuru,
                    tilEmailRegisterGuru,
                    edtEmailRegisterGuru
                ).forEach { it.isEnabled = false }

                btnSubmitRegister.showProgress()
            }
        }

    }

    private fun hideProgress(isEnable: Boolean) = with(binding) {
        if (args.level == "siswa") {
            with(rowRegisterSiswa) {
                btnSubmitRegister.postDelayed(
                    {
                        listOf(
                            btnSubmitRegister,
                            tilUsernameRegister,
                            edtUsernameRegister,
                            tilPasswordRegister,
                            edtPasswordRegister,
                            tilNamaRegister,
                            edtNamaRegister,
                            tilNisnRegister,
                            edtNisnRegister,
                            tilAlamatRegister,
                            edtAlamatRegister,
                            spinnerAgamaRegister,
                            spinnerAgamaAyahRegister,
                            spinnerAgamaIbuRegister,
                            spinnerKelasRegister,
                            spinnerPendidikanTerakhirAyahRegister,
                            spinnerPendidikanTerakhirIbuRegister,
                            tilTempatLahirRegister,
                            edtTempatLahirRegister,
                            tilTanggalLahirRegister,
                            edtTanggalLahirRegister,
                            tilAsalSekolahRegister,
                            edtAsalSekolahRegister,
                            tilStatusAsalSekolahRegister,
                            edtStatusAsalSekolahRegister,
                            tilNamaAyahRegister,
                            edtNamaAyahRegister,
                            tilUmurAyahRegister,
                            edtUmurAyahRegister,
                            tilPekerjaanAyahRegister,
                            edtPekerjaanAyahRegister,
                            tilNamaIbuRegister,
                            edtNamaIbuRegister,
                            tilUmurIbuRegister,
                            edtUmurIbuRegister
                        ).forEach { it.isEnabled = true }
                    }, 1000L
                )

                btnSubmitRegister.hideProgress(textBtnSubmit) {
                    isEnable && with(binding) {
                        "${edtNamaRegister.text}".isNotBlank() && "${edtNisnRegister.text}".isNotBlank()
                                && "${edtUsernameRegister.text}".isNotBlank()
                                && "${edtPasswordRegister.text}".isNotBlank()
                                && "${edtAlamatRegister.text}".isNotBlank()
                                && "${edtTempatLahirRegister.text}".isNotBlank()
                                && "${edtTempatLahirRegister.text}".isNotBlank() && "${edtTanggalLahirRegister.text}".isNotBlank()
                                && "${edtStatusAsalSekolahRegister.text}".isNotBlank() && "${edtNamaAyahRegister.text}".isNotBlank()
                                && "${edtUmurAyahRegister.text}".isNotBlank()
                                && "${edtPekerjaanAyahRegister.text}".isNotBlank() && "${edtNamaIbuRegister.text}".isNotBlank()
                                && "${edtUmurIbuRegister.text}".isNotBlank()
                    }
                }
            }
        } else {
            with(rowRegisterGuru) {
                btnSubmitRegister.postDelayed(
                    {
                        listOf(
                            btnSubmitRegister,
                            tilUsernameRegisterGuru,
                            edtUsernameRegisterGuru,
                            tilPasswordRegisterGuru,
                            edtPasswordRegisterGuru,
                            tilNamaRegisterGuru,
                            edtNamaRegisterGuru,
                            tilNipRegisterGuru,
                            edtNipRegisterGuru,
                            tilAlamatRegisterGuru,
                            edtAlamatRegisterGuru,
                            tilEmailRegisterGuru,
                            edtEmailRegisterGuru
                        ).forEach { it.isEnabled = true }
                    }, 1000L
                )

                btnSubmitRegister.hideProgress(textBtnSubmit) {
                    isEnable && with(binding) {
                        "${edtUsernameRegisterGuru.text}".isNotBlank() && "${edtPasswordRegisterGuru.text}".isNotBlank()
                                && "${edtNamaRegisterGuru.text}".isNotBlank() && "${edtNipRegisterGuru.text}".isNotBlank()
                                && "${edtAlamatRegisterGuru.text}".isNotBlank() && "${edtEmailRegisterGuru.text}".isNotBlank()
                    }
                }
            }
        }
    }

    private fun initSpinnerAgama() {
        agamaList.add("Islam")
        agamaList.add("Kristen Protestan")
        agamaList.add("Katolik")
        agamaList.add("Buddha")
        agamaList.add("Hindu")
        agamaList.add("Konghucu")

        binding.rowRegisterSiswa.spinnerAgamaRegister.apply {
            item = agamaList as MutableList<*>

            var index = -1

            for ((i, v) in agamaList.withIndex()) {
                if (v == viewModel.agamaId) {
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
                    Log.d("dfdfdf", "agamaList ${agamaList[position]}")
                    viewModel.agamaId = agamaList[position]
                }

                override fun onNothingSelected(adapterView: AdapterView<*>) {}
            }
        }
    }

    private fun initSpinnerAgamaAyah() {
        agamaAyahList.add("Islam")
        agamaAyahList.add("Kristen Protestan")
        agamaAyahList.add("Katolik")
        agamaAyahList.add("Buddha")
        agamaAyahList.add("Hindu")
        agamaAyahList.add("Konghucu")

        binding.rowRegisterSiswa.spinnerAgamaAyahRegister.apply {
            item = agamaAyahList as MutableList<*>

            var index = -1

            for ((i, v) in agamaAyahList.withIndex()) {
                if (v == viewModel.agamaAyahId) {
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
//                    Toast.makeText(context, agamaAyahList[position], Toast.LENGTH_SHORT).show()
                    Log.d("dfdfdf", "Dfdfdfdf agamaAyahList ${agamaAyahList[position]}")
                    viewModel.agamaAyahId = agamaAyahList[position]
                }

                override fun onNothingSelected(adapterView: AdapterView<*>) {}
            }
        }
    }

    private fun initSpinnerAgamaIbu() {
        agamaIbuList.add("Islam")
        agamaIbuList.add("Kristen Protestan")
        agamaIbuList.add("Katolik")
        agamaIbuList.add("Buddha")
        agamaIbuList.add("Hindu")
        agamaIbuList.add("Konghucu")

        binding.rowRegisterSiswa.spinnerAgamaIbuRegister.apply {
            item = agamaIbuList as MutableList<*>

            var index = -1

            for ((i, v) in agamaIbuList.withIndex()) {
                if (v == viewModel.agamaIbuId) {
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
//                    Toast.makeText(context, agamaIbuList[position], Toast.LENGTH_SHORT).show()
                    Log.d("dfdfdf", "agamaIbuList ${agamaIbuList[position]}")
                    viewModel.agamaIbuId = agamaIbuList[position]
                }

                override fun onNothingSelected(adapterView: AdapterView<*>) {}
            }
        }
    }

    private fun initSpinnerKelas() {
        kelasList.add("I IPA")
        kelasList.add("I IPS")
        kelasList.add("II IPA")
        kelasList.add("II IPS")
        kelasList.add("III IPA")
        kelasList.add("III IPS")

        binding.rowRegisterSiswa.spinnerKelasRegister.apply {
            item = kelasList as MutableList<*>

            var index = -1

            for ((i, v) in kelasList.withIndex()) {
                if (v == viewModel.kelas) {
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
//                    Toast.makeText(context, kelasList[position], Toast.LENGTH_SHORT).show()
                    Log.d("dfdfdf", "kelasList ${kelasList[position]}")
                    viewModel.kelas = kelasList[position]
                }

                override fun onNothingSelected(adapterView: AdapterView<*>) {}
            }
        }
    }

    private fun initSpinnerPendidikanAyah() {
        pendidikanAyahList.add("Tidak Sekolah")
        pendidikanAyahList.add("SD")
        pendidikanAyahList.add("SMP")
        pendidikanAyahList.add("SMA")
        pendidikanAyahList.add("D3/S1")
        pendidikanAyahList.add("S2")
        pendidikanAyahList.add("S3")

        binding.rowRegisterSiswa.spinnerPendidikanTerakhirAyahRegister.apply {
            item = pendidikanAyahList as MutableList<*>

            var index = -1

            for ((i, v) in pendidikanAyahList.withIndex()) {
                if (v == viewModel.pendidikanAyah) {
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
//                    Toast.makeText(context, pendidikanAyahList[position], Toast.LENGTH_SHORT).show()
                    Log.d("dfdfdf", "pendidikanAyahList ${pendidikanIbuList[position]}")
                    viewModel.pendidikanAyah = pendidikanAyahList[position]
                }

                override fun onNothingSelected(adapterView: AdapterView<*>) {}
            }
        }
    }

    private fun initSpinnerPendidikanIbu() {
        pendidikanIbuList.add("Tidak Sekolah")
        pendidikanIbuList.add("SD")
        pendidikanIbuList.add("SMP")
        pendidikanIbuList.add("SMA")
        pendidikanIbuList.add("D3/S1")
        pendidikanIbuList.add("S2")
        pendidikanIbuList.add("S3")

        binding.rowRegisterSiswa.spinnerPendidikanTerakhirIbuRegister.apply {
            item = pendidikanIbuList as MutableList<*>

            var index = -1

            for ((i, v) in pendidikanIbuList.withIndex()) {
                if (v == viewModel.pendidikanIbu) {
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
//                    Toast.makeText(context, pendidikanIbuList[position], Toast.LENGTH_SHORT).show()
                    Log.d("dfdfdf", "pendidikanIbuList ${pendidikanIbuList[position]}")
                    viewModel.pendidikanIbu = pendidikanIbuList[position]
                }

                override fun onNothingSelected(adapterView: AdapterView<*>) {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}