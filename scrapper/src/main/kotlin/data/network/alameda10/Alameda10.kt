package data.network.alameda10

import app.epochToDate
import com.google.gson.annotations.SerializedName
import domain.entity.Location
import domain.entity.Property


data class Alameda10(
    @SerializedName("ads") var listings: List<Listing>,
) {
    fun toPropertyList(): List<Property> {
        return listings
            .filter { it.property.housing.propertyArea != null }
            .map { listing ->
                with(listing) {
                    Property(
                        _id = id.toString(),
                        price = prices.byOperation.RENT?.price?.toDouble() ?: 0.0,
                        title = property.address?.AddressName ?: "",
                        location = Location(
                            lat = property.address?.coordinates?.latitude ?: 0.0,
                            lng = property.address?.coordinates?.longitude ?: 0.0,
                            name = property.address?.AddressName ?: "",
                            isApproximated = property.address?.coordinates?.latitude == null,
                            isUnknown = property.address?.coordinates?.latitude == null
                        ),
                        surface = property.housing.propertyArea?.toDouble()?.toInt() ?: 0,
                        dormCount = property.housing.roomNumber?.toDouble()?.toInt() ?: 0,
                        bathCount = property.housing.bathNumber?.toDouble()?.toInt() ?: 0,
                        avatarUrl = multimedias.MainPicture ?: "",
                        tag = "",
                        propertyUrl = "http://www.alameda10dt.es/ad/${id}",
                        videoUrl = null,
                        fullDescription = comments.adComments.find { it.language == 1 }?.propertyComment
                            ?: comments.adComments.last().propertyComment
                            ?: "",
                        locationDescription = null,
                        characteristics = listOf(),
                        photoGalleryUrls = listOf(),
                        pdfUrl = null,
                        origin = Property.Type.ALAMEDA10.tag,
                        isActive = true,
                        createdAt = epochToDate(creation)
                    )
                }

            }
    }
}

