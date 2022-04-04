package com.example.subfeature.pakar.fragments.two

import androidx.lifecycle.ViewModel
import com.example.core_data.domain.Answer
import com.example.core_data.domain.Answers


class PakarSecondViewModel : ViewModel() {

    private val formDataQuestions = HashMap<String, String>()
    fun putFormDataValue(
        id: String,
        value: String = "0"
    ) {
        formDataQuestions[id] = value
    }

    fun getFinalData(): Answers {
        val listAnswer = arrayListOf<Answer>()
        val data = formDataQuestions.filter { it.value != "0" }
        data.forEach {
            listAnswer.add(Answer(answer = it.key))
        }
        return listAnswer.sortedBy { it.answer }
    }

}