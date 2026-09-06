package com.example.carquiz

import androidx.annotation.XmlRes
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class QuizViewModel(
    questionRepository: QuestionRepository,
    @XmlRes resourceId: Int
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuizUiState(questionRepository.loadQuestions(resourceId)))
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    fun dispatch(intent: QuizIntent) {
        _uiState.update { state -> reduce(state, intent) }
    }
}
