package com.kemenag_inhu.absensi.core_util.utility

import android.view.View
import com.google.android.material.snackbar.BaseTransientBottomBar

internal class NoSwipe : BaseTransientBottomBar.Behavior() {
    override fun canSwipeDismissView(child: View):Boolean {
        return false
    }
}