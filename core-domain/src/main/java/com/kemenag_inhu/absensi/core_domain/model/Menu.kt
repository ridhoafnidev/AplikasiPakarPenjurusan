package com.kemenag_inhu.absensi.core_domain.model

import androidx.annotation.DrawableRes

data class Menu(val id: Int, val title: String, @DrawableRes val image: Int)

typealias ListMenu = List<Menu>