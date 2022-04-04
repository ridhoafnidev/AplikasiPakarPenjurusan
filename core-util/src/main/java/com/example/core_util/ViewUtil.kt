package com.example.core_util

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.OpenableColumns
import android.view.View
import androidx.fragment.app.Fragment

private var selected = false
private var imagePath = ""

fun View.setSingleOnClickListener(callback: () -> Unit) {
    this.setOnClickListener {
        this.isEnabled = false
        Handler(Looper.getMainLooper()).postDelayed({ this.isEnabled = true }, 1000)
        callback.invoke()
    }
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}

@SuppressLint( "WrongConstant", "Range")
fun Fragment.convertImagePath(data: Intent, selectedImage: Uri, filePathColumn: Array<String>): String{
    if (this != null && selectedImage != null) {
        var cursor: Cursor? = requireActivity().contentResolver.query(
            selectedImage,
            null, null, null, null
        )
        selected = true
        if (cursor != null) {
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            imagePath = cursor.getString(nameIndex)
            cursor?.close()
        }
    }
    return imagePath
}
