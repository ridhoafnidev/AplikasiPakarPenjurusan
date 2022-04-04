package com.example.subfeature.hasilangket

import android.view.View
import android.widget.TextView
import com.afollestad.recyclical.ViewHolder

class LastResultViewHolder(view: View): ViewHolder(view) {
    val tvName: TextView = view.findViewById(R.id.tv_label_name)
    val tvResult: TextView = view.findViewById(R.id.tv_result)
}