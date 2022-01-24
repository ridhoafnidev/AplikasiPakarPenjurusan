package com.kemenag_inhu.absensi.core_util

import androidx.lifecycle.LifecycleOwner
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.android.material.button.MaterialButton

fun MaterialButton.bindLifecycle(lifecycleOwner: LifecycleOwner){
    lifecycleOwner.bindProgressButton(this)
}

fun MaterialButton.showProgress(){
    setTag(R.id.button_text, text)
    showProgress { progressColor = currentTextColor }
    isEnabled = false
}

fun MaterialButton.hideProgress(text: String? = null, setEnabled: () -> Boolean = { true }) {
    hideProgress(text ?: getTag(R.id.button_text)?.toString() ?: "")
    isEnabled = setEnabled()
}