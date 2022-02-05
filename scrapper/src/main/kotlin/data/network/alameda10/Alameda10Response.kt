package data.network.alameda10

import com.google.gson.annotations.SerializedName
import domain.entity.Location
import domain.entity.Property


data class Alameda10Response(
    @SerializedName("ads") var listings: List<Listing>,
) {
    fun toPropertyList(): List<Property> {
        return listings
            .filter { it.isHousing() }
            .map { listing ->
                with(listing) {
                    Property(
                        _id = id.toString(),
                        price = price().toDouble(),
                        title = addressName(),
                        location = Location(
                            lat = lat(),
                            lng = lng(),
                            name = addressName(),
                            isApproximated = lat() == 0.0 || lng() == 0.0,
                            isUnknown = lat() == 0.0 || lng() == 0.0
                        ),
                        surface = surface().toInt(),
                        dormCount = dormCount(),
                        bathCount = bathCount(),
                        avatarUrl = avatarUrl(),
                        tag = "",
                        propertyUrl = listingUrl(),
                        videoUrl = null,
                        fullDescription = fullDescription(),
                        locationDescription = null,
                        characteristics = listOf(),
                        photoGalleryUrls = galleryUrls(),
                        pdfUrl = null,
                        origin = Property.Type.ALAMEDA10.tag,
                        isActive = true,
                        createdAt = createdAt()
                    )
                }
            }
    }
}

