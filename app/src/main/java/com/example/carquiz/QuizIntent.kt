package com.example.carquiz

sealed interface QuizIntent {
    data object Start : QuizIntent
    data class SelectAnswer(val option: String) : QuizIntent
    data object NextQuestion : QuizIntent
    data object Restart : QuizIntent
}
