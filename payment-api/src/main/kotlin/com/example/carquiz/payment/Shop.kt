package com.example.carquiz.payment

interface Shop {
    fun buy(item: String, amount: Double): String
}
