package com.kemenag_inhu.absensi.core_data.data.remote.api

sealed class ApiEvent<out DATA> {
    var hasBeenConsumed: Boolean = false
        protected set

    data class OnLoading<DATA>(private val currentData: DATA) : ApiEvent<DATA>(){
        fun getCurrentData(isConsumed: Boolean = true) = currentData.also {
            if (isConsumed) hasBeenConsumed = true
        }
    }

    data class OnSuccess<DATA>(
        private val data: DATA,
        val dataSource: DataSource,
        val cacheIndex:Int
    ) : ApiEvent<DATA>() {
        enum class DataSource { SERVER, CACHE }

        val isFromCache get() = dataSource == DataSource.CACHE
        val isFromServer get() = dataSource == DataSource.SERVER
        val isInitialCache get() = cacheIndex == 0

        fun getData(isConsumed: Boolean = true) = data.also {
            if (isConsumed) hasBeenConsumed = true
        }

        fun <T> toOtherSuccessEvent(data: T) = OnSuccess(data, dataSource, cacheIndex)
        fun <T> toOtherSuccessEvent(data: List<T>) = OnSuccess(data, dataSource, cacheIndex)

        companion object {
            fun <DATA> fromCache(data: DATA, cacheIndex: Int = 0) = OnSuccess(
                data = data,
                dataSource = DataSource.CACHE,
                cacheIndex = cacheIndex
            )

            fun <DATA> fromServer(data: DATA) = OnSuccess(
                data = data,
                dataSource = DataSource.SERVER,
                cacheIndex = Int.MIN_VALUE
            )
        }
    }

    data class OnFailed<DATA>(
        private val exception: ApiException,
        val latestData: DATA
    ) : ApiEvent<DATA>() {
        fun getException(isConsumed: Boolean = true) = exception.also {
            if (isConsumed) hasBeenConsumed = true
        }

        fun <T> toOtherFailedEventNullable() = OnFailed<T?>(exception, null)
        fun <T> toOtherFailedEventEmptyList() = OnFailed<List<T>>(exception, emptyList())
    }

}