package com.ryangunn.techtask.login.model

import com.google.gson.annotations.SerializedName

data class UserBody(
    @SerializedName("username")
    val username:String,
    @SerializedName("password")
    val password:String
)
