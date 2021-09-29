package domain.entity

import domain.valueobjects.PropertyDetail
import domain.valueobjects.PropertyItem

data class Property(
    val reference: String,
    val price: Double,
    val title: String,
    val location: String,
    val surface: String?,
    val dormCount: Int?,
    val description: String,
    val bathCount: Int?,
    val avatarUrl: String,
    val tag: String?,
    val propertyUrl: String,
    val videoUrl: String?,
    val fullDescription: String?,
    val locationDescription: String?,
    val characteristics: List<String?>,
    val photoGalleryUrls: List<String?>,
    val lat: Double?,
    val lng: Double?,
    val pdfUrl: String?
) {
    fun toMap() =
        mapOf(
            "reference" to reference,
            "price" to price,
            "title" to title,
            "location" to location,
            "surface" to surface,
            "dormCount" to dormCount,
            "description" to description,
            "bathCount" to bathCount,
            "avatarUrl" to avatarUrl,
            "tag" to Tags.fromString(tag)::class.simpleName,
            "propertyUrl" to propertyUrl,
            "videoUrl" to videoUrl,
            "fullDescription" to fullDescription,
            "characteristics" to characteristics,
            "photoGalleryUrls" to photoGalleryUrls,
            "lat" to lat,
            "lng" to lng,
            "pdfUrl" to pdfUrl,
            "locationDescription" to locationDescription
        )

    sealed class Tags(val identifier: String) {
        object EMPTY : Tags("")
        object NEW : Tags("nuevo")
        object RESERVED : Tags("reservada")
        object RENTED : Tags("alquilada")

        companion object {
            fun fromString(tag: String?): Tags {
                return tag?.let {
                    return@let when (it.toLowerCase()) {
                        NEW.identifier, "new" -> NEW
                        RESERVED.identifier -> RESERVED
                        RENTED.identifier -> RENTED
                        else -> EMPTY
                    }
                } ?: EMPTY
            }

        }
    }

    companion object {
        fun combine(propertySearchResult: PropertyItem, propertyDetail: PropertyDetail) =
            Property(
                reference = propertySearchResult.reference,
                price = propertySearchResult.price,
                title = propertySearchResult.title,
                location = propertySearchResult.location,
                surface = propertySearchResult.surface,
                dormCount = propertySearchResult.dormCount,
                description = propertySearchResult.description,
                bathCount = propertySearchResult.bathCount,
                avatarUrl = propertySearchResult.imageUrl,
                tag = propertySearchResult.tag,
                propertyUrl = propertySearchResult.propertyUrl,
                videoUrl = propertyDetail.videoUrl,
                fullDescription = propertyDetail.fullDescription,
                characteristics = propertyDetail.characteristics,
                photoGalleryUrls = propertyDetail.photosGalleryUrls,
                lat = propertyDetail.lat,
                lng = propertyDetail.lng,
                locationDescription = propertyDetail.locationDescription,
                pdfUrl = propertyDetail.pdfUrl
            )

        fun fromMap(data: Map<String, Any>) =
            Property(
                reference = data["reference"] as String,
                price = data["price"] as Double,
                title = data["title"] as String,
                location = data["location"] as String,
                surface = data["surface"] as String?,
                dormCount = (data["dormCount"] as Long?)?.toInt(),
                description = data["description"] as String? ?: "",
                bathCount = (data["bathCount"] as Long?)?.toInt(),
                avatarUrl = data["avatarUrl"] as String,
                tag = data["tag"] as String?,
                propertyUrl = data["propertyUrl"] as String,
                videoUrl = data["videoUrl"] as String?,
                fullDescription = data["fullDescription"] as String?,
                characteristics = data["characteristics"] as List<String?>,
                photoGalleryUrls = data["photoGalleryUrls"] as List<String?>,
                lat = data["lat"] as Double?,
                lng = data["lng"] as Double?,
                pdfUrl = data["pdfUrl"] as String? ?: "",
                locationDescription = data["locationDescription"] as String? ?: ""
            )
    }

    sealed class Type(val tag: String) {
        object APROPERTIES : Type("aProperties")
        object ENGELS : Type("engels")
    }
}