package com.example.carquiz

data class Question(
    val text: String,
    val options: List<String>,
    val correctOption: String
)

enum class QuizScreen {
    Start,
    Question,
    Result
}

sealed interface QuizIntent {
    data object Start : QuizIntent
    data class SelectAnswer(val option: String) : QuizIntent
    data object NextQuestion : QuizIntent
    data object Restart : QuizIntent
}

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

fun reduce(state: QuizUiState, intent: QuizIntent): QuizUiState = when (intent) {
    QuizIntent.Start,
    QuizIntent.Restart -> state.copy(
        screen = QuizScreen.Question,
        questionIndex = 0,
        selectedOption = null,
        score = 0
    )
    is QuizIntent.SelectAnswer -> {
        val question = state.currentQuestion
        if (state.screen != QuizScreen.Question || state.selectedOption != null || question == null ||
            intent.option !in question.options
        ) {
            state
        } else {
            state.copy(selectedOption = intent.option)
        }
    }
    QuizIntent.NextQuestion -> {
        val question = state.currentQuestion
        val selectedOption = state.selectedOption
        if (state.screen != QuizScreen.Question || question == null || selectedOption == null) {
            state
        } else {
            val newScore = state.score + if (selectedOption == question.correctOption) 1 else 0
            if (state.questionIndex == state.questions.lastIndex) {
                state.copy(screen = QuizScreen.Result, selectedOption = null, score = newScore)
            } else {
                state.copy(
                    questionIndex = state.questionIndex + 1,
                    selectedOption = null,
                    score = newScore
                )
            }
        }
    }
}
