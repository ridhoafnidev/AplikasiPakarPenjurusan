package com.example.core_data.domain

data class QuestionForPost(
    val idSiswa: Long = 0L,
    val answers: Answers = emptyList()
)

typealias Answers = List<Answer>

data class Answer(
    val answer: String
)
