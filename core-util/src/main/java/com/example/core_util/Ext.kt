package com.example.core_util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
infix fun LocalDate.withFormat(pattern: String): String =
    withFormat(pattern, Locale.getDefault())

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.withFormat(pattern: String, locale: Locale = Locale.getDefault()): String =
    format(DateTimeFormatter.ofPattern(pattern, locale))

@RequiresApi(Build.VERSION_CODES.O)
fun String.toLocalDate(): LocalDate = LocalDate.parse(this)