package com.example.tubesmobile.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryItem(
    val countryId : Int?,
    val task_name : String?,
    val task_deadline: Int?
) : Parcelable
