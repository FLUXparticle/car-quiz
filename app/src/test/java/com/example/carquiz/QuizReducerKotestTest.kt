package com.example.carquiz

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class QuizReducerKotestTest : FunSpec({
    test("a correct answer leads from start screen to result with one point") {
        val question = Question(
            text = "Welche Antwort ist richtig?",
            options = listOf("Richtig", "Falsch"),
            correctOption = "Richtig"
        )
        val initialState = QuizUiState(questions = listOf(question))

        val startedState = reduce(initialState, QuizIntent.Start)
        val answeredState = reduce(startedState, QuizIntent.SelectAnswer("Richtig"))
        val resultState = reduce(answeredState, QuizIntent.NextQuestion)

        resultState.screen shouldBe QuizScreen.Result
        resultState.score shouldBe 1
        resultState.selectedOption shouldBe null
    }
})
