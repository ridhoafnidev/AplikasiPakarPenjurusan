package com.example.subfeature.nilai_siswa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import com.example.core_navigation.ModuleNavigator
import com.example.core_util.base.BaseActivity
import com.example.subfeature.nilai_siswa.databinding.ActivityNilaiSiswaBinding

class NilaiSiswaActivity : BaseActivity<ActivityNilaiSiswaBinding>(ActivityNilaiSiswaBinding::inflate), ModuleNavigator.NilaiSiswaNav  {

    private val navController by lazy { findNavController(R.id.nilai_siswa_navigation) }

    val level by levelParam()

    override fun initView() {
        initToolbar(back = true, primary = true)
        setPageName("Nilai Siswa")
    }

    override fun initListener() {

    }

    override fun onSupportNavigateUp() = navController.navigateUp()
}