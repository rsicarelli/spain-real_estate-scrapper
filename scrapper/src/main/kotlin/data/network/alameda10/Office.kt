package data.network.alameda10

import com.google.gson.annotations.SerializedName


data class Office(

    @SerializedName("hasKitchen") var hasKitchen: Boolean? = null,
    @SerializedName("propertyFloors") var propertyFloors: Int? = null,
    @SerializedName("hasDoubleWindows") var hasDoubleWindows: Boolean? = null,
    @SerializedName("isOnlyOfficeBuilding") var isOnlyOfficeBuilding: Boolean? = null,
    @SerializedName("liftsNumber") var liftsNumber: Int? = null,
    @SerializedName("hasBathsInside") var hasBathsInside: Boolean? = null,
    @SerializedName("hasAccessControl") var hasAccessControl: String? = null,
    @SerializedName("isInTopFloor") var isInTopFloor: Boolean? = null,
    @SerializedName("parkingSpaceNumber") var parkingSpaceNumber: Int? = null,
    @SerializedName("hasSuspendedCeiling") var hasSuspendedCeiling: Boolean? = null,
    @SerializedName("hasSuspendedFloor") var hasSuspendedFloor: Boolean? = null,
    @SerializedName("bathRoomsType") var bathRoomsType: Int? = null,
    @SerializedName("distribution") var distribution: Int? = null,
    @SerializedName("minimalSurfaceToRent") var minimalSurfaceToRent: String? = null,
    @SerializedName("location") var location: Int? = null,
    @SerializedName("isHandicappedAdapted") var isHandicappedAdapted: Boolean? = null,
    @SerializedName("buildingFloors") var buildingFloors: Int? = null,
    @SerializedName("airConditioning") var airConditioning: Int? = null,
    @SerializedName("hasHotWater") var hasHotWater: Boolean? = null,
    @SerializedName("orientations") var orientations: ArrayList<String> = arrayListOf(),
    @SerializedName("securityFeatures") var securityFeatures: ArrayList<Int> = arrayListOf(),
    @SerializedName("fireSafeFeatures") var fireSafeFeatures: ArrayList<Int> = arrayListOf(),
    @SerializedName("TypologyName") var TypologyName: String? = null

)