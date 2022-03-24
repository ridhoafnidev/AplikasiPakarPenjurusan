package com.example.subfeature.pakar

import androidx.lifecycle.ViewModel


class PakarViewModel : ViewModel() {

    private var tempQuestion : TempQuestion? = null
    private val formDataQuestions = HashMap<String, String>()
    fun putFormDataValue(
        id: String,
        value: String = "0"
    ){
        formDataQuestions[id] = value
        println(formDataQuestions)
    }

    private val formDataValuesQuestions = HashMap<Int, List<TempQuestion>>()

    fun getValuesIndex(
        index: Int
    ){
        val tempData = arrayListOf<TempQuestion>()
        formDataQuestions.forEach {
            tempQuestion?.let { tempQues ->
                tempData.add(tempQues.copy(id = it.key, value = it.value))
            }
        }
        formDataValuesQuestions[index] = tempData
        println(formDataValuesQuestions)
    }
}

data class TempQuestion(val id: String, val value: String)