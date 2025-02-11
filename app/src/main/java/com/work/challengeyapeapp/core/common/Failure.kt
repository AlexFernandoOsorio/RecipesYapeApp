package com.work.challengeyapeapp.core.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class Failure : Parcelable {
    @Parcelize
    data object NetworkConnection : Failure()

    @Parcelize
    data object Http : Failure()

    @Parcelize
    data object UnExpected : Failure()

    @Parcelize
    data object Unauthorized: Failure()

}
