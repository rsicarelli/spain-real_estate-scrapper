package data.parser

import it.skrape.selects.ElementNotFoundException
import org.junit.jupiter.api.Test
import utils.extractAuthority
import utils.fixtures.AProperties.defaultSearchResults
import utils.fixtures.AProperties.invalidSearchResult
import utils.fixtures.AProperties.singleSearchResultWithMissingData
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class APropertiesSearchResultsParserTest {

    private val parser by lazy { APropertiesSearchResultsParser() }

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
    fun `given valid html with new tag when parse then should build correct tag`() {
        //given
        val (searchResultDoc, expectedObject) = defaultSearchResults()
        val expectedSingleObject = expectedObject.items.filter { it.tag.equals("New") }

        //when
        val result = parser.parse(searchResultDoc).items.filter { it.tag.equals("New") }

        //then
        assertEquals(1, result.size)
        assertEquals(expectedSingleObject, result)
    }

    @Test
    fun `given valid html with reserved tag when parse then should build correct tag`() {
        //given
        val (searchResultDoc, expectedObject) = defaultSearchResults()
        val expectedSingleObject = expectedObject.items.filter { it.tag.equals("Reserved") }

        //when
        val result = parser.parse(searchResultDoc).items.filter { it.tag.equals("Reserved") }

        //then
        assertEquals(1, result.size)
        assertEquals(expectedSingleObject, result)
    }

    @Test
    fun `given valid html with rented tag when parse then should build correct tag`() {
        //given
        val (searchResultDoc, expectedObject) = defaultSearchResults()
        val expectedSingleObject = expectedObject.items.filter { it.tag.equals("Rented") }

        //when
        val result = parser.parse(searchResultDoc).items.filter { it.tag.equals("Rented") }

        //then
        assertEquals(4, result.size)
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
        assertEquals(4, result.size)
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
            assertEquals("www.aproperties.es", propertySearchResult.propertyUrl.extractAuthority())
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
            assertEquals("www.aproperties.es", propertySearchResult.imageUrl.extractAuthority())
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
            assertTrue { reference.startsWith("AV") }
        }
    }

    @Test
    fun `given valid html and html has missing data when parse then should return valid object`() {
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
    fun `given invalid html when parse then should throw error`() {
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

}