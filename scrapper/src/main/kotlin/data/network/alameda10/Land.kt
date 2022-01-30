package data.network.alameda10

import com.google.gson.annotations.SerializedName


data class Land(

    @SerializedName("hasNaturalGas") var hasNaturalGas: String?,
    @SerializedName("maxBuildFloorAllowed") var maxBuildFloorAllowed: String?,
    @SerializedName("hasStreetLighting") var hasStreetLighting: String?,
    @SerializedName("hasWater") var hasWater: String?,
    @SerializedName("hasRoadAccess") var hasRoadAccess: String?,
    @SerializedName("buildingSurface") var buildingSurface: String?,
    @SerializedName("propertyArea") var propertyArea: String?,
    @SerializedName("hasSideWalks") var hasSideWalks: String?,
    @SerializedName("hasSewerSystem") var hasSewerSystem: String?,
    @SerializedName("hasElectricity") var hasElectricity: String?,
    @SerializedName("type") var type: String?,
    @SerializedName("nearestPlaceDistance") var nearestPlaceDistance: String?,
    @SerializedName("accessType") var accessType: String?,
    @SerializedName("minimalSurfaceToTrade") var minimalSurfaceToTrade: String?,
    @SerializedName("classification") var classification: ArrayList<String>,
    @SerializedName("TypologyName") var TypologyName: String?

)