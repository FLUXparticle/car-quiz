package com.example.carquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.carquiz.ui.theme.CarQuizTheme

class MainActivity : ComponentActivity() {
    private val quizViewModel: QuizViewModel by viewModels {
        QuizViewModelFactory(QuestionRepository(resources), R.xml.car_questions)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarQuizTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiState by quizViewModel.uiState.collectAsStateWithLifecycle()
                    CarQuiz(uiState, quizViewModel::dispatch)
                }
            }
        }
    }
}

@Composable
private fun CarQuiz(uiState: QuizUiState, onIntent: (QuizIntent) -> Unit) {
    when (uiState.screen) {
        QuizScreen.Start -> StartScreen(onStart = { onIntent(QuizIntent.Start) })
        QuizScreen.Question -> {
            val question = uiState.currentQuestion ?: return
            QuestionScreen(
                question = question,
                questionNumber = uiState.questionIndex + 1,
                questionCount = uiState.questions.size,
                selectedOption = uiState.selectedOption,
                onOptionSelected = { onIntent(QuizIntent.SelectAnswer(it)) },
                onNextQuestion = { onIntent(QuizIntent.NextQuestion) }
            )
        }
        QuizScreen.Result -> ResultScreen(
            score = uiState.score,
            questionCount = uiState.questions.size,
            onRestart = { onIntent(QuizIntent.Restart) }
        )
    }
}

@Composable
private fun StartScreen(onStart: () -> Unit) {
    QuizColumn {
        Text(text = "CarQuiz", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Teste dein Wissen über deutsche Automarken.")
        Button(onClick = onStart, modifier = Modifier.fillMaxWidth()) {
            Text("Quiz starten")
        }
    }
}

@Composable
private fun QuestionScreen(
    question: Question,
    questionNumber: Int,
    questionCount: Int,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    onNextQuestion: () -> Unit
) {
    QuizColumn {
        Text(text = "Frage $questionNumber von $questionCount")
        Text(text = question.text, style = MaterialTheme.typography.headlineSmall)

        question.options.forEach { option ->
            Button(
                onClick = { onOptionSelected(option) },
                enabled = selectedOption == null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(option)
            }
        }

        selectedOption?.let { answer ->
            val isCorrect = answer == question.correctOption
            Text(if (isCorrect) "Richtig!" else "Leider nicht. Richtig ist: ${question.correctOption}")
            Button(onClick = onNextQuestion, modifier = Modifier.fillMaxWidth()) {
                Text(if (questionNumber == questionCount) "Ergebnis anzeigen" else "Nächste Frage")
            }
        }
    }
}

@Composable
private fun ResultScreen(score: Int, questionCount: Int, onRestart: () -> Unit) {
    QuizColumn {
        Text(text = "Quiz beendet", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Du hast $score von $questionCount Fragen richtig beantwortet.")
        Button(onClick = onRestart, modifier = Modifier.fillMaxWidth()) {
            Text("Noch einmal spielen")
        }
    }
}

@Composable
private fun QuizColumn(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        content = content
    )
}
