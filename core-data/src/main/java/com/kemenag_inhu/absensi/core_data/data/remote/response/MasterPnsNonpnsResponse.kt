package com.kemenag_inhu.absensi.core_data.data.remote.response

import com.kemenag_inhu.absensi.core_domain.model.MasterPnsNonpns
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MasterPnsNonpnsResponse(
    @Json(name = "status")
    val status: String = "",
    @Json(name = "code")
    val code: Int = 0,
    @Json(name = "message")
    val message: String = "",
    @Json(name = "result")
    val data: List<DataMasterPnsNonpnsResponse> = listOf()
)

@JsonClass(generateAdapter = true)
data class DataMasterPnsNonpnsResponse(
    @Json(name = "id_master_pns_nonpns")
    val idMasterPnsNonpns: Int = 0,
    @Json(name = "pns_nonpns")
    val pnsNonpns: String = ""
)

//region Convert from Response to Domain

internal fun MasterPnsNonpnsResponse.toDomain() =
    data.map { it.toDomain() }

internal fun DataMasterPnsNonpnsResponse.toDomain() =
    MasterPnsNonpns(idMasterPnsNonpns = idMasterPnsNonpns, pnsNonpns = pnsNonpns)

//endregion
