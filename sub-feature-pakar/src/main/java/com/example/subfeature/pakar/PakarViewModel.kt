package com.example.subfeature.pakar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_data.api.ApiEvent
import com.example.core_data.domain.Answer
import com.example.core_data.domain.Answers
import com.example.core_data.repository.AnswerRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class PakarViewModel(
    val answerRepository: AnswerRepository
) : ViewModel() {

    private val _isDataSaved = MutableLiveData<Boolean>()
    val isDataSaved: LiveData<Boolean> = _isDataSaved

    fun saveQuestionAnswer(
        siswaId: Long,
        answers: Answers
    ){
        viewModelScope.launch {
            answerRepository.saveAnswer(siswaId, findAnswer(answers),  answers)
                .collect {
                    _isDataSaved.value = it is ApiEvent.OnSuccess<*>
                }
        }
    }

    private fun findAnswer(answers: Answers) : String {
        var result = ""
        answers.forEach {
            result = when {
                it.answer =="J01" && it.answer=="B01" && it.answer=="B02" && it.answer=="M01" && it.answer=="M02" && it.answer=="M03" && it.answer=="M05" && it.answer=="M06" && it.answer=="P01" && it.answer=="N01" && it.answer=="C01" && it.answer=="D01"  -> "IPA"
                it.answer =="J01" && it.answer=="B01" && it.answer=="B02" && it.answer=="M01" && it.answer=="M02" && it.answer=="M03" && it.answer=="M05" && it.answer=="M07" && it.answer=="P01" && it.answer=="N01" && it.answer=="C01" && it.answer=="D01"  -> "IPA"
                else -> "IPS"
            }
        }
        return result
    }
}

data class TempQuestion(val id: String, val value: String)