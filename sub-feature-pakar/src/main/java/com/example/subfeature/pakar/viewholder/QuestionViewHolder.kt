package com.example.subfeature.pakar.viewholder

import android.view.View
import android.widget.CheckBox
import com.afollestad.recyclical.ViewHolder
import com.example.subfeature.pakar.R

class QuestionViewHolder(view: View) : ViewHolder(view){
    val titleCheckBox: CheckBox = view.findViewById(R.id.cb_question)
}
