package com.example.demo.features.auth.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponseData(
    @SerializedName("token") val token: String,
    @SerializedName("userData") val userData: UserData
)

data class UserData(
    @SerializedName("userId") val userId: String,
    @SerializedName("name") val name: String,
    @SerializedName("roleId") val roleId: Int,
    @SerializedName("mobile") val mobile: String,
    @SerializedName("address") val address: String
)
