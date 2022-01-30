package data.network.alameda10

import com.google.gson.annotations.SerializedName


data class Room(

    @SerializedName("type") var type: Int? = null,
    @SerializedName("minTenantAge") var minTenantAge: String? = null,
    @SerializedName("hasInternet") var hasInternet: String? = null,
    @SerializedName("hasAirConditioning") var hasAirConditioning: String? = null,
    @SerializedName("roomNumber") var roomNumber: String? = null,
    @SerializedName("bathNumber") var bathNumber: String? = null,
    @SerializedName("hasCupboard") var hasCupboard: String? = null,
    @SerializedName("hasLift") var hasLift: String? = null,
    @SerializedName("isFurnish") var isFurnish: String? = null,
    @SerializedName("propertyArea") var propertyArea: String? = null,
    @SerializedName("maxTenantAge") var maxTenantAge: String? = null,
    @SerializedName("tenantNumber") var tenantNumber: String? = null,
    @SerializedName("newSexualOrientation") var newSexualOrientation: Int? = null,
    @SerializedName("newGender") var newGender: Int? = null,
    @SerializedName("isInTopFloor") var isInTopFloor: String? = null,
    @SerializedName("tenantGender") var tenantGender: Int? = null,
    @SerializedName("minimalStay") var minimalStay: String? = null,
    @SerializedName("newOccupation") var newOccupation: Int? = null,
    @SerializedName("hasHouseKeeper") var hasHouseKeeper: String? = null,
    @SerializedName("isSmokingAllowed") var isSmokingAllowed: String? = null,
    @SerializedName("arePetsAllowed") var arePetsAllowed: String? = null,
    @SerializedName("bedType") var bedType: Int? = null,
    @SerializedName("couplesAllowed") var couplesAllowed: String? = null,
    @SerializedName("childrenAllowed") var childrenAllowed: String? = null,
    @SerializedName("hasPrivateBathroom") var hasPrivateBathroom: String? = null,
    @SerializedName("availableFrom") var availableFrom: String? = null,
    @SerializedName("windowView") var windowView: Int? = null,
    @SerializedName("isAccessible") var isAccessible: String? = null,
    @SerializedName("flatLocation") var flatLocation: Int? = null,
    @SerializedName("isOwnerLiving") var isOwnerLiving: String? = null,
    @SerializedName("hasTerrace") var hasTerrace: String? = null,
    @SerializedName("hasSwimmingPool") var hasSwimmingPool: String? = null,
    @SerializedName("hasGarden") var hasGarden: String? = null,
    @SerializedName("TypologyName") var TypologyName: String? = null

)