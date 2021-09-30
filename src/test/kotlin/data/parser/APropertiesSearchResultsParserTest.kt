package data.parser

import it.skrape.selects.ElementNotFoundException
import org.junit.jupiter.api.Test
import utils.extractAuthority
import utils.fixtures.AProperties.defaultSearchResults
import utils.fixtures.AProperties.emptySearchResult
import utils.fixtures.AProperties.invalidSearchResult
import utils.fixtures.AProperties.searchResultsWithPagination
import utils.fixtures.AProperties.singleSearchResultWithMissingData
import kotlin.test.*

class APropertiesSearchResultsParserTest {

    private val parser by lazy { APropertiesSearchResultsParser() }

    @Test
    fun `given valid document when parse then should return valid object`() {
        //given
        val (searchResultDoc, expectedObject) = defaultSearchResults()

        //when
        val result = parser.parse(searchResultDoc)

        //then
        assertEquals(expectedObject, result)
    }

    @Test
    fun `given valid document with new tag when parse then should build correct tag`() {
        //given
        val (searchResultDoc, expectedObject) = defaultSearchResults()
        val expectedSingleObject = expectedObject.items.filter { it.tag.equals("New") }

        //when
        val result = parser.parse(searchResultDoc).items.filter { it.tag.equals("New") }

        //then
        assertEquals(2, result.size)
        assertEquals(expectedSingleObject, result)
    }

    @Test
    fun `given valid document with reserved tag when parse then should build correct tag`() {
        //given
        val (searchResultDoc, expectedObject) = defaultSearchResults()
        val expectedSingleObject = expectedObject.items.filter { it.tag.equals("Reserved") }

        //when
        val result = parser.parse(searchResultDoc).items.filter { it.tag.equals("Reserved") }

        //then
        assertEquals(2, result.size)
        assertEquals(expectedSingleObject, result)
    }

    @Test
    fun `given valid document with rented tag when parse then should build correct tag`() {
        //given
        val (searchResultDoc, expectedObject) = defaultSearchResults()
        val expectedSingleObject = expectedObject.items.filter { it.tag.equals("Rented") }

        //when
        val result = parser.parse(searchResultDoc).items.filter { it.tag.equals("Rented") }

        //then
        assertEquals(1, result.size)
        assertEquals(expectedSingleObject, result)
    }

    @Test
    fun `given valid document with empty tag when parse then should build with empty tag`() {
        //given
        val (searchResultDoc, expectedObject) = defaultSearchResults()
        val expectedSingleObject = expectedObject.items.filter { it.tag.equals("") }

        //when
        val result = parser.parse(searchResultDoc).items.filter { it.tag.equals("") }

        //then
        assertEquals(6, result.size)
        assertEquals(expectedSingleObject, result)
    }

    @Test
    fun `given valid document when parse then should build correct property url`() {
        //given
        val (searchResultDoc, _) = defaultSearchResults()

        //when
        val result = parser.parse(searchResultDoc)

        //then
        result.items.forEach { propertySearchResult ->
            assertEquals("www.aproperties.es", propertySearchResult.propertyUrl.extractAuthority())
        }
    }

    @Test
    fun `given valid document when parse then should build correct image url`() {
        //given
        val (searchResultDoc, _) = defaultSearchResults()

        //when
        val result = parser.parse(searchResultDoc)

        //then
        result.items.forEach { propertySearchResult ->
            assertEquals("www.aproperties.es", propertySearchResult.imageUrl.extractAuthority())
        }
    }

    @Test
    fun `given valid document when parse then should build correct reference`() {
        //given
        val (searchResultDoc, _) = defaultSearchResults()

        //when
        val result = parser.parse(searchResultDoc).items.map { it.reference }

        //then
        result.forEach { reference ->
            assertTrue { reference.startsWith("AV") }
        }
    }

    @Test
    fun `given valid document and document has missing data when parse then should return valid object`() {
        //given
        val (searchResultDoc, expectedObject) = singleSearchResultWithMissingData()

        //when
        val result = parser.parse(searchResultDoc)

        //then
        assertEquals(1, result.items.size)
        assertEquals(expectedObject, result)
        assertNull(result.items.first().bathCount)
        assertNull(result.items.first().surface)
        assertNull(result.items.first().dormCount)
    }

    @Test
    fun `given invalid document when parse then should throw error`() {
        //given
        val (searchResultDoc, expectedObject) = invalidSearchResult()

        //when
        val result = runCatching { parser.parse(searchResultDoc) }

        //then
        assertTrue(result.isFailure)
        assertNotNull(result.exceptionOrNull())
        assertEquals(ElementNotFoundException::class, result.exceptionOrNull()!!::class)
        assertEquals(expectedObject, result.getOrNull())
    }

    @Test
    fun `given a valid document when parse and has pagination then should return valid pagination`() {
        //given
        val (searchResultDoc, expectedObject) = searchResultsWithPagination()

        //when
        val result = parser.parse(searchResultDoc)

        //then
        assertNotNull(result.pagination)
        assertEquals(expectedObject, result.pagination)
        assertTrue(result.pagination.pagesUrl.isNotEmpty())
        assertTrue(result.pagination.totalItems > 0)
    }

    @Test
    fun `given a valid document when parse and has pagination then should build valid pages url`() {
        //given
        val (searchResultDoc, _) = defaultSearchResults()

        //when
        val result = parser.parse(searchResultDoc)

        //then
        result.pagination.pagesUrl.forEach {
            assertEquals("www.aproperties.es", it.extractAuthority())
        }
    }

    @Test
    fun `given a valid document when parse and has pagination then page url should be correct`() {
        //given
        val (searchResultDoc, _) = searchResultsWithPagination()

        //when
        val result = parser.parse(searchResultDoc)

        //then
        assertEquals(5, result.pagination.pagesUrl.size)
    }

    @Test
    fun `given a search result with one page when parse then pagination should be empty`() {
        //given
        val (searchResultDoc, _) = singleSearchResultWithMissingData()

        //when
        val result = parser.parse(searchResultDoc)

        //then
        assertNotNull(result.pagination)
        assertEquals(1, result.pagination.totalItems)
        assertEquals(emptyList(), result.pagination.pagesUrl)
    }

    @Test
    fun `given a search result with pagination when parse then pages url should not have first page`() {
        //given
        val (searchResultDoc, _) = searchResultsWithPagination()

        //when
        val result = parser.parse(searchResultDoc).pagination
            .pagesUrl.map { it.last().toString() }

        //then
        assertFalse(result.contains("1"))
    }
}