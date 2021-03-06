package data.parser

import app.*
import data.parser.EngelPropertyDetailParser.Mapper.characteristics
import data.parser.EngelPropertyDetailParser.Mapper.exposeeDetail
import data.parser.EngelPropertyDetailParser.Mapper.fullDescription
import data.parser.EngelPropertyDetailParser.Mapper.locationDescription
import data.parser.EngelPropertyDetailParser.Mapper.photosGalleryUrls
import data.parser.EngelPropertyDetailParser.Mapper.reference
import domain.valueobject.PropertyDetail
import it.skrape.selects.Doc
import it.skrape.selects.DocElement
import it.skrape.selects.eachHref
import it.skrape.selects.eachText


const val ENGEL_PROPERTY_DETAIL_PARSER_QUALIFIER = "EngelPropertyDetailParser"

internal class EngelPropertyDetailParser : Parser<PropertyDetail> {
    override fun parse(document: Doc): PropertyDetail =
        document.exposeeDetail { docElement ->
            PropertyDetail(
                reference = docElement.reference(),
                fullDescription = docElement.fullDescription(),
                characteristics = docElement.characteristics(),
                locationDescription = docElement.locationDescription(),
                photosGalleryUrls = document.photosGalleryUrls(),
                lat = null, //engel doesn't provide lat lng
                lng = null,
                pdfUrl = "",
                videoUrl = ""
            )
        }

    private object Mapper {
        fun Doc.photosGalleryUrls() = runCatchingOrDefault(emptyList()) {
            aWithClass(GALLERY_IMAGES) { findAll { eachHref } }
        }

        fun Doc.exposeeDetail(action: (DocElement) -> PropertyDetail) =
            divWithClass(EXPOSEE_DETAIL) {
                findFirst {
                    return@findFirst action(this)
                }
            }

        fun DocElement.reference() = runCatchingOrDefault("") {
            spanWithClass(FACT_VALUE) { findSecond { text } }
        }

        fun DocElement.fullDescription() = runCatchingOrDefault("") {
            pWithClass(PROPERTY_DETAILS) { findFirst { text } }
        }

        fun DocElement.characteristics(): List<String> = runCatchingOrDefault(emptyList()) {
            ulWithClass(CHARACTERISTICS_ROOT) {
                findByIndex(3) {
                    liWithClass(CHARACTERISTICS) {
                        findAll { eachText }.filterNot {
                            it.lowercase().contains("rooms")
                        }.filterNot {
                            it.lowercase().contains("surface")
                        }.map {
                            if (it.lowercase().contains("property subtype"))
                                it.replace("Property subtype ", "")
                            else
                                it
                        }
                    }
                }
            }
        }

        fun DocElement.locationDescription() = runCatchingOrDefault("") {
            pWithClass(PROPERTY_DETAILS) { findSecond { text } }
        }

        private const val GALLERY_IMAGES = "ev-image-gallery-image-link "
        private const val EXPOSEE_DETAIL = "ev-exposee-detail"
        private const val FACT_VALUE = "ev-exposee-detail-fact-value"
        private const val PROPERTY_DETAILS = "ev-exposee-text"
        private const val CHARACTERISTICS_ROOT = "ev-exposee-content.ev-exposee-detail-facts"
        private const val CHARACTERISTICS = "ev-exposee-detail-fact"
    }
}
