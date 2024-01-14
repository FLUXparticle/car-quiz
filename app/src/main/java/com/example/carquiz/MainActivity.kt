package com.example.carquiz

import android.content.Context
import android.content.res.XmlResourceParser
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.carquiz.ui.theme.CarQuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarQuizTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    // Greeting("Android")
                    DisplayFirstQuestion(context = this)
                }
            }
        }
    }
}

@Composable
fun DisplayFirstQuestion(context: Context) {
    val xmlResourceParser = context.resources.getXml(R.xml.questions)

    val firstQuestionText: String?
    val answers = mutableListOf<String>()

    while (true) {
        val eventType = xmlResourceParser.next()
        if (eventType == XmlResourceParser.START_TAG && xmlResourceParser.name == "question") {
            firstQuestionText = readQuestion(xmlResourceParser, answers)
            break
        }
    }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = firstQuestionText ?: "Keine Frage gefunden",
            modifier = Modifier.fillMaxWidth()
        )

        answers.forEach { answer ->
            Button(
                onClick = { /* Hier kannst du die Antwort-Funktion hinzufügen */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = answer)
            }
        }
    }
}

private fun readQuestion(
    xmlResourceParser: XmlResourceParser,
    answers: MutableList<String>
): String? {
    var firstQuestionText: String? = null
    while (true) {
        val eventType = xmlResourceParser.next()
        if (eventType == XmlResourceParser.START_TAG) {
            when (xmlResourceParser.name) {
                "text" -> firstQuestionText = xmlResourceParser.nextText()
                "option" -> answers.add(xmlResourceParser.nextText())
            }
        }
        if (eventType == XmlResourceParser.END_TAG && xmlResourceParser.name == "question") {
            break
        }
    }
    return firstQuestionText
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CarQuizTheme {}
}