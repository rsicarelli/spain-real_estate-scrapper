package data.network.alameda10

import com.google.gson.annotations.SerializedName


data class Duplicates(

    @SerializedName("duplicatesNumber") var duplicatesNumber: String?,
    @SerializedName("duplicatesDate") var duplicatesDate: String?,
    @SerializedName("deactivatedDuplicatesNumber") var deactivatedDuplicatesNumber: String?,
    @SerializedName("deactivatedDuplicatesDate") var deactivatedDuplicatesDate: String?,
    @SerializedName("newPriceNumber") var newPriceNumber: String?,
    @SerializedName("newPriceDate") var newPriceDate: String?,
    @SerializedName("lastModificationDate") var lastModificationDate: String?,
    @SerializedName("duplicatesNewHighlightNumber") var duplicatesNewHighlightNumber: String?,
    @SerializedName("duplicatesNewHighlightDate") var duplicatesNewHighlightDate: String?

)