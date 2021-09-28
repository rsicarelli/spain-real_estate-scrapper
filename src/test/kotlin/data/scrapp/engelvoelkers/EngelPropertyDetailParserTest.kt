package data.scrapp.engelvoelkers

import org.junit.jupiter.api.Test
import utils.extractAuthority
import utils.fixtures.Engel.badPropertyDetailMissing
import utils.fixtures.Engel.defaultPropertyDetail
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class EngelPropertyDetailParserTest {

    private val parser by lazy { EngelPropertyDetailParser() }

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
        val result = parser.parse(propertyDetailDocument)!!

        //then
        assertTrue(result.reference.startsWith("W-"))
        assertEquals(expectedResult.reference, result.reference)
    }

    @Test
    fun `given a valid html when parse then should not build video url`() {
        //given
        val (propertyDetailDocument, expectedResult) = defaultPropertyDetail()

        //when
        val result = parser.parse(propertyDetailDocument)!!

        //then
        assertEquals("", result.videoUrl)
        assertEquals(expectedResult.videoUrl, result.videoUrl)
    }

    @Test
    fun `given a valid html when parse then should build correct photo gallery paths`() {
        //given
        val (propertyDetailDocument, expectedResult) = defaultPropertyDetail()

        //when
        val result = parser.parse(propertyDetailDocument)!!

        //then
        result.photosGalleryUrls.forEach { photoUrl ->
            assertEquals("www.engelvoelkers.com", photoUrl?.extractAuthority())
        }
        assertEquals(expectedResult.photosGalleryUrls, result.photosGalleryUrls)
    }

    @Test
    fun `given a valid html when parse then should build with empty pdf url`() {
        //given
        val (propertyDetailDocument, expectedResult) = defaultPropertyDetail()

        //when
        val result = parser.parse(propertyDetailDocument)!!

        //then
        assertEquals("", result.pdfUrl)
        assertEquals(expectedResult.pdfUrl, result.pdfUrl)
    }

    @Test
    fun `given a bad property detail when parse then should return null`() {
        //given
        val propertyDetailDocument = badPropertyDetailMissing()

        //when
        val result = parser.parse(propertyDetailDocument)

        //then
        assertNull(result)
    }

}