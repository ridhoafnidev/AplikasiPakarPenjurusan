package com.kemenag_inhu.absensi.feature.home.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.afollestad.recyclical.ViewHolder
import com.kemenag_inhu.home.R

class ItemMenuViewHolder(view: View) : ViewHolder(view) {
    val titleMenu: TextView = view.findViewById(R.id.tv_title_menu)
    val ivMenu: ImageView = view.findViewById(R.id.iv_menu)
}