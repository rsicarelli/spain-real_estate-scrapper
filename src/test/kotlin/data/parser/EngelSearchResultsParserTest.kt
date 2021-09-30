package data.parser

import it.skrape.selects.ElementNotFoundException
import org.junit.jupiter.api.Test
import utils.extractAuthority
import utils.fixtures.Engel.defaultSearchResults
import utils.fixtures.Engel.defaultSearchResultsWithPagination
import utils.fixtures.Engel.emptySearchResult
import utils.fixtures.Engel.singleSearchResult
import kotlin.test.*

class EngelSearchResultsParserTest {

    private val parser by lazy { EngelSearchResultsParser() }

    @Test
    fun `given valid html when parse then should return valid object`() {
        //given
        val (searchResultDoc, expectedObject) = defaultSearchResults()

        //when
        val result = parser.parse(searchResultDoc)

        //then
        assertEquals(expectedObject, result)
    }

    @Test
    fun `given valid html with tag 'new' when parse then should build correct tag`() {
        //given
        val (searchResultDoc, expectedObject) = defaultSearchResults()
        val expectedSingleObject = expectedObject.items.filter { it.tag.equals("NEW") }

        //when
        val result = parser.parse(searchResultDoc).items.filter { it.tag.equals("NEW") }

        //then
        assertEquals(2, result.size)
        assertEquals(expectedSingleObject, result)
    }

    @Test
    fun `given valid html with empty tag when parse then should build with empty tag`() {
        //given
        val (searchResultDoc, expectedObject) = defaultSearchResults()
        val expectedSingleObject = expectedObject.items.filter { it.tag.equals("") }

        //when
        val result = parser.parse(searchResultDoc).items.filter { it.tag.equals("") }

        //then
        assertEquals(14, result.size)
        assertEquals(expectedSingleObject, result)
    }

    @Test
    fun `given valid html when parse then should build correct property url`() {
        //given
        val (searchResultDoc, _) = defaultSearchResults()

        //when
        val result = parser.parse(searchResultDoc)

        //then
        result.items.forEach { propertySearchResult ->
            assertEquals("www.engelvoelkers.com", propertySearchResult.propertyUrl.extractAuthority())
        }
    }

    @Test
    fun `given valid html when parse then should build correct image url`() {
        //given
        val (searchResultDoc, _) = defaultSearchResults()

        //when
        val result = parser.parse(searchResultDoc)

        //then
        result.items.forEach { propertySearchResult ->
            assertEquals("www.engelvoelkers.com", propertySearchResult.imageUrl.extractAuthority())
        }
    }

    @Test
    fun `given valid html when parse then should build correct reference`() {
        //given
        val (searchResultDoc, _) = defaultSearchResults()

        //when
        val result = parser.parse(searchResultDoc).items.map { it.reference }

        //then
        result.forEach { reference ->
            assertTrue { reference.startsWith("W-") }
        }
    }

    @Test
    fun `given valid html when parse then should return valid pagination`() {
        //given
        val (searchResultDocument, expectedPagination) = defaultSearchResultsWithPagination()

        //when
        val result = parser.parse(searchResultDocument)

        //then
        assertNotNull(result)
        assertEquals(expectedPagination, result)
        assertEquals(234, result.pagination.totalItems)
    }

    @Test
    fun `given empty search result when parse then should throw error`() {
        //given
        val searchResultDocument = emptySearchResult()

        //when
        val result = runCatching { parser.parse(searchResultDocument) }

        //then
        assertFalse(result.isSuccess)
        assertNotNull(result.exceptionOrNull())
        assertEquals(ElementNotFoundException::class, result.exceptionOrNull()!!::class)
        assertNull(result.getOrNull())
    }

    @Test
    fun `given single search result when parse then should return single property item`() {
        //given
        val (searchResultDocument, expectedObject) = singleSearchResult()

        //when
        val result = parser.parse(searchResultDocument)

        //then
        assertEquals(1, result.items.size)
        assertEquals(expectedObject, result)
    }

    @Test
    fun `given single search result when parse then should return empty pagination`() {
        //given
        val (searchResultDocument, _) = singleSearchResult()

        //when
        val result = parser.parse(searchResultDocument)

        //then
        assertEquals(emptyList(), result.pagination.pagesUrl)
        assertEquals(1, result.pagination.totalItems)
    }

    @Test
    fun `given search result when parse then page urls should have correct index`() {
        //given
        val (searchResultDocument, _) = defaultSearchResults()

        //when
        val result = parser.parse(searchResultDocument)
            .pagination.pagesUrl.map {
                it.replaceBefore("startIndex", "")
                    .replaceAfter("&", "")
                    .replace("&", "")
                    .replace("startIndex=", "")
            }.toList()

        //then
        assertEquals(result[0], "16")
        assertEquals(result[1], "34")
        assertEquals(result[2], "52")
        assertEquals(result[3], "70")
        assertEquals(result[4], "88")
        assertEquals(result[5], "106")
        assertEquals(result[6], "124")
        assertEquals(result[7], "142")
        assertEquals(result[8], "160")
        assertEquals(result[9], "178")
        assertEquals(result[10], "196")
        assertEquals(result[11], "214")
        assertEquals(result[12], "232")
    }

    @Test
    fun `given search result with pagination when parse then should pagination should have correct authority`() {
        //given
        val (searchResultDoc, _) = defaultSearchResults()

        //when
        val result = parser.parse(searchResultDoc)

        //then
        result.pagination.pagesUrl.forEach { pageUrl ->
            assertEquals("www.engelvoelkers.com", pageUrl.extractAuthority())
        }
    }
}