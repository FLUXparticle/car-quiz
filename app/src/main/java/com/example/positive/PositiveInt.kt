package com.example.positive

import kotlin.text.iterator

fun parsePositiveInt(text: String): Int {
    if (text.isEmpty()) throw IllegalArgumentException("Leerer String")

    var result = 0

    for (ch in text) {
        if (ch !in '0'..'9') {
            throw IllegalArgumentException("Keine gültige Zahl: $text")
        }

        val digit = ch - '0'
        result = result * 10 + digit
    }

    return result
}
