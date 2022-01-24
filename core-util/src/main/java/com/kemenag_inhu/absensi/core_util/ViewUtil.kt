package com.kemenag_inhu.absensi.core_util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.View
import android.provider.MediaStore

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

fun Double.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this.toString())

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun Unit.toEditable() : Editable = Editable.Factory.getInstance().newEditable(this.toString())

fun getRealPathFromURI(context: Context, contentUri: Uri?): String? {
    var cursor: Cursor? = null
    return try {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        cursor = context.contentResolver.query(contentUri!!, proj, null, null, null)
        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        cursor.getString(column_index)
    } finally {
        cursor?.close()
    }
}
