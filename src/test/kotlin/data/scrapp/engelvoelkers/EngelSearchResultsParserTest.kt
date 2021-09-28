package data.scrapp.engelvoelkers

import org.junit.jupiter.api.Test
import utils.extractAuthority
import utils.fixtures.Engel.defaultSearchResults
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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
        val expectedSingleObject = expectedObject.filter { it.tag.equals("NEW") }

        //when
        val result = parser.parse(searchResultDoc).filter { it.tag.equals("NEW") }

        //then
        assertEquals(2, result.size)
        assertEquals(expectedSingleObject, result)
    }

    @Test
    fun `given valid html with empty tag when parse then should build with empty tag`() {
        //given
        val (searchResultDoc, expectedObject) = defaultSearchResults()
        val expectedSingleObject = expectedObject.filter { it.tag.equals("") }

        //when
        val result = parser.parse(searchResultDoc).filter { it.tag.equals("") }

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
        result.forEach { propertySearchResult ->
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
        result.forEach { propertySearchResult ->
            assertEquals("www.engelvoelkers.com", propertySearchResult.imageUrl.extractAuthority())
        }
    }

    @Test
    fun `given valid html when parse then should build correct reference`() {
        //given
        val (searchResultDoc, _) = defaultSearchResults()

        //when
        val result = parser.parse(searchResultDoc).map { it.reference }

        //then
        result.forEach { reference ->
            assertTrue { reference.startsWith("W-") }
        }
    }

}