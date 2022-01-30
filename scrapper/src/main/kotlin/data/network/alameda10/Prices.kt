package data.network.alameda10

import com.google.gson.annotations.SerializedName
import data.network.alameda10.ByOperation


data class Prices(
    @SerializedName("byOperation") var byOperation: ByOperation
)