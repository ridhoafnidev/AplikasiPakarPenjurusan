@file:Suppress("TooManyFunctions")

package com.kemenag_inhu.absensi.core_data.data.remote.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

//region ApiEvent Type Checker inside LiveData

inline val <DATA> LiveData<ApiEvent<DATA>>.isLoadingEvent
    get() = value is ApiEvent.OnLoading<DATA>

inline val <DATA> LiveData<ApiEvent<DATA>>.isSuccessEvent
    get() = value is ApiEvent.OnSuccess<DATA>

inline val <DATA> LiveData<ApiEvent<DATA>>.isFailedEvent
    get() = value is ApiEvent.OnFailed<DATA>

//endregion
//region ApiEvent Data Shortcut inside Live Data

inline fun <reified DATA> LiveData<ApiEvent<DATA>>.getDataOrNull(isConsumed: Boolean = true): DATA? =
    when (val it = value) {
        is ApiEvent.OnLoading<*> -> it.getCurrentData(isConsumed) as DATA?
        is ApiEvent.OnSuccess<*> -> it.getData(isConsumed) as DATA?
        else -> null
    }

@Suppress("UNCHECKED_CAST")
inline fun <reified DATA> LiveData<ApiEvent<List<DATA>>>.getDataOrEmpty(isConsumed: Boolean = true): List<DATA> =
    when (val it = value) {
        is ApiEvent.OnLoading<*> -> it.getCurrentData(isConsumed) as List<DATA>
        is ApiEvent.OnSuccess<*> -> it.getData(isConsumed) as List<DATA>
        else -> emptyList()
    }

//endregion
//region ApiEvent Transformation inside Live Data

fun <DATA> ApiEvent<DATA>.map(modifier: (DATA) -> DATA): ApiEvent<DATA>? {
    return if (this is ApiEvent.OnSuccess<DATA>) {
        toOtherSuccessEvent(modifier(getData(false)))
    } else null
}

@JvmName("updateOrIgnoreList")
fun <DATA> ApiEvent<List<DATA>>.map(modifier: (List<DATA>) -> List<DATA>): ApiEvent<List<DATA>>? {
    return if (this is ApiEvent.OnSuccess<List<DATA>>) {
        toOtherSuccessEvent(modifier(getData(false)))
    } else null
}

fun <DATA> MutableLiveData<ApiEvent<DATA>>.updateOrIgnore(modifier: (DATA) -> DATA) =
    value?.map(modifier)?.also { value = it }

@JvmName("updateOrIgnoreListLiveData")
fun <DATA> MutableLiveData<ApiEvent<List<DATA>>>.updateOrIgnore(modifier: (List<DATA>) -> List<DATA>) =
    value?.map(modifier)?.also { value = it }

inline infix fun <reified T> ApiEvent.OnLoading<T?>.copyFrom(other: LiveData<ApiEvent<T?>>) =
    copy(currentData = other.getDataOrNull(false))

@JvmName("copyLoadingEventFromList")
inline infix fun <reified T> ApiEvent.OnLoading<List<T>>.copyFrom(other: LiveData<ApiEvent<List<T>>>) =
    copy(currentData = other.getDataOrEmpty(false))

inline infix fun <reified T> ApiEvent.OnFailed<T?>.copyFrom(other: LiveData<ApiEvent<T?>>) =
    copy(latestData = other.getDataOrNull(false))

@JvmName("copyFailedFromList")
inline infix fun <reified T> ApiEvent.OnFailed<List<T>>.copyFrom(other: LiveData<ApiEvent<List<T>>>) =
    copy(latestData = other.getDataOrEmpty(false))

inline infix fun <reified T> ApiEvent<T?>.orCopyIfFailedFrom(other: LiveData<ApiEvent<T?>>) =
    if (this is ApiEvent.OnFailed<T?>) this copyFrom other else this

@JvmName("orCopyEventIfFailedFromList")
inline infix fun <reified T> ApiEvent<List<T>>.orCopyIfFailedFrom(other: LiveData<ApiEvent<List<T>>>) =
    if (this is ApiEvent.OnFailed<List<T>>) this copyFrom other else this

//endregion
