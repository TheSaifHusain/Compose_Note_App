package com.thesaifhusain.note.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getTime(): String{
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
}

fun getDate(): String{
    return SimpleDateFormat("EEEE dd", Locale.getDefault()).format(Date())
}