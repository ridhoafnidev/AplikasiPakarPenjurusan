package com.example.subfeature.hasilangket

import androidx.annotation.MenuRes
import androidx.navigation.findNavController
import com.example.core_util.base.BaseActivity
import com.example.subfeature.hasilangket.databinding.ActivityHasilAngketBinding

class HasilAngketActivity : BaseActivity<ActivityHasilAngketBinding>(ActivityHasilAngketBinding::inflate) {

    private val navController by lazy { findNavController(R.id.hasil_angket_navigation) }
    //private val toolbar by lazy { binding.componentToolbar.toolbar }

    override fun initView() {
        initToolbar(back = true)
        setPageName("Hasil Angket")
    }

//    private fun appendMenu(@MenuRes menu: Int) {
//        toolbar.inflateMenu(menu)
//    }

    override fun initListener() {
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

}