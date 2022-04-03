package com.example.subfeature.hasilangket

import androidx.lifecycle.ViewModel
import com.example.core_data.api.ApiEvent
import com.example.core_data.api.request.RequestAnswer
import com.example.core_data.api.request.RequestAnswerInsert
import com.example.core_data.api.response.CommonResponse
import com.example.core_data.api.service.AnswerService
import com.example.core_data.repository.LastResultRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HasilAngketViewModel(val repository: LastResultRepository) : ViewModel() {
}