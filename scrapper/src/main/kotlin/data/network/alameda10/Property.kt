package data.network.alameda10

import com.google.gson.annotations.SerializedName


data class Property(

    @SerializedName("deposit") var deposit: Int? = null,
    @SerializedName("communityCosts") var communityCosts: String? = null,
    @SerializedName("typology") var typology: Int? = null,
    @SerializedName("propertyType") var propertyType: Int? = null,
    @SerializedName("address") var address: Address?,
    @SerializedName("parent") var parent: Parent?,
    @SerializedName("housing") var housing: Housing,
    @SerializedName("land") var land: Land?,
    @SerializedName("storageRoom") var storageRoom: StorageRoom?,
    @SerializedName("room") var room: Room?,
    @SerializedName("IsHousing") var IsHousing: Boolean? = null,
    @SerializedName("IsHome") var IsHome: Boolean? = null,
    @SerializedName("IsChalet") var IsChalet: Boolean? = null,
    @SerializedName("IsCountryHouse") var IsCountryHouse: Boolean? = null,
    @SerializedName("IsTrading") var IsTrading: Boolean? = null,
    @SerializedName("IsOffice") var IsOffice: Boolean? = null,
    @SerializedName("IsWarehouse") var IsWarehouse: Boolean? = null,
    @SerializedName("IsGarage") var IsGarage: Boolean? = null,
    @SerializedName("IsLand") var IsLand: Boolean? = null,
    @SerializedName("IsBuilding") var IsBuilding: Boolean? = null,
    @SerializedName("IsRoom") var IsRoom: Boolean? = null,
    @SerializedName("IsStorageRoom") var IsStorageRoom: Boolean? = null,
    @SerializedName("HasEnergyCertification") var HasEnergyCertification: Boolean? = null,
    @SerializedName("RoomNumber") var RoomNumber: Int? = null,
    @SerializedName("BathNumber") var BathNumber: Int? = null,
    @SerializedName("ConstructedArea") var ConstructedArea: Int? = null,
    @SerializedName("TypologyName") var TypologyName: String? = null

)