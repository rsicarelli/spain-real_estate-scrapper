package data.network.alameda10

import com.google.gson.annotations.SerializedName


data class Address(

    @SerializedName("id") var id: Int?,
    @SerializedName("kmNumber") var kmNumber: String?,
    @SerializedName("coordinates") var coordinates: Coordinates?,
    @SerializedName("movedCoordinates") var movedCoordinates: MovedCoordinates?,
    @SerializedName("buildingName") var buildingName: String?,
    @SerializedName("streetTypeId") var streetTypeId: Int?,
    @SerializedName("streetNumber") var streetNumber: Int?,
    @SerializedName("postalBox") var postalBox: String?,
    @SerializedName("streetName") var streetName: String?,
    @SerializedName("block") var block: String?,
    @SerializedName("door") var door: String?,
    @SerializedName("postalCode") var postalCode: String?,
    @SerializedName("stairs") var stairs: String?,
    @SerializedName("floorNumber") var floorNumber: String?,
    @SerializedName("streetNumberSuffix") var streetNumberSuffix: String?,
    @SerializedName("isInTopFloor") var isInTopFloor: Boolean?,
    @SerializedName("addressVisible") var addressVisible: Int?,
    @SerializedName("isDirty") var isDirty: Boolean?,
    @SerializedName("AddressName") var AddressName: String?
)