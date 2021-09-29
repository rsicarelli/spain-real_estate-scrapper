package data.parser

import app.*
import data.parser.EngelSearchResultsParser.Mapper.items
import data.parser.EngelSearchResultsParser.Mapper.pagination
import domain.model.Pagination
import domain.model.PropertyItem
import domain.model.PropertySearchResult
import it.skrape.selects.Doc
import it.skrape.selects.DocElement
import it.skrape.selects.html5.div

const val ENGEL_SEARCH_RESULTS_PARSER_QUALIFIER = "EngelSearchResultsParser"

internal class EngelSearchResultsParser : Parser<PropertySearchResult> {
    override fun parse(document: Doc): PropertySearchResult {
        return PropertySearchResult(
            pagination = document.pagination(),
            items = document.items()
        )
    }

    private object Mapper {
        fun Doc.searchResults(action: (List<DocElement>) -> List<PropertyItem>) =
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

        fun Doc.totalItems() = h1WithClass(PAGINATION_LAST_CLASS) { findFirst { text.convertToInt() } }

        fun Doc.pageUrl() = divWithClass(PAGINATION_CONTAINER) {
            findFirst {
                liWithClass(PAGINATION_ITEM) {
                    findFirst { eachHref.first() }
                }
            }
        }

        /*
        * First page contains 18 items, being 2 of them promotional banners
        * Pagination should iterate adding 18 items in the page index
        * */
        fun Doc.pagination(): Pagination {
            val totalItems = totalItems()
            val pageUrl = pageUrl()

            var pageIndex = 16
            val pageIndexToken = "startIndex=0"

            val pages = mutableListOf(
                pageUrl.replace(pageIndexToken, "startIndex=$pageIndex")
            )

            while (totalItems - pageIndex >= 18) {
                pageIndex += 18
                val url = pageUrl.replace(pageIndexToken, "startIndex=$pageIndex")
                pages.add(url)

            }
            return Pagination(
                totalItems = totalItems,
                pagesUrl = pages
            )
        }

        fun Doc.items(): List<PropertyItem> {
            return searchResults { docElements ->
                docElements
                    .filter { it.reference().isNotEmpty() }
                    .map { docElement ->
                        with(docElement) {
                            PropertyItem(
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
        private const val PAGINATION_LAST_CLASS = "ev-search-result-title"
        private const val PAGINATION_CONTAINER = "ev-pager"
        private const val PAGINATION_ITEM = "ev-pager-item"
    }

    companion object {
        val referenceRegex = Regex("displayId_: (.*?),")
    }
}
