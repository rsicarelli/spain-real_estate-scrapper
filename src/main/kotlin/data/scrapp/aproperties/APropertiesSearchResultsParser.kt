package data.scrapp.aproperties

import app.*
import data.scrapp.Parser
import data.scrapp.aproperties.APropertiesSearchResultsParser.Mapper.ROOT_CLASS
import data.scrapp.aproperties.APropertiesSearchResultsParser.Mapper.avatarUrl
import data.scrapp.aproperties.APropertiesSearchResultsParser.Mapper.bathCount
import data.scrapp.aproperties.APropertiesSearchResultsParser.Mapper.description
import data.scrapp.aproperties.APropertiesSearchResultsParser.Mapper.dormCount
import data.scrapp.aproperties.APropertiesSearchResultsParser.Mapper.location
import data.scrapp.aproperties.APropertiesSearchResultsParser.Mapper.price
import data.scrapp.aproperties.APropertiesSearchResultsParser.Mapper.propertyUrl
import data.scrapp.aproperties.APropertiesSearchResultsParser.Mapper.reference
import data.scrapp.aproperties.APropertiesSearchResultsParser.Mapper.surface
import data.scrapp.aproperties.APropertiesSearchResultsParser.Mapper.tag
import data.scrapp.aproperties.APropertiesSearchResultsParser.Mapper.title
import domain.valueobjects.PropertySearchResult
import it.skrape.selects.Doc
import it.skrape.selects.DocElement
import it.skrape.selects.ElementNotFoundException
import it.skrape.selects.eachHref
import it.skrape.selects.html5.a

const val APROPERTIES_SEARCH_RESULT_PARSER_QUALIFIER = "APropertiesSearchResultsParser"

internal class APropertiesSearchResultsParser : Parser<List<PropertySearchResult>> {

    @Throws(ElementNotFoundException::class)
    override fun parse(document: Doc): List<PropertySearchResult> {
        return document.divWithClass(ROOT_CLASS) {
            findAll {
                map { docElement ->
                    with(docElement) {
                        PropertySearchResult(
                            propertyUrl = propertyUrl(),
                            imageUrl = avatarUrl(),
                            reference = reference(),
                            price = price(),
                            title = title(),
                            location = location(),
                            description = description(),
                            surface = surface(),
                            dormCount = dormCount(),
                            bathCount = bathCount(),
                            tag = tag()
                        )
                    }
                }
            }
        }
    }

    private object Mapper {
        fun DocElement.propertyUrl(): String = a { findAll { eachHref.first() } }

        fun DocElement.avatarUrl(): String =
            divWithClass(IMAGE_CLASS) { findFirst { "https://www.aproperties.es${eachSrc.first()}" } }

        fun DocElement.reference(): String = spanWithClass(REFERENCE_CLASS) { findFirst { text } }

        fun DocElement.price(): Double = divWithClass(PRICE_CLASS) { findFirst { text.convertToDouble() } }

        fun DocElement.title(): String = h3WithClass(TITLE_CLASS) { findFirst { text } }

        fun DocElement.location() = divWithClass(LOCATION_CLASS) { findFirst { text } }

        fun DocElement.description() = divWithClass(CONTENT_CLASS) { findFirst { text } }

        fun DocElement.surface() = runCatchingOrDefault(null) {
            this.divWithClass(SURFACE_CLASS) {
                findFirst {
                    spanWithClass(VALUE_FIELD_CLASS) {
                        findFirst { text }
                    }
                }
            }
        }

        fun DocElement.dormCount() = runCatchingOrDefault(null) {
            this.divWithClass(BEDROOMS_CLASS) {
                findFirst {
                    spanWithClass(VALUE_FIELD_CLASS) {
                        findFirst { text.convertToInt() }
                    }
                }
            }
        }

        fun DocElement.bathCount() = runCatchingOrDefault(null) {
            divWithClass(BATHROOMS_CLASS) {
                findFirst {
                    spanWithClass(VALUE_FIELD_CLASS) {
                        findFirst { text.convertToInt() }
                    }
                }
            }
        }

        fun DocElement.tag() = divWithClass(IMAGE_CLASS) {
            findFirst {
                var tag = ""
                val tagResult = runCatching {
                    divWithClass(TAG_CLASS) { findFirst { text } }
                }
                if (tagResult.isFailure) {
                    val ribbonResult = runCatching {
                        divWithClass(RIBBON_TAG) { findFirst { text } }
                    }
                    if (ribbonResult.isSuccess) {
                        tag = ribbonResult.getOrDefault("")
                    }
                } else {
                    tag = tagResult.getOrDefault("")
                }

                tag
            }
        }

        const val ROOT_CLASS = "propertyBlock"

        private const val IMAGE_CLASS = "propertyBlock__image"
        private const val TAG_CLASS = "propertyBlock__tag"
        private const val RIBBON_TAG = "propertyBlock__ribbon"
        private const val REFERENCE_CLASS = "propertyBlock__reference"
        private const val PRICE_CLASS = "propertyBlock__price"
        private const val TITLE_CLASS = "propertyBlock__title"
        private const val LOCATION_CLASS = "propertyBlock__location"
        private const val CONTENT_CLASS = "propertyBlock__content"
        private const val SURFACE_CLASS = "propertyBlock__surface"
        private const val VALUE_FIELD_CLASS = "propertyBlock__value"
        private const val BEDROOMS_CLASS = "propertyBlock__bedrooms"
        private const val BATHROOMS_CLASS = "propertyBlock__bathrooms"
    }
}
