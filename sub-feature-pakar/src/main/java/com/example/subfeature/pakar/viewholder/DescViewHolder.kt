package com.example.subfeature.pakar.viewholder

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.afollestad.recyclical.ViewHolder
import com.example.subfeature.pakar.R

class DescViewHolder(view: View) : ViewHolder(view){
    val title: TextView = view.findViewById(R.id.title)
    val desc: TextView = view.findViewById(R.id.desc)
}
