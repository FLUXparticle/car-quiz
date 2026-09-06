package com.example.carquiz

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.koin.dsl.koinApplication
import org.koin.dsl.module

class QuizViewModelKoinTest {
    @Test
    fun testQuestionSourceIsInjectedIntoQuizViewModel() {
        val testQuestions = listOf(
            Question(
                text = "Testfrage",
                options = listOf("Richtig", "Falsch"),
                correctOption = "Richtig"
            )
        )
        val testModule = module {
            single<QuestionSource> { StaticQuestionSource(testQuestions) }
        }
        val koinApplication = koinApplication {
            modules(carQuizModule, testModule)
        }

        try {
            val viewModel = koinApplication.koin.get<QuizViewModel>()

            assertThat(viewModel.uiState.value.questions).isEqualTo(testQuestions)
        } finally {
            koinApplication.close()
        }
    }
}

private class StaticQuestionSource(
    private val questions: List<Question>
) : QuestionSource {
    override fun loadQuestions(resourceId: Int): List<Question> = questions
}
