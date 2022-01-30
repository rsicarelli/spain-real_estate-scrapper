package data.network.alameda10

import com.google.gson.annotations.SerializedName
import data.network.alameda10.MultipleListing
import data.network.alameda10.VisualHighlight


data class Services(

    @SerializedName("multipleListing") var multipleListing: MultipleListing?,
    @SerializedName("publishServiceId") var publishServiceId: Int?,
    @SerializedName("hasPicsAndPlan") var hasPicsAndPlan: Boolean?,
    @SerializedName("isPreferenceHighlight") var isPreferenceHighlight: Boolean?,
    @SerializedName("isTopHighlight") var isTopHighlight: Boolean?,
    @SerializedName("IsMLS") var IsMLS: Boolean?
)