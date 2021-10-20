package domain.entity

data class Property(
    override val _id: String,
    val price: Double,
    val title: String,
    val location: Location,
    val surface: Int,
    val dormCount: Int?,
    val bathCount: Int?,
    val avatarUrl: String,
    val tag: String?,
    val propertyUrl: String,
    val videoUrl: String?,
    val fullDescription: String?,
    val locationDescription: String?,
    val characteristics: List<String?>,
    val photoGalleryUrls: List<String?>,
    val pdfUrl: String?,
    val origin: String,
    val isActive: Boolean = true,
) : Model {

    sealed class Type(val tag: String) {
        object APROPERTIES : Type("aProperties")
        object ENGELS : Type("engels")
    }

    sealed class Tag(val identifier: String) {
        object EMPTY : Tag("")
        object NEW : Tag("NEW")
        object RESERVED : Tag("RESERVED")
        object RENTED : Tag("RENTED")
    }
}

fun String?.toTag() = this?.let {
    return@let when (it.uppercase()) {
        Property.Tag.NEW.identifier -> Property.Tag.NEW.identifier
        Property.Tag.RESERVED.identifier -> Property.Tag.RESERVED.identifier
        Property.Tag.RENTED.identifier -> Property.Tag.RENTED.identifier
        else -> Property.Tag.EMPTY.identifier
    }
} ?: Property.Tag.EMPTY