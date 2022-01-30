package data.network.alameda10

import com.google.gson.annotations.SerializedName


data class Coordinates(

    @SerializedName("longitude") var longitude: Double?,
    @SerializedName("latitude") var latitude: Double?

)