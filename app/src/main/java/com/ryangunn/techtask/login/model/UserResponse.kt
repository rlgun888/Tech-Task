package com.ryangunn.techtask.login.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id:Long,
    @SerializedName("username")
    val username:String,
    @SerializedName("first")
    val firstName:String,
    @SerializedName("last")
    val lastName:String
)
