package com.example.carquiz.payment

interface PaymentService {
    fun processPayment(amount: Double): String?
}
