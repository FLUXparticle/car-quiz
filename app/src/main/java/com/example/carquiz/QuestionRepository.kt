package com.example.carquiz

import android.content.res.Resources
import android.content.res.XmlResourceParser
import androidx.annotation.XmlRes

class QuestionRepository(private val resources: Resources) {
    fun loadQuestions(@XmlRes resourceId: Int): List<Question> {
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

        validateQuestions(questions)
        return questions
    }
}

fun validateQuestions(questions: List<Question>) {
    require(questions.isNotEmpty()) { "Das Quiz benötigt mindestens eine Frage." }
    questions.forEachIndexed { index, question ->
        require(question.options.isNotEmpty()) { "Frage ${index + 1} hat keine Antworten." }
        require(question.correctOption in question.options) {
            "Die richtige Antwort von Frage ${index + 1} ist nicht auswählbar."
        }
    }
}
