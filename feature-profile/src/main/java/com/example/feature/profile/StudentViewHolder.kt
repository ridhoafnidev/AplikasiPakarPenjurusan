package com.example.feature.profile

import android.view.View
import android.widget.TextView
import com.afollestad.recyclical.ViewHolder

class StudentViewHolder (view: View): ViewHolder(view) {
    val tvName: TextView = view.findViewById(R.id.tv_label_name)
}