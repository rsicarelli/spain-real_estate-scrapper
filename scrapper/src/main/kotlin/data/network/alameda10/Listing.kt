package data.network.alameda10

import app.epochToDate
import com.google.gson.annotations.SerializedName


data class Listing(
    @SerializedName("id") val id: Int,
    @SerializedName("comments") val description: Descriptions,
    @SerializedName("multimedias") val multimedia: Multimedia,
    @SerializedName("prices") val prices: Price,
    @SerializedName("property") val property: Property,
    @SerializedName("creation") val creation: Long,
    @SerializedName("IsRent") val isRent: Boolean,
    @SerializedName("IsInactive") val isInactive: Boolean,
) {
    fun lat() = property.address.coordinates.latitude
    fun lng() = property.address.coordinates.longitude
    fun price() = prices.getPrice()
    fun addressName() = property.address.name
    fun surface() = property.surfaceArea
    fun dormCount() = property.dormCount
    fun bathCount() = property.bathCount
    fun avatarUrl() = multimedia.mainPicture
    fun listingUrl() = "http://www.alameda10dt.es/ad/${id}"
    fun fullDescription(): String {
        if (description.descriptions.isEmpty()) return ""

        return description.descriptions.find { it.language == 1 }?.propertyComment
            ?: description.descriptions.last().propertyComment
    }

    fun createdAt() = epochToDate(creation)
    fun isHousing() = property.isHousing
}

data class Price(@SerializedName("byOperation") val byOperation: ByOperation) {
    fun getPrice(): Int = byOperation.rent.price
}

data class ByOperation(@SerializedName("RENT") var rent: Rent)

data class Rent(@SerializedName("price") var price: Int)

data class Descriptions(@SerializedName("adComments") val descriptions: ArrayList<Description>)

data class Description(
    @SerializedName("propertyComment") val propertyComment: String,
    @SerializedName("language") val language: Int
)

data class Property(
    @SerializedName("address") var address: Address,
    @SerializedName("IsHousing") val isHousing: Boolean,
    @SerializedName("RoomNumber") var dormCount: Int,
    @SerializedName("BathNumber") var bathCount: Int,
    @SerializedName("ConstructedArea") val surfaceArea: Double
)

data class Address(
    @SerializedName("coordinates") var coordinates: Coordinates,
    @SerializedName("AddressName") var name: String
)

data class Coordinates(
    @SerializedName("longitude") var longitude: Double,
    @SerializedName("latitude") var latitude: Double
)

data class Multimedia(
    @SerializedName("pictures") val pictures: ArrayList<Pictures>,
    @SerializedName("professionalVideos") val professionalVideos: ArrayList<String>,
    @SerializedName("videos") val videos: ArrayList<Videos>,
    @SerializedName("MainPicture") val mainPicture: String,
    @SerializedName("CountPictures") val galleryPictureCount: Int?
)

data class Pictures(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("masterPath") val masterPath: String? = null,
    @SerializedName("masterName") val masterName: String? = null,
    @SerializedName("heightPixels") val heightPixels: Int? = null,
    @SerializedName("widthPixels") val widthPixels: Int? = null,
    @SerializedName("masterIsThumbnail") val masterIsThumbnail: Boolean? = null,
    @SerializedName("position") val position: Int? = null,
    @SerializedName("aspectRatioId") val aspectRatioId: Int? = null,
    @SerializedName("multimediaTag") val multimediaTag: String? = null
)

data class Videos(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("masterPath") val masterPath: String? = null,
    @SerializedName("masterName") val masterName: String? = null,
    @SerializedName("heightPixels") val heightPixels: Int? = null,
    @SerializedName("widthPixels") val widthPixels: Int? = null,
    @SerializedName("masterIsThumbnail") val masterIsThumbnail: Boolean? = null,
    @SerializedName("position") val position: Int? = null,
    @SerializedName("aspectRatioId") val aspectRatioId: Int? = null,
    @SerializedName("rotationDegree") val rotationDegree: Int? = null,
    @SerializedName("multimediaTag") val multimediaTag: String? = null
)