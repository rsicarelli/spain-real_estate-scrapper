package data.parser

import app.*
import data.parser.APropertiesSearchResultsParser.Mapper.items
import data.parser.APropertiesSearchResultsParser.Mapper.pagination
import domain.model.Pagination
import domain.model.PropertyItem
import domain.model.PropertySearchResult
import it.skrape.selects.Doc
import it.skrape.selects.DocElement
import it.skrape.selects.ElementNotFoundException
import it.skrape.selects.eachHref
import it.skrape.selects.html5.a
import it.skrape.selects.html5.h2

const val APROPERTIES_SEARCH_RESULT_PARSER_QUALIFIER = "APropertiesSearchResultsParser"

internal class APropertiesSearchResultsParser : Parser<PropertySearchResult> {

    @Throws(ElementNotFoundException::class)
    override fun parse(document: Doc): PropertySearchResult {
        return PropertySearchResult(
            pagination = document.pagination(),
            items = document.items()
        )

    }

    private object Mapper {
        private fun DocElement.propertyUrl(): String = a { findAll { "https://www.aproperties.es${eachHref.first()}" } }

        private fun DocElement.avatarUrl(): String =
            divWithClass(IMAGE_CLASS) { findFirst { "https://www.aproperties.es${eachSrc.first()}".replaceAfter("jpg", "").replace("thumb?src=/", "") } }

        private fun DocElement.reference(): String = spanWithClass(REFERENCE_CLASS) { findFirst { text } }

        private fun DocElement.price(): Double = divWithClass(PRICE_CLASS) { findFirst { text.convertToDouble() } }

        private fun DocElement.title(): String = h3WithClass(TITLE_CLASS) { findFirst { text } }

        private fun DocElement.location() = divWithClass(LOCATION_CLASS) { findFirst { text } }

        private fun DocElement.description() = divWithClass(CONTENT_CLASS) { findFirst { text } }

        private fun DocElement.surface() = this.divWithClass(SURFACE_CLASS) {
            findFirst {
                spanWithClass(VALUE_FIELD_CLASS) {
                    findFirst { text.replace("m", "").replace("2", "").trim().convertToInt() }
                }
            }
        }

        private fun DocElement.dormCount() = runCatchingOrDefault(null) {
            this.divWithClass(BEDROOMS_CLASS) {
                findFirst {
                    spanWithClass(VALUE_FIELD_CLASS) {
                        findFirst { text.convertToInt() }
                    }
                }
            }
        }

        private fun DocElement.bathCount() = runCatchingOrDefault(null) {
            divWithClass(BATHROOMS_CLASS) {
                findFirst {
                    spanWithClass(VALUE_FIELD_CLASS) {
                        findFirst { text.convertToInt() }
                    }
                }
            }
        }

        private fun DocElement.tag() = divWithClass(IMAGE_CLASS) {
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

        fun Doc.pagination(): Pagination {
            val pageUrl = runCatchingOrDefault("") {
                divWithClass(PAGINATION_CONTAINER) {
                    findFirst { eachHref.first().dropLast(1) }
                }
            }


            val pageCount = runCatchingOrDefault(0) {
                liWithClass(PAGINATION_LAST_CLASS) {
                    findFirst { a { findFirst { text.toInt() } } }
                }
            }


            val totalItems = runCatchingOrDefault(0) {
                sectionWithClass(PAGE_COUNT) {
                    findFirst {
                        h2 { findFirst { text.convertToInt() } }
                    }
                }
            }

            if (pageUrl.isEmpty()) return Pagination(totalItems, emptyList())

            return Pagination(
                totalItems = totalItems,
                pagesUrl = mutableListOf<String>().apply {
                    if (pageUrl.isEmpty()) return@apply
                    //Drop first page
                    for (i in 2..pageCount) {
                        add("https://www.aproperties.es$pageUrl$i")
                    }
                }
            )
        }

        fun Doc.items(): List<PropertyItem> {
            return ulWithClass("properties-list") {
                findFirst {
                    divWithClass(ROOT_CLASS) {
                        findAll {
                            map { docElement ->
                                with(docElement) {
                                    PropertyItem(
                                        propertyUrl = propertyUrl(),
                                        imageUrl = avatarUrl(),
                                        reference = reference(),
                                        price = price(),
                                        title = title(),
                                        location = location().split(",").first().trim(),
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
            }
        }

        private const val PAGINATION_LAST_CLASS = "pagination__last"

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
        private const val PAGE_COUNT = "top-results"
        private const val PAGINATION_CONTAINER = "pagination__container"
    }
}
