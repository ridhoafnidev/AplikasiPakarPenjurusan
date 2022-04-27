package com.example.feature.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.example.core_data.APP_GURU_IMAGES_URL
import com.example.core_data.APP_SISWA_IMAGES_URL
import com.example.core_data.api.ApiEvent
import com.example.core_data.clearAppData
import com.example.core_data.domain.User
import com.example.core_data.domain.isGuru
import com.example.core_data.domain.isSiswa
import com.example.core_data.removeAll
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.core_util.convertImagePath
import com.example.core_util.gone
import com.example.core_util.visible
import com.example.feature.auth.AuthViewModel
import com.example.feature.auth.GuruViewModel
import com.example.feature.auth.SiswaViewModel
import com.example.home.R
import com.example.home.databinding.FragmentProfileBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.net.CookieHandler

class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate,
    R.string.title_profile
), ModuleNavigator {

    private val cookieHandler: CookieHandler by inject()

    private val viewModel by sharedViewModel<AuthViewModel>()
    private val guruViewModel by sharedViewModel<GuruViewModel>()
    private val siswaViewModel by sharedViewModel<SiswaViewModel>()
    private var idUser = ""
    private var level = ""
    var photo = ""
    var name = ""
    var nipNisn = ""
    var alamat = ""
    var email = ""
    var asalSekolah = ""

    private var imagePath: String? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun initView() {
        observeAuth()

        binding.rowProfileHeader.ivPhoto.setOnClickListener {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                Log.d("ksdskd", "sdsdjsd 1")
                navigateToGallery()
            } else {
                if (isCameraPermissionGranted()) {
                    Log.d("ksdskd", "sdsdjsd 2")
                    navigateToGallery()
                } else {
                    Log.d("ksdskd", "sdsdjsd 3")
                    requestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }

        binding.rowMenuProfile.rowDataPribadi.idRowDataPribadi.setOnClickListener {
            navigateToProfileActivity(status = "Personal Data")
        }

        binding.rowMenuProfile.rowDataSiswa.idRowDataSiswa.setOnClickListener {
            navigateToProfileActivity(status = "Data Siswa")
        }

        binding.rowMenuProfile.rowGantiPassword.idGantiPassword.setOnClickListener {
            navigateToProfileActivity(status = "Password")
        }

        binding.rowMenuProfile.rowTentangAplikasi.idRowTentangAplikasi.setOnClickListener {
            navigateToProfileActivity(status = "About")
        }

        binding.rowMenuProfile.rowBantuan.idBantuan.setOnClickListener {
            navigateToProfileActivity(status = "Help")
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
                viewModel.getSiswaById(idUser.toInt())
                viewModel.siswaRequest.observe(viewLifecycleOwner) { siswa ->
//                    Log.d("sdsdsd", "cureesdsd ${siswa!!.alamat}")
                    when (siswa) {
                        is ApiEvent.OnProgress -> {
                            Timber.d("progress ${siswa.currentResult}")
                        }
                        is ApiEvent.OnSuccess -> {
                            siswa.getData()?.let {
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

                                    if (it.foto.isNotEmpty()) {
                                        Log.d(
                                            "jghghg",
                                            "gytiuiuh ${APP_SISWA_IMAGES_URL + it.foto}"
                                        )
                                        binding.rowProfileHeader.ivPhoto.load(APP_SISWA_IMAGES_URL + it.foto) {
                                            crossfade(true)
                                            transformations(CircleCropTransformation())
                                        }
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
            } else if (data.isGuru) {
                viewModel.getGuruById(idUser.toInt())

                viewModel.guruRequest.observe(viewLifecycleOwner) { guru ->
                    when (guru) {
                        is ApiEvent.OnProgress -> {
                            Timber.d("progress ${guru.currentResult}")
                        }
                        is ApiEvent.OnSuccess -> {
                            guru.getData()?.let {
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

                                    if (it.foto.isNotEmpty()) {
                                        Log.d("jghghg", "gytiuiuh ${APP_GURU_IMAGES_URL + it.foto}")
                                        binding.rowProfileHeader.ivPhoto.load(APP_GURU_IMAGES_URL + it.foto) {
                                            crossfade(true)
                                            transformations(CircleCropTransformation())
                                        }
                                    }
                                }
                            }
                        }
                        is ApiEvent.OnFailed -> {
                            Snackbar.make(
                                requireContext(), requireView(),
                                guru.getException().toString(), Snackbar.LENGTH_SHORT
                            ).show()
                            Log.d("sdsdsd", "cureesdsd ${guru.getException().toString()}")
                        }
                    }

                }
            }
        }
    }

    private fun observeAuth() {
        viewModel.auth.observe(viewLifecycleOwner) { data ->
            if (!data?.idUser.toString().isEmpty()) {
                idUser = data?.idUser.toString()
                level = data?.level.toString()
                viewModel.idUser = idUser

                if (data != null) {
                    if (data.isGuru) {
                        binding.rowMenuProfile.rowDataSiswa.root.visible()
                    } else {
                        binding.rowMenuProfile.rowDataSiswa.root.gone()
                    }
                }

                observeDetail(data)
            }
        }
    }

    override fun initListener() {
    }

    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun navigateToGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
        }
        resultPickFoto.launch(intent)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private val resultPickFoto =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val imageUri: Uri? = result.data?.data
            val filePathColumn = arrayOf(MediaStore.Images.Media._ID)
            binding.rowProfileHeader.ivPhoto.load(imageUri) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            if (imageUri != null && result.data != null) {
                imagePath = convertImagePath(result?.data!!, imageUri, filePathColumn)

                if (level == "siswa") {
                    siswaViewModel.updateFotoSiswa(
                        idUser.toInt(),
                        imagePath!!,
                        imageUri,
                        requireActivity().contentResolver,
                        requireContext()
                    )
                } else if (level == "guru") {
                    Log.d(
                        "sdsdsd", "sdsdsddsd ${imagePath!!} dan $imageUri!!" +
                                " ${requireActivity().contentResolver}"
                    )
                    guruViewModel.updateFotoGuru(
                        idUser.toInt(),
                        imagePath!!,
                        imageUri,
                        requireActivity().contentResolver,
                        requireContext()
                    )
                }
            }
        }

    @RequiresApi(Build.VERSION_CODES.Q)
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permission ->
            if (permission) {
                navigateToGallery()
            } else {
                Toast.makeText(requireContext(), "No Permission Granted", Toast.LENGTH_SHORT).show()
            }
        }
}