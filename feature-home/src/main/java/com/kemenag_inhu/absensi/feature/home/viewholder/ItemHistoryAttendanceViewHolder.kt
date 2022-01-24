package com.kemenag_inhu.absensi.feature.home.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.afollestad.recyclical.ViewHolder
import com.kemenag_inhu.home.R

class ItemNewEventViewHolder(view: View) : ViewHolder(view) {
    val mounthAttendance: TextView = view.findViewById(R.id.tv_mounth_attendance)
    val placeEvent: TextView = view.findViewById(R.id.tv_place_new_event)
    val imageEvent: ImageView = view.findViewById(R.id.iv_event)
}