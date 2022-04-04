package com.example.feature.profile

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.afollestad.vvalidator.form
import com.example.core_data.api.ApiEvent
import com.example.core_data.api.request.guru.UpdateGuruRequest
import com.example.core_data.domain.User
import com.example.core_data.domain.isGuru
import com.example.core_data.domain.isSiswa
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.core_util.*
import com.example.feature.auth.AuthViewModel
import com.example.feature.auth.GuruViewModel
import com.example.feature.auth.SiswaViewModel
import com.example.feature.profile.databinding.FragmentEditPersonalDataBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.time.LocalDate
import java.util.*

class EditPersonalDataFragment : BaseFragment<FragmentEditPersonalDataBinding>(
    FragmentEditPersonalDataBinding::inflate
),
    ModuleNavigator {

    private val viewModel by sharedViewModel<AuthViewModel>()
    private val guruViewModel by sharedViewModel<GuruViewModel>()
    private val siswaViewModel by sharedViewModel<SiswaViewModel>()
    private var agamaList = ArrayList<String>()
    private var agamaAyahList = ArrayList<String>()
    private var agamaIbuList = ArrayList<String>()
    private var kelasList = ArrayList<String>()
    private var pendidikanAyahList = ArrayList<String>()
    private var pendidikanIbuList = ArrayList<String>()

    lateinit var datePicker: DatePickerHelper

    private val args: EditPersonalDataFragmentArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.O)
    private var datePickerValue = LocalDate.MIN

    private val textBtnSubmit by lazy {
        "Submit";
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {
        setupInput()
        observeAuth()

        siswaViewModel.updateSiswa.observe(viewLifecycleOwner, { updateSiswa ->
            when (updateSiswa) {
                is ApiEvent.OnProgress -> {
                    showProgress()
                    Timber.d("progress ${updateSiswa.currentResult}")
                }
                is ApiEvent.OnSuccess -> {
                    hideProgress(true)
                    requireActivity().finish()
                }
                is ApiEvent.OnFailed -> {
                    Log.d("sdsdsd", "cureesdsd ${updateSiswa.getException().toString()}")
                    hideProgress(true)
                    Snackbar.make(
                        requireContext(), requireView(),
                        updateSiswa.getException().toString(), Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        })

        guruViewModel.updateGuru.observe(viewLifecycleOwner, { updateGuru ->
            when (updateGuru) {
                is ApiEvent.OnProgress -> {
                    showProgress()
                    Timber.d("progress ${updateGuru.currentResult}")
                }
                is ApiEvent.OnSuccess -> {
                    hideProgress(true)
                    requireActivity().finish()
                }
                is ApiEvent.OnFailed -> {
                    Log.d("sdsdsd", "cureesdsd ${updateGuru.getException().toString()}")
                    hideProgress(true)
                    Snackbar.make(
                        requireContext(), requireView(),
                        updateGuru.getException().toString(), Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    override fun initListener() {

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeAuth() {
        viewModel.auth.observe(viewLifecycleOwner) { data ->
            if (!data?.idUser.toString().isEmpty()) {
                observeDetail(data)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeDetail(data: User?) {
        if (data != null) {
            if (data.isSiswa) {
                viewModel.siswaDetail.observe(viewLifecycleOwner) { siswa ->
//                    Log.d("sdsdsd", "cureesdsd ${siswa!!.alamat}")
                    siswa?.let {
                        with(binding) {
                            siswaViewModel.agamaId = it.agama
                            siswaViewModel.agamaAyahId = it.agamaAyah
                            siswaViewModel.agamaIbuId = it.agamaIbu
                            siswaViewModel.kelas = it.kelas
                            siswaViewModel.pendidikanAyah = it.pendidikanTerakhirAyah
                            siswaViewModel.pendidikanIbu = it.pendidikanTerakhirIbu
                            siswaViewModel.tanggalLahir =
                                it.tanggalLahir.toLocalDate() withFormat getString(R.string.date_format_yyyy_MM_dd)
                            rowPersonalDataSiswaEdit.root.visibility = View.VISIBLE
                            rowPersonalDataGuruEdit.root.visibility = View.GONE
                            with(rowPersonalDataSiswaEdit) {
                                edtNamaSiswa.setText(it.nama)
                                edtNisn.setText(it.nisn)
                                edtAlamatSiswa.setText(it.alamat)
                                edtTempatLahir.setText(it.tempatLahir)
                                edtTanggalLahir.setText(
                                    it.tanggalLahir.toLocalDate() withFormat getString(
                                        R.string.date_format_d_MMM_yyyy
                                    )
                                )
                                edtAsalSekolah.setText(it.asalSekolah)
                                edtStatusAsalSekolah.setText(it.statusAsalSekolah)
                                edtNamaAyah.setText(it.namaAyah)
                                edtUmurAyah.setText(it.umurAyah)
                                edtPekerjaanAyah.setText(it.pekerjaanAyah)
                                edtNamaIbu.setText(it.namaIbu)
                                edtUmurIbu.setText(it.umurIbu)
                                edtPekerjaanIbu.setText(it.pekerjaanIbu)

                                edtTanggalLahir.setOnClickListener {
                                    datePickerShow()
                                }

                                Log.d("sdsdsd", "cureesdsdsd sdd ${siswaViewModel.tanggalLahir}")
                            }
                        }

                        initSpinnerAgama()
                        initSpinnerAgamaAyah()
                        initSpinnerAgamaIbu()
                        initSpinnerKelas()
                        initSpinnerPendidikanAyah()
                        initSpinnerPendidikanIbu()
                    }
                }
            } else if (data.isGuru) {
                viewModel.guruDetail.observe(viewLifecycleOwner) { guru ->
                    guru?.let {
                        with(binding) {
                            rowPersonalDataSiswaEdit.root.visibility = View.GONE
                            rowPersonalDataGuruEdit.root.visibility = View.VISIBLE
                            with(rowPersonalDataGuruEdit) {
                                edtNama.setText(it.nama)
                                edtNip.setText(it.nip)
                                edtAlamat.setText(it.alamat)
                                edtEmail.setText(it.email)
                            }
                        }
                    }

                    Log.d("sdsdsd", "cureesdsd ${guru!!.alamat}")
                }
            }
        }
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

                with(binding.rowPersonalDataSiswaEdit) {
                    siswaViewModel.tanggalLahir =
                        datePickerValue withFormat getString(R.string.date_format_yyyy_MM_dd)
                    edtTanggalLahir.setText(datePickerValue withFormat getString(R.string.date_format_d_MMM_yyyy))
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupInput() {
        Log.d("sdsdsd", "cureesdsd level nya adalah ${args.level}")
        with(binding) {
            if (args.level == "siswa") {
                with(rowPersonalDataSiswaEdit) {
                    form {
                        useRealTimeValidation(disableSubmit = true)
                        inputLayout(R.id.til_nama_siswa) {
                            isNotEmpty().description("Nama harus diisi")
                        }
                        inputLayout(R.id.til_nisn) {
                            isNotEmpty().description("NISN harus diisi")
                        }
                        inputLayout(R.id.til_alamat_siswa) {
                            isNotEmpty().description("Alamat harus diisi")
                        }
                        spinner(R.id.spinner_kelas) {
                            selection()
                                .greaterThan(0)
                                .description("Kelas Harus diisi")
                        }
                        inputLayout(R.id.til_tempat_lahir) {
                            isNotEmpty().description("Tempat lahir harus diisi")
                        }
                        inputLayout(R.id.til_tanggal_lahir) {
                            isNotEmpty().description("Tanggal lahir harus diisi")
                        }
                        inputLayout(R.id.til_asal_sekolah) {
                            isNotEmpty().description("Asal sekolah harus diisi")
                        }
                        inputLayout(R.id.til_status_asal_sekolah) {
                            isNotEmpty().description("Status asal sekolah harus diisi")
                        }
                        inputLayout(R.id.til_nama_ayah) {
                            isNotEmpty().description("Nama ayah harus diisi")
                        }
                        inputLayout(R.id.til_umur_ayah) {
                            isNotEmpty().description("Umur harus diisi")
                        }
                        inputLayout(R.id.til_pekerjaan_ayah) {
                            isNotEmpty().description("Pekerjaan ayah harus diisi")
                        }
                        inputLayout(R.id.til_nama_ibu) {
                            isNotEmpty().description("Nama ibu harus diisi")
                        }
                        inputLayout(R.id.til_umur_ibu) {
                            isNotEmpty().description("Umur ibu harus diisi")
                        }
                        inputLayout(R.id.til_pekerjaan_ibu) {
                            isNotEmpty().description("Umur ibu harus diisi")
                        }

                        Log.d("sdsdsd", "cureesdsd bjhjkjkk kkmkm")

                        submitWith(R.id.btn_submit) {
                            dismissKeyboard()

                            val nama = edtNamaSiswa.text.toString()
                            val nisn = edtNisn.text.toString()
                            val alamat = edtAlamatSiswa.text.toString()
                            val tempatLahir = edtTempatLahir.text.toString()
                            val asalSekolah = edtAsalSekolah.text.toString()
                            val statusAsalSekolah = edtStatusAsalSekolah.text.toString()
                            val namaAyah = edtNamaAyah.text.toString()
                            val umurAyah = edtUmurAyah.text.toString()
                            val pekerjaanAyah = edtPekerjaanAyah.text.toString()
                            val namaIbu = edtNamaIbu.text.toString()
                            val umurIbu = edtUmurIbu.text.toString()
                            val pekerjaanIbu = edtPekerjaanIbu.text.toString()

                            Log.d("sdsdsd", "jalankan murid  ")

                            siswaViewModel.updateSiswa(
                                idUser = args.idUser.toInt(),
                                nisn = nisn,
                                nama = nama,
                                alamat = alamat,
                                asalSekolah = asalSekolah,
                                statusAsalSekolah = statusAsalSekolah,
                                namaAyah = namaAyah,
                                umurAyah = umurAyah,
                                pekerjaanAyah = pekerjaanAyah,
                                namaIbu = namaIbu,
                                umurIbu = umurIbu,
                                pekerjaanIbu = pekerjaanIbu,
                                tempatLahir = tempatLahir
                            )
                        }
                    }
                }
            } else if (args.level == "guru"){
                with(rowPersonalDataGuruEdit) {
                    form {
                        useRealTimeValidation(disableSubmit = true)
                        inputLayout(R.id.til_nama) {
                            isNotEmpty().description("Nama harus diisi")
                        }
                        inputLayout(R.id.til_nip) {
                            isNotEmpty().description("NIP harus diisi")
                        }
                        inputLayout(R.id.til_alamat) {
                            isNotEmpty().description("Alamat harus diisi")
                        }
                        inputLayout(R.id.til_email) {
                            isNotEmpty().description("Email harus diisi")
                        }

                        Log.d("sdsdsd", "cureesdsd bjhjkjkk ghghgh")

                        submitWith(R.id.btn_submit) {
                            dismissKeyboard()

                            Log.d("sdsdsd", "jalankan guru  ")

                            val nama = edtNama.text.toString()
                            val nip = edtNip.text.toString()
                            val alamat = edtAlamat.text.toString()
                            val email = edtEmail.text.toString()

                            guruViewModel.updateGuru(
                                args.idUser.toInt(),
                                UpdateGuruRequest(
                                    nama = nama,
                                    nip = nip,
                                    alamat = alamat,
                                    email = email
                                )
                            )
                        }
                    }
//                    btnSubmit.bindLifecycle(viewLifecycleOwner)
                }
            }

            btnSubmit.bindLifecycle(viewLifecycleOwner)
        }
    }

    private fun showProgress() = with(binding) {
        if (args.level == "siswa") {
            with(rowPersonalDataSiswaEdit) {
                listOf(
                    btnSubmit, tilNamaSiswa, edtNamaSiswa, tilNisn, edtNisn,
                    tilAlamatSiswa, edtAlamatSiswa, spinnerAgama, spinnerAgamaAyah, spinnerAgamaIbu,
                    spinnerKelas, spinnerPendidikanTerakhirAyah, spinnerPendidikanTerakhirIbu,
                    tilTempatLahir, edtTempatLahir, tilTanggalLahir,
                    edtTanggalLahir, tilAsalSekolah, edtAsalSekolah,
                    tilStatusAsalSekolah, edtStatusAsalSekolah, tilNamaAyah, edtNamaAyah,
                    tilUmurAyah, edtUmurAyah,
                    tilPekerjaanAyah, edtPekerjaanAyah, tilNamaIbu, edtNamaIbu,
                    tilUmurIbu, edtUmurIbu
                ).forEach { it.isEnabled = false }

                btnSubmit.showProgress()
            }
        } else {
            with(rowPersonalDataGuruEdit) {
                listOf(
                    btnSubmit, tilNama, edtNama, tilNip, edtNip,
                    tilAlamat, edtAlamat, tilEmail, edtEmail
                ).forEach { it.isEnabled = false }

                btnSubmit.showProgress()
            }
        }

    }

    private fun hideProgress(isEnable: Boolean) = with(binding) {
        if (args.level == "siswa") {
            with(rowPersonalDataSiswaEdit) {
                btnSubmit.postDelayed(
                    {
                        listOf(
                            btnSubmit,
                            tilNamaSiswa,
                            edtNamaSiswa,
                            tilNisn,
                            edtNisn,
                            tilAlamatSiswa,
                            edtAlamatSiswa,
                            spinnerAgama,
                            spinnerAgamaAyah,
                            spinnerAgamaIbu,
                            spinnerKelas,
                            spinnerPendidikanTerakhirAyah,
                            spinnerPendidikanTerakhirIbu,
                            tilTempatLahir,
                            edtTempatLahir,
                            tilTanggalLahir,
                            edtTanggalLahir,
                            tilAsalSekolah,
                            edtAsalSekolah,
                            tilStatusAsalSekolah,
                            edtStatusAsalSekolah,
                            tilNamaAyah,
                            edtNamaAyah,
                            tilUmurAyah,
                            edtUmurAyah,
                            tilPekerjaanAyah,
                            edtPekerjaanAyah,
                            tilNamaIbu,
                            edtNamaIbu,
                            tilUmurIbu,
                            edtUmurIbu
                        ).forEach { it.isEnabled = true }
                    }, 1000L
                )

                btnSubmit.hideProgress(textBtnSubmit) {
                    isEnable && with(binding) {
                        "${edtNamaSiswa.text}".isNotBlank() && "${edtNisn.text}".isNotBlank()
                                && "${edtAlamatSiswa.text}".isNotBlank()
                                && "${edtTempatLahir.text}".isNotBlank()
                                && "${edtTempatLahir.text}".isNotBlank() && "${edtTanggalLahir.text}".isNotBlank()
                                && "${edtStatusAsalSekolah.text}".isNotBlank() && "${edtNamaAyah.text}".isNotBlank()
                                && "${edtUmurAyah.text}".isNotBlank()
                                && "${edtPekerjaanAyah.text}".isNotBlank() && "${edtNamaIbu.text}".isNotBlank()
                                && "${edtUmurIbu.text}".isNotBlank()
                    }
                }
            }
        } else {
            with(rowPersonalDataGuruEdit) {
                btnSubmit.postDelayed(
                    {
                        listOf(
                            btnSubmit, tilNama, edtNama, tilNip, edtNip,
                            tilAlamat, edtAlamat, tilEmail, edtEmail
                        ).forEach { it.isEnabled = true }
                    }, 1000L
                )

                btnSubmit.hideProgress(textBtnSubmit) {
                    isEnable && with(binding) {
                        "${edtNama.text}".isNotBlank() && "${edtNip.text}".isNotBlank()
                                && "${edtAlamat.text}".isNotBlank() && "${edtEmail.text}".isNotBlank()
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

        binding.rowPersonalDataSiswaEdit.spinnerAgama.apply {
            item = agamaList as MutableList<*>

            var index = -1

            for ((i, v) in agamaList.withIndex()) {
                if (v == siswaViewModel.agamaId) {
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
                    Toast.makeText(context, agamaList[position], Toast.LENGTH_SHORT).show()
                    Log.d("dfdfdf", "agamaList ${agamaList[position]}")
                    siswaViewModel.agamaId = agamaList[position]
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

        binding.rowPersonalDataSiswaEdit.spinnerAgamaAyah.apply {
            item = agamaAyahList as MutableList<*>

            var index = -1

            for ((i, v) in agamaAyahList.withIndex()) {
                if (v == siswaViewModel.agamaAyahId) {
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
                    Toast.makeText(context, agamaAyahList[position], Toast.LENGTH_SHORT).show()
                    Log.d("dfdfdf", "Dfdfdfdf agamaAyahList ${agamaAyahList[position]}")
                    siswaViewModel.agamaAyahId = agamaAyahList[position]
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

        binding.rowPersonalDataSiswaEdit.spinnerAgamaIbu.apply {
            item = agamaIbuList as MutableList<*>

            var index = -1

            for ((i, v) in agamaIbuList.withIndex()) {
                if (v == siswaViewModel.agamaIbuId) {
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
                    Toast.makeText(context, agamaIbuList[position], Toast.LENGTH_SHORT).show()
                    Log.d("dfdfdf", "agamaIbuList ${agamaIbuList[position]}")
                    siswaViewModel.agamaIbuId = agamaIbuList[position]
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

        binding.rowPersonalDataSiswaEdit.spinnerKelas.apply {
            item = kelasList as MutableList<*>

            var index = -1

            for ((i, v) in kelasList.withIndex()) {
                if (v == siswaViewModel.kelas) {
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
                    Toast.makeText(context, kelasList[position], Toast.LENGTH_SHORT).show()
                    Log.d("dfdfdf", "kelasList ${kelasList[position]}")
                    siswaViewModel.kelas = kelasList[position]
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

        binding.rowPersonalDataSiswaEdit.spinnerPendidikanTerakhirAyah.apply {
            item = pendidikanAyahList as MutableList<*>

            var index = -1

            for ((i, v) in pendidikanAyahList.withIndex()) {
                if (v == siswaViewModel.pendidikanAyah) {
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
                    Toast.makeText(context, pendidikanAyahList[position], Toast.LENGTH_SHORT).show()
                    Log.d("dfdfdf", "pendidikanAyahList ${pendidikanIbuList[position]}")
                    siswaViewModel.pendidikanAyah = pendidikanAyahList[position]
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

        binding.rowPersonalDataSiswaEdit.spinnerPendidikanTerakhirIbu.apply {
            item = pendidikanIbuList as MutableList<*>

            var index = -1

            for ((i, v) in pendidikanIbuList.withIndex()) {
                if (v == siswaViewModel.pendidikanIbu) {
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
                    Toast.makeText(context, pendidikanIbuList[position], Toast.LENGTH_SHORT).show()
                    Log.d("dfdfdf", "pendidikanIbuList ${pendidikanIbuList[position]}")
                    siswaViewModel.pendidikanIbu = pendidikanIbuList[position]
                }

                override fun onNothingSelected(adapterView: AdapterView<*>) {}
            }
        }
    }
}