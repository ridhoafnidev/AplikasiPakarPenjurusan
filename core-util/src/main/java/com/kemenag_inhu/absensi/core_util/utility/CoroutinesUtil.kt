package com.kemenag_inhu.absensi.core_util.utility

import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout

suspend fun executeWithDelay(minTime: Long, block: suspend () -> Unit) {
    val startTime = System.currentTimeMillis()
    block()
    val endTime = System.currentTimeMillis()
    val delta = minTime - (endTime - startTime)
    if (delta > 0) delay(delta)
}

suspend fun executeWithDelayAndTimeOut(
    minTime: Long,
    timeOut: Long,
    block: suspend () -> Unit
): Boolean = runCatching {
    val startTime = System.currentTimeMillis()
    withTimeout(timeOut) { block() }
    val endTime = System.currentTimeMillis()
    val delta = minTime - (endTime - startTime)
    if (delta > 0) delay(delta)
    true
}.getOrDefault(false)
