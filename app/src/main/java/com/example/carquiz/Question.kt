package com.example.carquiz

data class Question(
    val text: String,
    val options: List<String>,
    val correctOption: String
)
