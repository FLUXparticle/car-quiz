package com.example.carquiz

import androidx.annotation.XmlRes

interface QuestionSource {
    fun loadQuestions(@XmlRes resourceId: Int): List<Question>
}
