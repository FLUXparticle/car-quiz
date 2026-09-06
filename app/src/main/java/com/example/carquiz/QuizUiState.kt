package com.example.carquiz

data class QuizUiState(
    val questions: List<Question>,
    val screen: QuizScreen = QuizScreen.Start,
    val questionIndex: Int = 0,
    val selectedOption: String? = null,
    val score: Int = 0
) {
    val currentQuestion: Question?
        get() = questions.getOrNull(questionIndex)
}
