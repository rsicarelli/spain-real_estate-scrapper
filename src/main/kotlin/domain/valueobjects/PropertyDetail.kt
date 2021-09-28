package domain.valueobjects

data class PropertyDetail(
    val reference: String,
    val videoUrl: String?,
    val fullDescription: String?,
    val locationDescription: String?,
    val characteristics: List<String?>,
    val photosGalleryUrls: List<String?>,
    val lat: Double?,
    val lng: Double?,
    val pdfUrl: String?
) {

    companion object {
        fun imagesFullPath(images: List<String>): List<String> {
            return images.map {
                "https://www.aproperties.es$it"
            }
        }
    }

}