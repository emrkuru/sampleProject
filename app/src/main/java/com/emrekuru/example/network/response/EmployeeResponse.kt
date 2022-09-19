package com.emrekuru.example.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmployeeResponse(
    @SerializedName("name")  val name : String,
    @SerializedName("lastName") val lastName : String,
    @SerializedName("age")  val age : Int,
    @SerializedName("title")  val title : String)

    :Parcelable