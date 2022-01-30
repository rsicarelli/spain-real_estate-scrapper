package data.network.alameda10

import com.google.gson.annotations.SerializedName


data class RENT(

    @SerializedName("price") var price: Int? = null,
    @SerializedName("byArea") var byArea: Double? = null,
    @SerializedName("withParkingSpace") var withParkingSpace: Int? = null,
    @SerializedName("priceDrop") var priceDrop: String? = null

)