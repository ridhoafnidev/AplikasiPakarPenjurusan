package com.example.subfeature.nilai_penjurusan

import androidx.navigation.findNavController
import com.example.core_navigation.ModuleNavigator
import com.example.core_util.base.BaseActivity
import com.example.subfeature.nilai_penjurusan.databinding.ActivityNilaiPenjurusanBinding

class NilaiPenjurusanActivity : BaseActivity<ActivityNilaiPenjurusanBinding>(ActivityNilaiPenjurusanBinding::inflate), ModuleNavigator.NilaiPenjurusanNav  {

    private val navController by lazy { findNavController(R.id.nilai_penjurusan_navigation) }

    val level by levelParam()

    val userId by userIdParam()

    val isGuru
        get() = level == "guru"

    override fun initView() {
        initToolbar(back = true, primary = true)
        setPageName("Hasil Penjurusan")
    }

    override fun initListener() {

    }

    override fun onSupportNavigateUp() = navController.navigateUp()
}