package com.example.core_navigation

import android.content.Context
import android.content.Intent

enum class ActivityClassPath(private val className: String) {
    Auth("$BASE_PATH.auth.AuthActivity"),
    Home("$BASE_PATH.home.HomeActivity"),
    Profile("$BASE_PATH.profile.ProfileActivity"),
    Pakar("$SUB_PATH.pakar.PakarActivity"),
    HasilAngket("$SUB_PATH.hasilangket.HasilAngketActivity"),
    NilaiSiswa("$SUB_PATH.nilai_siswa.NilaiSiswaActivity"),
    NilaiPenjurusan("$SUB_PATH.nilai_penjurusan.NilaiPenjurusanActivity");

    fun getIntent(context: Context) = Intent(context, Class.forName(className))
}

private const val BASE_PATH = "com.example.feature"
private const val SUB_PATH = "com.example.subfeature"