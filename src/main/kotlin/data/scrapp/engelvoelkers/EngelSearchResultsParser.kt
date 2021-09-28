package data.scrapp.engelvoelkers

import app.*
import data.scrapp.Parser
import data.scrapp.engelvoelkers.EngelSearchResultsParser.Mapper.bathCount
import data.scrapp.engelvoelkers.EngelSearchResultsParser.Mapper.dormCount
import data.scrapp.engelvoelkers.EngelSearchResultsParser.Mapper.imageUrl
import data.scrapp.engelvoelkers.EngelSearchResultsParser.Mapper.location
import data.scrapp.engelvoelkers.EngelSearchResultsParser.Mapper.price
import data.scrapp.engelvoelkers.EngelSearchResultsParser.Mapper.propertyUrl
import data.scrapp.engelvoelkers.EngelSearchResultsParser.Mapper.reference
import data.scrapp.engelvoelkers.EngelSearchResultsParser.Mapper.searchResults
import data.scrapp.engelvoelkers.EngelSearchResultsParser.Mapper.surface
import data.scrapp.engelvoelkers.EngelSearchResultsParser.Mapper.tag
import data.scrapp.engelvoelkers.EngelSearchResultsParser.Mapper.title
import domain.valueobjects.PropertySearchResult
import it.skrape.selects.Doc
import it.skrape.selects.DocElement
import it.skrape.selects.html5.div

const val ENGEL_SEARCH_RESULTS_PARSER_QUALIFIER = "EngelSearchResultsParser"

internal class EngelSearchResultsParser : Parser<List<PropertySearchResult>> {
    override fun parse(document: Doc): List<PropertySearchResult> {
        return document.searchResults { docElements ->
            docElements.filter { it.reference().isNotEmpty() }
                .map { docElement ->
                    with(docElement) {
                        PropertySearchResult(
                            reference = reference(),
                            price = price(),
                            title = title(),
                            location = location(),
                            surface = surface(),
                            dormCount = dormCount(),
                            bathCount = bathCount(),
                            description = "",
                            tag = tag(),
                            propertyUrl = propertyUrl(),
                            imageUrl = imageUrl()
                        )
                    }
                }
        }
    }

    private object Mapper {
        fun Doc.searchResults(action: (List<DocElement>) -> List<PropertySearchResult>) =
            divWithClass(ROOT_CLASS) {
                findAll {
                    return@findAll action(this)
                }
            }

        fun DocElement.reference() = referenceRegex.find(this.toString())
            ?.value?.split("'")
            ?.first { it.contains("W") }
            ?: ""

        fun DocElement.price() = runCatchingOrDefault(0.0) {
            divWithClass(PRICE) {
                findFirst {
                    divWithClass(FIELD_VALUE) {
                        findFirst {
                            text.convertToDouble()
                        }
                    }
                }
            }
        }

        fun DocElement.title() = runCatchingOrDefault("") {
            divWithClass(TITLE) { findFirst { text } }
        }

        fun DocElement.location() = runCatchingOrDefault("") {
            divWithClass(SUBTITLE) { findFirst { text } }
        }

        fun DocElement.surface() = runCatchingOrDefault("") {
            divWithClass(ATTRIBUTE) {
                findThird {
                    spanWithClass(ATTRIBUTE_VALUE) {
                        findFirst { text }
                    }
                }
            }
        }

        fun DocElement.dormCount() = runCatchingOrDefault(0) {
            divWithClass(ATTRIBUTE) {
                findFirst {
                    spanWithClass(ATTRIBUTE_VALUE) {
                        findFirst { text.convertToInt() }
                    }
                }
            }
        }

        fun DocElement.bathCount() = runCatchingOrDefault(0) {
            divWithClass(ATTRIBUTE) {
                findSecond {
                    spanWithClass(ATTRIBUTE_VALUE) {
                        findFirst { text.convertToInt() }
                    }
                }
            }
        }

        fun DocElement.tag() = runCatchingOrDefault("") {
            divWithClass(TAG) {
                findFirst {
                    div { this@findFirst.text }
                }
            }
        }

        fun DocElement.propertyUrl() = runCatchingOrDefault("") {
            aWithClass(PROPERTY_URL) {
                findFirst { eachHref.first() }
            }
        }

        fun DocElement.imageUrl() = runCatchingOrDefault("") {
            divWithClass(IMAGE) {
                findFirst { eachImage.values.first().toString() }
            }
        }

        private const val ROOT_CLASS = "col-lg-4"
        private const val PRICE = "ev-teaser-price"
        private const val FIELD_VALUE = "ev-value"
        private const val TITLE = "ev-teaser-title"
        private const val SUBTITLE = "ev-teaser-subtitle"
        private const val ATTRIBUTE = "ev-teaser-attribute"
        private const val ATTRIBUTE_VALUE = "ev-teaser-attribute-value"
        private const val TAG = "ev-new-profile"
        private const val PROPERTY_URL = "ev-property-container"
        private const val IMAGE = "slide"
    }

    companion object {
        val referenceRegex = Regex("displayId_: (.*?),")
    }
}
