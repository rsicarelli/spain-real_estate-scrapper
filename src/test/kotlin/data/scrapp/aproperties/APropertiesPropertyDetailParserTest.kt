package data.scrapp.aproperties

import data.scrapp.aproperties.APropertiesPropertyDetailParser.Companion.vimeoRegex
import it.skrape.selects.ElementNotFoundException
import org.junit.jupiter.api.Test
import utils.extractAuthority
import utils.fixtures.badPropertyDetailMissing
import utils.fixtures.defaultPropertyDetail
import utils.fixtures.propertyDetailMissingData
import kotlin.test.*

class APropertiesPropertyDetailParserTest {

    private val parser by lazy { APropertiesPropertyDetailParser() }

    @Test
    fun `given valid html when parse then should return valid property details object`() {
        //given
        val (propertyDetailDocument, expectedObject) = defaultPropertyDetail()

        //when
        val result = parser.parse(propertyDetailDocument)

        //then
        assertNotNull(result)
        assertEquals(expectedObject, result)
    }

    @Test
    fun `given a valid html when parse then should build correct reference`() {
        //given
        val (propertyDetailDocument, expectedResult) = defaultPropertyDetail()

        //when
        val result = parser.parse(propertyDetailDocument)

        //then
        assertTrue(result.reference.startsWith("AV"))
        assertEquals(expectedResult.reference, result.reference)
    }

    @Test
    fun `given a valid html when parse then should build correct video url`() {
        //given
        val (propertyDetailDocument, expectedResult) = defaultPropertyDetail()

        //when
        val result = parser.parse(propertyDetailDocument)

        //then
        assertTrue(vimeoRegex.matches(result.videoUrl.toString()))
        assertNotNull(result.videoUrl)
        assertEquals(expectedResult.videoUrl, result.videoUrl)
    }

    @Test
    fun `given a valid html when parse then should build correct photo gallery paths`() {
        //given
        val (propertyDetailDocument, expectedResult) = defaultPropertyDetail()

        //when
        val result = parser.parse(propertyDetailDocument)

        //then
        result.photosGalleryUrls.forEach { photoUrl ->
            assertEquals("www.aproperties.es", photoUrl?.extractAuthority())
        }
        assertEquals(expectedResult.photosGalleryUrls, result.photosGalleryUrls)
    }

    @Test
    fun `given a valid html when parse then should build correct pdf url`() {
        //given
        val (propertyDetailDocument, expectedResult) = defaultPropertyDetail()

        //when
        val result = parser.parse(propertyDetailDocument)

        //then
        assertEquals("www.aproperties.es", result.pdfUrl?.extractAuthority())
        assertEquals(expectedResult.pdfUrl, result.pdfUrl)
    }

    @Test
    fun `given a property without location when parse then should build valid object`() {
        //given
        val (propertyDetailDocument, expectedResult) = propertyDetailMissingData()

        //when
        val result = parser.parse(propertyDetailDocument)

        //then
        assertNull(result.lat)
        assertNull(result.lng)
        assertEquals(expectedResult, result)
    }

    @Test
    fun `given a bad property detail when parse then should throw error`() {
        //given
        val propertyDetailDocument = badPropertyDetailMissing()

        //when
        val result = runCatching { parser.parse(propertyDetailDocument) }

        //then
        assertFalse(result.isSuccess)
        assertNotNull(result.exceptionOrNull())
        assertNull(result.getOrNull())
        assertEquals(ElementNotFoundException::class, result.exceptionOrNull()!!::class)
    }

}