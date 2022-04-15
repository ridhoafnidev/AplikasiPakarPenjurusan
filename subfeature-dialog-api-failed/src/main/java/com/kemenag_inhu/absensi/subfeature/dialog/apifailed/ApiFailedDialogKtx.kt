@file:Suppress("TooManyFunctions")

package com.kemenag_inhu.absensi.subfeature.dialog.apifailed

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kemenag_inhu.absensi.core_data.data.remote.api.ApiException
import com.kemenag_inhu.absensi.core_resource.components.showErrorDialog

//region Fragment

fun Fragment.showApiFailedDialog(
    exception: ApiException = ApiException.Unknown,
    onDismissListener: () -> Unit = {},
    customText: String = ""
) {
    when (exception) {
        ApiException.Offline -> showErrorDialogOffline(onDismissListener)
        ApiException.Timeout,
        ApiException.Network -> showErrorDialogNetwork(onDismissListener)
        is ApiException.FailedResponse,
        is ApiException.Http,
        ApiException.NullResponse,
        ApiException.EmptyResponse -> showErrorDialogServer(onDismissListener)
        ApiException.Unknown -> showErrorDialogUnknown(
            onDismissListener = onDismissListener,
            customText = customText
        )
    }
}

private fun Fragment.showErrorDialogUnknown(
    onDismissListener: () -> Unit = {},
    customText: String = ""
) {
    showErrorDialog(
        assetRes = R.raw.dialog_api_failed_unknown,
        isAnimation = true,
        titleRes = R.string.dialog_api_failed_unknown_title,
        contentRes = R.string.dialog_api_failed_unknown_content,
        onDismissListener = onDismissListener,
        customText = customText
    )
}

private fun Fragment.showErrorDialogOffline(onDismissListener: () -> Unit = {}) {
    showErrorDialog(
        assetRes = R.drawable.dialog_api_failed_network,
        isAnimation = false,
        titleRes = R.string.dialog_api_failed_offline_title,
        contentRes = R.string.dialog_api_failed_offline_content,
        onDismissListener = onDismissListener
    )
}

private fun Fragment.showErrorDialogNetwork(onDismissListener: () -> Unit = {}) {
    showErrorDialog(
        assetRes = R.drawable.dialog_api_failed_network,
        isAnimation = false,
        titleRes = R.string.dialog_api_failed_network_title,
        contentRes = R.string.dialog_api_failed_network_content,
        onDismissListener = onDismissListener
    )
}

private fun Fragment.showErrorDialogServer(onDismissListener: () -> Unit = {}) {
    showErrorDialog(
        assetRes = R.raw.dialog_api_failed_server,
        isAnimation = true,
        titleRes = R.string.dialog_api_failed_server_title,
        contentRes = R.string.dialog_api_failed_server_content,
        onDismissListener = onDismissListener
    )
}

//endregion
//region Activity

fun AppCompatActivity.showApiFailedDialog(
    exception: ApiException = ApiException.Unknown,
    onDismissListener: () -> Unit = {},
    customText: String = ""
) {
    when (exception) {
        ApiException.Offline -> showErrorDialogOffline(onDismissListener)
        ApiException.Timeout,
        ApiException.Network -> showErrorDialogNetwork(onDismissListener)
        is ApiException.FailedResponse,
        is ApiException.Http,
        ApiException.NullResponse,
        ApiException.EmptyResponse -> showErrorDialogServer(
            onDismissListener = onDismissListener,
            customText = customText
        )
        ApiException.Unknown -> showErrorDialogUnknown(
            onDismissListener = onDismissListener,
            customText = customText
        )
    }
}

private fun AppCompatActivity.showErrorDialogUnknown(
    onDismissListener: () -> Unit = {},
    customText: String = ""
) {
    showErrorDialog(
        assetRes = R.raw.dialog_api_failed_unknown,
        isAnimation = true,
        titleRes = R.string.dialog_api_failed_unknown_title,
        contentRes = R.string.dialog_api_failed_unknown_content,
        onDismissListener = onDismissListener,
        customText = customText
    )
}

private fun AppCompatActivity.showErrorDialogOffline(onDismissListener: () -> Unit = {}) {
    showErrorDialog(
        assetRes = R.drawable.dialog_api_failed_network,
        isAnimation = false,
        titleRes = R.string.dialog_api_failed_offline_title,
        contentRes = R.string.dialog_api_failed_offline_content,
        onDismissListener = onDismissListener
    )
}

private fun AppCompatActivity.showErrorDialogNetwork(onDismissListener: () -> Unit = {}) {
    showErrorDialog(
        assetRes = R.drawable.dialog_api_failed_network,
        isAnimation = false,
        titleRes = R.string.dialog_api_failed_network_title,
        contentRes = R.string.dialog_api_failed_network_content,
        onDismissListener = onDismissListener
    )
}

private fun AppCompatActivity.showErrorDialogServer(
    onDismissListener: () -> Unit = {},
    customText: String = ""
) {
    showErrorDialog(
        assetRes = R.raw.dialog_api_failed_server,
        isAnimation = true,
        titleRes = R.string.dialog_api_failed_server_title,
        contentRes = R.string.dialog_api_failed_server_content,
        onDismissListener = onDismissListener,
        customText = customText
    )
}

//endregion
