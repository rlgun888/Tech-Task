package com.ryangunn.techtask.driver.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Driver(
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("details") val driverDetails: DriverDetails
) : Serializable {

    fun getFullName(): String {
        return "$firstName $lastName"
    }
}

data class DriverDetails(

    @SerializedName("trailerType") val trailerType: String,
    @SerializedName("trailerLength") val trailerLength: Int,
    @SerializedName("trailerHeight") val trailerHeight: Int,
    @SerializedName("trailerWidth") val trailerWidth: Int,
    @SerializedName("plateNumber") val plateNumber: String,
    @SerializedName("currentLocation") val currentLocation: CurrentLocation
) : Serializable

data class CurrentLocation(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double
) : Serializable