package com.kemenag_inhu.absensi.feature.home.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.afollestad.recyclical.ViewHolder
import com.kemenag_inhu.home.R

class ItemHistoryAttendanceViewHolder(view: View) : ViewHolder(view) {
    val dayAttendance: TextView = view.findViewById(R.id.tv_day_attendance)
    val dateAttendance: TextView = view.findViewById(R.id.tv_date_attendance)
    val timeAttendent: TextView = view.findViewById(R.id.tv_time_attendent)
    val clockInOut: TextView = view.findViewById(R.id.tv_title_clock_in_out)
}