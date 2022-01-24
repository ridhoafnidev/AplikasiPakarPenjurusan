package com.kemenag_inhu.absensi.subfeature.create_event

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kemenag_inhu.absensi.core_data.domain.Event
import com.kemenag_inhu.absensi.core_domain.usecase.EventUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class CreateEventViewModel(private val eventUseCase: EventUseCase) : ViewModel() {

    private var _isSaved = MutableLiveData<Boolean>()
    var isSaved : LiveData<Boolean> = _isSaved

    val minStartDate: LocalDate? = null

    val minStartTime: LocalTime
        @SuppressLint("NewApi")
        get() = LocalTime.of(0, 0)

    @SuppressLint("NewApi")
    private var _startDate: MutableLiveData<LocalDate> = MutableLiveData(LocalDate.now())
    var startDate: LiveData<LocalDate> = _startDate

    @SuppressLint("NewApi")
    private var _endDate: MutableLiveData<LocalDate> = MutableLiveData(LocalDate.now())
    var endDate: LiveData<LocalDate> = _endDate

    @SuppressLint("NewApi")
    private var _startTime: MutableLiveData<LocalTime> = MutableLiveData(LocalTime.now())
    var startTime: LiveData<LocalTime> = _startTime

    @SuppressLint("NewApi")
    private var _endTime: MutableLiveData<LocalTime> = MutableLiveData(LocalTime.now())
    var endTime: LiveData<LocalTime> = _endTime

    fun setStartTime(time: LocalTime){
        _startTime.value = time
        startTime.value?.let { startTime ->
            if (startTime.equals(endTime.value)){
                adjustEndTime()
            }
        }
    }

    fun setEndTime(time: LocalTime){
        _endTime.value = time
        startTime.value?.let { startTime ->
            if (startTime.equals(endTime.value)){
                adjustEndTime()
            }
        }
    }

    @SuppressLint("NewApi")
    private fun adjustEndTime() {
        _endTime.value?.let { endTime ->
            if (endTime.isBefore(_startTime.value)){
                _endTime.value = _startTime.value
            }
        }
    }

    @SuppressLint("NewApi")
    fun setStartDate(date: LocalDate) {
        _startDate.value = date
        if (endDate.value!!.isBefore(date)) {
            _endDate.value = date
            adjustEndTime()
        }
    }

    @SuppressLint("NewApi")
    fun setEndDate(date: LocalDate) {
        _endDate.value = date
        if (date.isEqual(startDate.value!!)) {
            adjustEndTime()
        }
    }

    internal fun next(
        eventName: String, eventLocation: String, eventDateStart: String, eventDateEnd: String,
        eventTimeStart: String, eventTimeEnd: String, eventDescription: String, eventOrganizer: String, image: String
    ){

        val event = Event(name = eventName, location = eventLocation, startDate = eventDateStart,
            startTime = eventTimeStart, endDate = eventDateEnd, endTime = eventTimeEnd, description = eventDescription,
            poweredBy = eventOrganizer, image = image)
        viewModelScope.launch {
            eventUseCase.insertEvent(event)
            _isSaved.value = true
        }
    }

}