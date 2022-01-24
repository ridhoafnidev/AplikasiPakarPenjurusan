package com.kemenag_inhu.absensi.core_util

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import java.util.*

fun AppCompatActivity.setSystemBarColor(@ColorRes color: Int){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window: Window = this.getWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this, color)
    }
}

fun Fragment.setSnapHelper(recycler: RecyclerView){
    LinearSnapHelper().attachToRecyclerView(recycler)
}

fun Fragment.showSnackBar(context: Context, layoutParent: View, message: String, duration: Int = Snackbar.LENGTH_SHORT, actionText: String = "", actionListener: Runnable? = null) {
    val mSnackBar = Snackbar.make(layoutParent, message, duration)

    if(message.contains("Unable to resolve")) mSnackBar.behavior = NoSwipe()

    mSnackBar.view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTextPrimary))

    val textViewAction = mSnackBar.view.findViewById(com.google.android.material.R.id.snackbar_action) as TextView
    val textView = mSnackBar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView

    textView.setTypeface(Typeface.createFromAsset(context.assets, Constants.View.Name[0] + Constants.View.Style[0] + Constants.View.Type), Typeface.NORMAL)
    textView.setTextColor(ContextCompat.getColor(context, R.color.colorButtonTextPrimary))
    textView.gravity = Gravity.CENTER
    textView.textSize = 11.5f
    textView.maxLines = 2

    if(actionText != "") {

        textViewAction.setTypeface(Typeface.createFromAsset(context.assets, Constants.View.Name[0] + Constants.View.Style[1] + Constants.View.Type), Typeface.NORMAL)
        textViewAction.setTextColor(ContextCompat.getColor(context, R.color.colorTextPrimary))
        textViewAction.gravity = Gravity.CENTER
        textViewAction.background = ContextCompat.getDrawable(context, R.drawable.button_primary)
        textViewAction.textSize = 11.5f

        mSnackBar.setAction(actionText) { actionListener?.run() }
    }

    Handler(Looper.getMainLooper()).postDelayed({
        mSnackBar.show()
    }, (if(actionText != "") 500 else 0).toLong())
}

fun dayTimeGreeting(context: Context) : String {
    val cal = Calendar.getInstance()

    cal.time = Date()

    return when (cal.get(Calendar.HOUR_OF_DAY)) {
        in 12..16 -> context.getString(R.string.text_good_afternoon)
        in 17..20 -> context.getString(R.string.text_good_evening)
        in 21..23 -> context.getString(R.string.text_good_night)
        else -> context.getString(R.string.text_good_morning)
    }

}