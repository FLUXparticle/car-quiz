package com.example.carquiz.payment

interface ShopFactory {
    fun createShop(paymentService: PaymentService): Shop
}
