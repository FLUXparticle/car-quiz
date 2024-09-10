package com.example.carquiz.payment

class PaymentServiceImpl : PaymentService {
    override fun processPayment(amount: Double): String {
        return "Processing PayPal payment of $$amount"
    }
}
