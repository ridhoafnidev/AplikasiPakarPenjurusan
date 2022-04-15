package com.kemenag_inhu.absensi.core_util.utility

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class BaseDateTime {

    companion object {
        const val DATE_FORMAT = "dd MMM yyyy"
        const val TIME_FORMAT = "HH:mm"

        inline val LOCALE_IND: Locale
            get() = Locale("in", "ID")

        inline val TODAY: LocalDate
            @SuppressLint("NewApi")
            get() = LocalDate.now()

        @SuppressLint("NewApi")
        fun currentDate() = LocalDate.now()

        @SuppressLint("NewApi")
        infix fun LocalDate.currentDate(pattern: String){
            withFormat(pattern, LOCALE_IND)

        }

        infix fun convertTo12H(time: String): String {
            val code12Hours = SimpleDateFormat("hh:mm")
            var dateCode12: Date? = null
            var formatTwelve: String? = null
            var result = ""
            try {
                dateCode12 = code12Hours.parse(time)
            }
            catch (e: ParseException){
                e.printStackTrace()
            }

            formatTwelve = code12Hours.format(dateCode12)

            result = if (formatTwelve.equals(time)){
                formatTwelve + " AM"
            }
            else {
                formatTwelve + " PM"
            }

            return result
        }

        @SuppressLint("NewApi")
        infix fun LocalTime.withIndoFormat(pattern: String) =
            withFormat(pattern, LOCALE_IND)

        @SuppressLint("NewApi")
        infix fun LocalTime.withIndoFormat(style: FormatStyle) =
            withFormat(style, LOCALE_IND)

        @SuppressLint("NewApi")
        fun LocalTime.withFormat(style: FormatStyle, local: Locale = Locale.getDefault()) : String =
            format(DateTimeFormatter.ofLocalizedTime(style).withLocale(local))

        @SuppressLint("NewApi")
        fun LocalTime.withFormat(pattern: String, locale: Locale = Locale.getDefault()) : String =
            format(DateTimeFormatter.ofPattern(pattern, locale))

        // region Date

        //region Formatting

        infix fun LocalDate.withIndFormat(pattern: String): String =
            withFormat(pattern, LOCALE_IND)

        @SuppressLint("NewApi")
        infix fun LocalDate.withIndFormat(style: FormatStyle): String =
            withFormat(style, LOCALE_IND)

        infix fun LocalDate.withFormat(pattern: String): String =
            withFormat(pattern, Locale.getDefault())

        @SuppressLint("NewApi")
        fun LocalDate.withFormat(pattern: String, locale: Locale = Locale.getDefault()): String =
            format(DateTimeFormatter.ofPattern(pattern, locale))

        @SuppressLint("NewApi")
        infix fun LocalDate.withFormat(style: FormatStyle): String =
            withFormat(style, Locale.getDefault())

        @SuppressLint("NewApi")
        fun LocalDate.withFormat(style: FormatStyle, locale: Locale = Locale.getDefault()): String =
            format(DateTimeFormatter.ofLocalizedDate(style).withLocale(locale))


        //endregion

        // endregion


    }
}