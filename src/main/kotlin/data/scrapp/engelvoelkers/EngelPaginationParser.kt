package data.scrapp.engelvoelkers

import app.convertToInt
import app.h1WithClass
import data.scrapp.Parser
import data.scrapp.engelvoelkers.EngelPaginationParser.Mapper.pagination
import domain.valueobjects.EngelPagination
import it.skrape.selects.Doc

const val ENGEL_PAGINATION_PARSER_QUALIFIER = "EngelPaginationParser"

internal class EngelPaginationParser : Parser<EngelPagination> {

    override fun parse(document: Doc): EngelPagination {
        return EngelPagination(document.pagination())
    }

    private object Mapper {
        const val PAGINATION_LAST_CLASS = "ev-search-result-title"

        fun Doc.pagination() = h1WithClass(PAGINATION_LAST_CLASS) { findFirst { text.convertToInt() } }
    }
}
