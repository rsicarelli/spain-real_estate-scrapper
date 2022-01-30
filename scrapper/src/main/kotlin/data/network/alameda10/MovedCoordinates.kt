package data.network.alameda10

import com.google.gson.annotations.SerializedName


data class MovedCoordinates(

    @SerializedName("longitude") var longitude: Double?,
    @SerializedName("latitude") var latitude: Double?

)