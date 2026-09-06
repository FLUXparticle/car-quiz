package com.example.carquiz

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val carQuizModule = module {
    single<QuestionSource> { QuestionRepository(androidContext().resources) }
    viewModel { QuizViewModel(get(), R.xml.car_questions) }
}
