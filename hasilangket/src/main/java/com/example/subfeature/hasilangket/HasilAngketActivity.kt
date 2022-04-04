package com.example.subfeature.hasilangket

import androidx.navigation.findNavController
import com.example.core_util.base.BaseActivity
import com.example.subfeature.hasilangket.databinding.ActivityHasilAngketBinding

class HasilAngketActivity : BaseActivity<ActivityHasilAngketBinding>(ActivityHasilAngketBinding::inflate) {

    private val navController by lazy { findNavController(R.id.hasil_angket_navigation) }

    override fun initView() {
        initToolbar(back = true, primary = true)
        setPageName("Hasil Angket")
    }

    override fun initListener() {
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

}