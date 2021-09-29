package data.parser

import app.divWithClass
import app.liWithClass
import app.sectionWithClass
import app.spanWithClass
import data.parser.APropertiesPropertyDetailParser.Mapper.characteristics
import data.parser.APropertiesPropertyDetailParser.Mapper.description
import data.parser.APropertiesPropertyDetailParser.Mapper.galleryImages
import data.parser.APropertiesPropertyDetailParser.Mapper.latLng
import data.parser.APropertiesPropertyDetailParser.Mapper.pdfUrl
import data.parser.APropertiesPropertyDetailParser.Mapper.reference
import data.parser.APropertiesPropertyDetailParser.Mapper.videoUrl
import domain.model.PropertyDetail
import it.skrape.selects.Doc
import it.skrape.selects.eachHref
import it.skrape.selects.html5.a
import it.skrape.selects.text

const val APROPERTIES_PROPERTY_DETAIL_PARSER_QUALIFIER = "APropertiesPropertyDetailParser"

internal class APropertiesPropertyDetailParser : Parser<PropertyDetail> {
    override fun parse(document: Doc): PropertyDetail = with(document) {
        val latLng = latLng()
        return PropertyDetail(
            reference = reference(),
            videoUrl = videoUrl(),
            fullDescription = description(),
            characteristics = characteristics(),
            photosGalleryUrls = galleryImages(),
            lat = latLng.first,
            lng = latLng.second,
            locationDescription = "",
            pdfUrl = pdfUrl()
        )
    }

    companion object {
        val vimeoRegex =
            Regex("(?:http|https)?:?\\/?\\/?(?:www\\.|player\\.)?vimeo\\.com\\/(?:channels\\/(?:\\w+\\/)?|groups\\/(?:[^\\/]*)\\/videos\\/|video\\/|)(\\d+)(?:|\\/\\?)")
    }

    private object Mapper {
        private fun Doc.entireDocAsString() = findAll { this.toString() }

        fun Doc.videoUrl() = vimeoRegex.find(this.entireDocAsString())?.value

        fun Doc.description() = sectionWithClass(PROPERTY_CONTENT) { findAll { text } }

        fun Doc.characteristics(): List<String> =
            this.liWithClass(PROPERTY_CHARACTERISTICS) { findAll { text } }
                .split(" ")
                .let { it.subList(1, it.size) }

        fun Doc.galleryImages() = divWithClass(PROPERTY_GALLERY) {
            findAll {
                a {
                    withAttributeKey = PROPERTY_GALLERY_ITEM
                    findAll { eachHref }
                }
            }
        }.map { "https://www.aproperties.es$it" }

        fun Doc.latLng() = this.entireDocAsString()
            .substringAfter("var latlng = new google.maps.LatLng(")
            .substringBefore(")")
            .split(",")
            .map { it.toDoubleOrNull() }
            .let { geometry ->
                var lat: Double? = null
                var lng: Double? = null
                if (geometry.size > 1) {
                    lat = geometry[0]
                    lng = geometry[1]
                }
                Pair(lat, lng)
            }

        fun Doc.reference() = divWithClass(PROPERTY_REFERENCE) {
            findFirst {
                spanWithClass(PROPERTY_REF) { findFirst { text } }
            }
        }.replace("Ref. ", "")

        fun Doc.pdfUrl() = "https://www.aproperties.es/pdf/properties/es/${this.reference().toLowerCase()}.pdf"

        private const val PROPERTY_CONTENT = "propertyContent"
        private const val PROPERTY_CHARACTERISTICS = "description__featuresCaractListItem"
        private const val PROPERTY_GALLERY = "propertyGallery__mainViewer"
        private const val PROPERTY_GALLERY_ITEM = "data-fancybox"
        private const val PROPERTY_REFERENCE = "_leadform__titles"
        private const val PROPERTY_REF = "ref"
    }
}
