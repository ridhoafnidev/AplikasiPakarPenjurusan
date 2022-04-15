package com.kemenag_inhu.absensi.core_util.utility

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle

val Context.metaData: Bundle
    get() = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA).metaData
