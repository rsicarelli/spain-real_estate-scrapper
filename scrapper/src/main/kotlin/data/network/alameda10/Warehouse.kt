package data.network.alameda10

import com.google.gson.annotations.SerializedName


data class Warehouse(

    @SerializedName("localFloorsNumber") var localFloorsNumber: String? = null,
    @SerializedName("shopWindowsNumber") var shopWindowsNumber: String? = null,
    @SerializedName("transferCost") var transferCost: String? = null,
    @SerializedName("isCornerLocated") var isCornerLocated: String? = null,
    @SerializedName("hasSmokeExtractor") var hasSmokeExtractor: String? = null,
    @SerializedName("isATransfer") var isATransfer: String? = null,
    @SerializedName("lastCommercialActivity") var lastCommercialActivity: String? = null,
    @SerializedName("ubication") var ubication: String? = null,
    @SerializedName("buildPurpose") var buildPurpose: String? = null,
    @SerializedName("facadeArea") var facadeArea: String? = null,
    @SerializedName("roomDistribution") var roomDistribution: Int? = null,
    @SerializedName("securityFeatures") var securityFeatures: ArrayList<Int> = arrayListOf(),
    @SerializedName("hasAirConditioning") var hasAirConditioning: String? = null,
    @SerializedName("hasHandicapedBaths") var hasHandicapedBaths: String? = null,
    @SerializedName("hasLoadingDock") var hasLoadingDock: String? = null,
    @SerializedName("IsCommercial") var IsCommercial: Boolean? = null,
    @SerializedName("IsIndustrial") var IsIndustrial: Boolean? = null,
    @SerializedName("TypologyName") var TypologyName: String? = null

)