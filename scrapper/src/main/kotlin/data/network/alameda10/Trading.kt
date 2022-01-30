package data.network.alameda10

import com.google.gson.annotations.SerializedName


data class Trading(
    @SerializedName("propertyArea") var propertyArea: Int? = null,
    @SerializedName("usableArea") var usableArea: String? = null,
    @SerializedName("bathNumber") var bathNumber: Int? = null,
    @SerializedName("builtType") var builtType: Int? = null,
    @SerializedName("constructionYear") var constructionYear: Int? = null,
    @SerializedName("hasArchive") var hasArchive: Boolean? = null,
    @SerializedName("hasHeating") var hasHeating: Boolean? = null,
    @SerializedName("office") var office: Office? = Office(),
    @SerializedName("warehouse") var warehouse: Warehouse? = Warehouse(),
    @SerializedName("casaBuiltType") var casaBuiltType: String? = null
)