package data.scrapp.aproperties

import app.liWithClass
import data.scrapp.Parser
import data.scrapp.aproperties.APropertiesPaginationParser.Mapper.pagination
import domain.valueobjects.APropertiesPagination
import it.skrape.selects.Doc
import it.skrape.selects.html5.a

const val APROPERTIES_PAGINATION_PARSER_QUALIFIER = "APropertiesPaginationParser"

internal class APropertiesPaginationParser : Parser<APropertiesPagination> {
    override fun parse(document: Doc): APropertiesPagination {
        return document.pagination()
    }

    private object Mapper {
        fun Doc.pagination() = this.liWithClass(PAGINATION_LAST_CLASS) {
            findFirst {
                APropertiesPagination(
                    pageCount = a {
                        findFirst {
                            text.toInt()
                        }
                    }
                )
            }
        }

        private const val PAGINATION_LAST_CLASS = "pagination__last"
    }
}