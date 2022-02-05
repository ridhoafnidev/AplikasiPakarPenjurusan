package com.example.subfeature.pakar

import androidx.annotation.MenuRes
import androidx.navigation.findNavController
import com.example.core_util.base.BaseActivity
import com.example.subfeature.pakar.databinding.ActivityPakarBinding

class PakarActivity : BaseActivity<ActivityPakarBinding>(ActivityPakarBinding::inflate) {

    private val navController by lazy { findNavController(R.id.pakar_navigation) }
    private val toolbar by lazy { binding.componentToolbar.toolbar }

    override fun initView() {
        initToolbar(back = true)
        setPageName("Pakar")
    }

    private fun appendMenu(@MenuRes menu: Int) {
        toolbar.inflateMenu(menu)
    }

    override fun initListener() {
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

}