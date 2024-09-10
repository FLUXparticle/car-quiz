package com.example.carquiz.payment

class ShopImpl(private val paymentService: PaymentService) : Shop {
    override fun buy(item: String, amount: Double): String {
        return "Buying $item in the US ...\n" + paymentService.processPayment(amount);
    }
}
