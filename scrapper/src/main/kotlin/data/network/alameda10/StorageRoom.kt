package data.network.alameda10

import com.google.gson.annotations.SerializedName


data class StorageRoom(
    @SerializedName("propertyArea") var propertyArea: String? = null,
    @SerializedName("height") var height: String? = null,
    @SerializedName("securityFeatures") var securityFeatures: ArrayList<String> = arrayListOf(),
    @SerializedName("accessFeatures") var accessFeatures: ArrayList<String> = arrayListOf(),
    @SerializedName("TypologyName") var TypologyName: String? = null
)