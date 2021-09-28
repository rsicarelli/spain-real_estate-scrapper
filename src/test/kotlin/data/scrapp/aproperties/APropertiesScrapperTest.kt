@file:Suppress("MainFunctionReturnUnit")

package data.scrapp.aproperties

import data.scrapp.Parser
import data.scrapp.Skraper
import data.scrapp.aproperties.APropertiesScrapper.Output.SearchResult
import data.scrapp.aproperties.APropertiesScrapper.Output.SingleProperty
import domain.valueobjects.APropertiesPagination
import domain.valueobjects.PropertyDetail
import domain.valueobjects.PropertySearchResult
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import it.skrape.selects.Doc
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import utils.fixtures.Fixtures.defaultPagination
import utils.fixtures.Fixtures.defaultPropertyDetail
import utils.fixtures.Fixtures.defaultSearchResults
import utils.fixtures.Fixtures.propertyUrl
import utils.fixtures.Fixtures.searchUrl
import java.lang.RuntimeException
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@ExtendWith(MockKExtension::class)
class APropertiesScrapperTest {

    @MockK
    private lateinit var searchResultParser: Parser<List<PropertySearchResult>>

    @MockK
    private lateinit var paginationParser: Parser<APropertiesPagination>

    @MockK
    private lateinit var propertyDetailsParser: Parser<PropertyDetail>

    @MockK
    private lateinit var skraper: Skraper

    @InjectMockKs
    private lateinit var scrapper: APropertiesScrapper

    @BeforeEach
    fun setup() {
        coEvery { skraper.get<APropertiesScrapper.Output>(any(), any()) } answers {
            secondArg<(Doc) -> APropertiesScrapper.Output>().invoke(mockk())
        }
    }

    @Test
    fun `given a valid html document when scrap search results then should return correct output`() = runBlocking {
        //given
        every { paginationParser.parse(any()) } returns defaultPagination
        every { searchResultParser.parse(any()) } returns defaultSearchResults

        //when
        val result = scrapper.scrapSearchPage(searchUrl, true).first()

        //then
        assertNotNull(result)
        assertEquals(SearchResult::class, result::class)
        assertEquals(defaultPagination, result.asSearchResult().pagination)
        assertEquals(defaultSearchResults, result.asSearchResult().results)
    }

    @Test
    fun `given a valid html and html has no page when scrap search results then should return correct output`() =
        runBlocking {
            //given
            every { paginationParser.parse(any()) } answers { nothing }
            every { searchResultParser.parse(any()) } returns defaultSearchResults

            //when
            val result = scrapper.scrapSearchPage(searchUrl, true).first()

            //then
            assertNotNull(result)
            assertEquals(SearchResult::class, result::class)
            assertNull(result.asSearchResult().pagination)
            assertEquals(defaultSearchResults, result.asSearchResult().results)
        }

    @Test
    fun `given a valid html when scrap search results but without pagination search then should return correct output`() =
        runBlocking {
            //given
            every { paginationParser.parse(any()) } answers { nothing }
            every { searchResultParser.parse(any()) } returns defaultSearchResults

            //when
            val result = scrapper.scrapSearchPage(searchUrl, false).first()

            //then
            assertNotNull(result)
            assertEquals(SearchResult::class, result::class)
            assertNull(result.asSearchResult().pagination)
            assertEquals(defaultSearchResults, result.asSearchResult().results)
        }

    @Test
    fun `given a valid document when scrap search results but parse returns null then should throw error`() =
        runBlocking {
            //given
            every { paginationParser.parse(any()) } answers { nothing }
            every { searchResultParser.parse(any()) } answers { nothing }

            //when
            val result = runCatching { scrapper.scrapSearchPage(searchUrl, false).first() }

            //then
            assertFalse(result.isSuccess)
            assertNotNull(result.exceptionOrNull())
            assertEquals(RuntimeException::class, result.exceptionOrNull()!!::class)
        }

    @Test
    fun `given a valid document when scrap property details then should return valid output`() = runBlocking {
        //given
        every { propertyDetailsParser.parse(any()) } answers { defaultPropertyDetail }

        //when
        val result = scrapper.scrapPropertyDetails(propertyUrl).first()

        //then
        assertNotNull(result)
        assertEquals(result::class, SingleProperty::class)
        assertEquals(defaultPropertyDetail, result.asSingleProperty().propertyDetail)
    }

    @Test
    fun `given a valid document when scrap property details but parse returns null then should throw error`() =
        runBlocking {
            //given
            every { propertyDetailsParser.parse(any()) } answers { nothing }

            //when
            val result = runCatching { scrapper.scrapPropertyDetails(propertyUrl).first() }

            //then
            assertFalse(result.isSuccess)
            assertNotNull(result.exceptionOrNull())
            assertEquals(RuntimeException::class, result.exceptionOrNull()!!::class)
        }

}