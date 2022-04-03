package com.example.subfeature.pakar.fragments.seven

import androidx.lifecycle.ViewModel
import com.example.core_data.domain.Answer
import com.example.core_data.domain.Answers


class PakarSeventhViewModel : ViewModel() {

    private val formDataQuestions = HashMap<String, String>()
    fun putFormDataValue(
        id: String,
        value: String = "0"
    ) {
        formDataQuestions.clear()
        formDataQuestions[id] = value
    }

    fun getFinalData(): Answers {
        val listAnswer = arrayListOf<Answer>()
        val data = formDataQuestions.filter { it.value != "0" }
        data.forEach {
            listAnswer.add(Answer(answer = it.key))
        }
        return listAnswer
    }

}