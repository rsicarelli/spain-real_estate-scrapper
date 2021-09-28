package data.scrapp.aproperties

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
    fun `given valid html with tag 'nuevo' when parse then should build correct tag`() {
        //given
        val (searchResultDoc, expectedObject) = defaultSearchResults()
        val expectedSingleObject = expectedObject.filter { it.tag.equals("Nuevo") }

        //when
        val result = parser.parse(searchResultDoc).filter { it.tag.equals("Nuevo") }

        //then
        assertEquals(1, result.size)
        assertEquals(expectedSingleObject, result)
    }

    @Test
    fun `given valid html with tag 'reservada' when parse then should build correct tag`() {
        //given
        val (searchResultDoc, expectedObject) = defaultSearchResults()
        val expectedSingleObject = expectedObject.filter { it.tag.equals("Reservada") }

        //when
        val result = parser.parse(searchResultDoc).filter { it.tag.equals("Reservada") }

        //then
        assertEquals(1, result.size)
        assertEquals(expectedSingleObject, result)
    }

    @Test
    fun `given valid html with tag 'alquilada' when parse then should build correct tag`() {
        //given
        val (searchResultDoc, expectedObject) = defaultSearchResults()
        val expectedSingleObject = expectedObject.filter { it.tag.equals("Alquilada") }

        //when
        val result = parser.parse(searchResultDoc).filter { it.tag.equals("Alquilada") }

        //then
        assertEquals(4, result.size)
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
        result.forEach { propertySearchResult ->
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
        result.forEach { propertySearchResult ->
            assertEquals("www.aproperties.es", propertySearchResult.imageUrl.extractAuthority())
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
        assertEquals(1, result.size)
        assertEquals(expectedObject, result)
        assertNull(result.first().bathCount)
        assertNull(result.first().surface)
        assertNull(result.first().dormCount)
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