package com.example.tubesmobile.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryDetail(
    var countryId : Int?,
    var task_name : String?,
    var subject_name : String?,
    var task_deadline : Int?,
    var task_description : String
) : Parcelable

//countryname - task_name
//countryArea - subject_name
//countryPopulation - task_deadline
//countryDescription - task_description
