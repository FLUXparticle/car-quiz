package com.example.carquiz

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class QuizReducerTruthTest {
    @Test
    fun correctAnswerLeadsFromStartScreenToResultWithOnePoint() {
        val question = Question(
            text = "Welche Antwort ist richtig?",
            options = listOf("Richtig", "Falsch"),
            correctOption = "Richtig"
        )
        val initialState = QuizUiState(questions = listOf(question))

        val startedState = reduce(initialState, QuizIntent.Start)
        val answeredState = reduce(startedState, QuizIntent.SelectAnswer("Richtig"))
        val resultState = reduce(answeredState, QuizIntent.NextQuestion)

        assertThat(resultState.screen).isEqualTo(QuizScreen.Result)
        assertThat(resultState.score).isEqualTo(1)
        assertThat(resultState.selectedOption).isNull()
    }

    @Test
    fun questionDataContractRejectsNonSelectableCorrectAnswer() {
        val questions = listOf(
            Question(
                text = "Beispielfrage",
                options = listOf("Ja", "Nein"),
                correctOption = "Vielleicht"
            )
        )

        val error = assertThrows<IllegalArgumentException> {
            validateQuestions(questions)
        }

        assertThat(error).hasMessageThat().contains("nicht auswählbar")
    }
}
