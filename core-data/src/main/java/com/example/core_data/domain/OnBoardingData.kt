package com.example.core_data.domain

data class OnBoardingData(val title: String, val desc: String, val questions: ListQuestions)

typealias ListOnBoardingData = List<OnBoardingData>

data class Questions(val question: String)

typealias ListQuestions = List<Questions>