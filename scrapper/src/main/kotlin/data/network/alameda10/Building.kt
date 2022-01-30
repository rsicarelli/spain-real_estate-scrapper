package data.network.alameda10

import com.google.gson.annotations.SerializedName


data class Building(
    @SerializedName("classification") var classification: ArrayList<String>,
    @SerializedName("propertyArea") var propertyArea: String?,
    @SerializedName("hasTenants") var hasTenants: String?,
    @SerializedName("builtType") var builtType: String?,
    @SerializedName("parkingSpaceNumber") var parkingSpaceNumber: String?,
    @SerializedName("constructionYear") var constructionYear: String?,
    @SerializedName("liftsNumber") var liftsNumber: String?,
    @SerializedName("minimalSurfaceToRent") var minimalSurfaceToRent: String?,
    @SerializedName("securityFeatures") var securityFeatures: ArrayList<String>,
    @SerializedName("homesNumber") var homesNumber: String?,
    @SerializedName("buildingFloors") var buildingFloors: String?,
    @SerializedName("casaBuiltType") var casaBuiltType: String?,
    @SerializedName("gardenType") var gardenType: String?,
    @SerializedName("hasGarden") var hasGarden: String?,
    @SerializedName("TypologyName") var TypologyName: String?
)