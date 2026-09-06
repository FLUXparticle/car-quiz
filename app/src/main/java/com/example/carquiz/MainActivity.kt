package com.example.carquiz

import android.content.res.XmlResourceParser
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.XmlRes
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.carquiz.ui.theme.CarQuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val questions = loadQuestions(R.xml.car_questions)

        setContent {
            CarQuizTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CarQuiz(questions)
                }
            }
        }
    }
}

data class Question(
    val text: String,
    val options: List<String>,
    val correctOption: String
)

private enum class QuizScreen {
    Start,
    Question,
    Result
}

@Composable
private fun CarQuiz(questions: List<Question>) {
    var screen by remember { mutableStateOf(QuizScreen.Start) }
    var questionIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<String?>(null) }

    fun restartQuiz() {
        questionIndex = 0
        score = 0
        selectedOption = null
        screen = QuizScreen.Question
    }

    when (screen) {
        QuizScreen.Start -> StartScreen(onStart = ::restartQuiz)
        QuizScreen.Question -> QuestionScreen(
            question = questions[questionIndex],
            questionNumber = questionIndex + 1,
            questionCount = questions.size,
            selectedOption = selectedOption,
            onOptionSelected = { selectedOption = it },
            onNextQuestion = {
                if (selectedOption == questions[questionIndex].correctOption) {
                    score++
                }
                selectedOption = null
                if (questionIndex == questions.lastIndex) {
                    screen = QuizScreen.Result
                } else {
                    questionIndex++
                }
            }
        )
        QuizScreen.Result -> ResultScreen(
            score = score,
            questionCount = questions.size,
            onRestart = ::restartQuiz
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

private fun MainActivity.loadQuestions(@XmlRes resourceId: Int): List<Question> {
    val questions = mutableListOf<Question>()
    val parser = resources.getXml(resourceId)

    try {
        var questionText: String? = null
        var options = mutableListOf<String>()
        var correctOption: String? = null

        while (parser.eventType != XmlResourceParser.END_DOCUMENT) {
            when (parser.eventType) {
                XmlResourceParser.START_TAG -> when (parser.name) {
                    "question" -> {
                        questionText = null
                        options = mutableListOf()
                        correctOption = null
                    }
                    "text" -> questionText = parser.nextText()
                    "option" -> options += parser.nextText()
                    "correctOption" -> correctOption = parser.nextText()
                }
                XmlResourceParser.END_TAG -> if (parser.name == "question" &&
                    questionText != null && correctOption != null
                ) {
                    questions += Question(questionText, options, correctOption)
                }
            }
            parser.next()
        }
    } finally {
        parser.close()
    }

    return questions
}
