package data.scrapp.aproperties

import org.junit.jupiter.api.Test
import utils.fixtures.AProperties.defaultSearchResultsWithPagination
import utils.fixtures.AProperties.emptySearchResult
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull


class APropertiesPaginationParserTest {

    private val parser by lazy { APropertiesPaginationParser() }

    @Test
    fun `given valid html when parse then should return valid pagination`() {
        //given
        val (searchResultDocument, _, expectedPagination) = defaultSearchResultsWithPagination()

        //when
        val result = parser.parse(searchResultDocument)

        //then
        assertNotNull(result)
        assertEquals(expectedPagination, result)
        assertEquals(6, result.pageCount)
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