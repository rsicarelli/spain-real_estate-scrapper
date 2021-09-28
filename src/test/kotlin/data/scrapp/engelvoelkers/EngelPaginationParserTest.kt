package data.scrapp.engelvoelkers

import org.junit.jupiter.api.Test
import utils.fixtures.Engel.defaultSearchResultsWithPagination
import utils.fixtures.Engel.emptySearchResult
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull


class EngelPaginationParserTest {

    private val parser by lazy { EngelPaginationParser() }

    @Test
    fun `given valid html when parse then should return valid pagination`() {
        //given
        val (searchResultDocument, _, expectedPagination) = defaultSearchResultsWithPagination()

        //when
        val result = parser.parse(searchResultDocument)

        //then
        assertNotNull(result)
        assertEquals(expectedPagination, result)
        assertEquals(234, result.totalResults)
    }

    @Test
    fun `given empty search result when parse then should return null pagination`() {
        //given
        val searchResultDocument = emptySearchResult()

        //when
        val result = parser.parse(searchResultDocument)

        //then
        assertNull(result)
    }

}