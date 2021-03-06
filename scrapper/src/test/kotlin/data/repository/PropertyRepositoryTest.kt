@file:Suppress("MainFunctionReturnUnit")


package data.repository

import data.datasource.WebDataSource
import data.parser.ParserProxy
import domain.entity.Property
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import it.skrape.selects.Doc
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class PropertyRepositoryTest {
    @MockK
    private lateinit var webDataSource: WebDataSource

    @MockK
    private lateinit var parserProxy: ParserProxy

    @InjectMockKs
    private lateinit var repository: PropertyRepositoryImpl

    @MockK
    private lateinit var type: Property.Type

    @Test
    fun `given a valid url when scrap search results then should return valid result`() =
        runBlocking {
            //given
            coEvery { webDataSource.get(any()) } returns flow { emit(mockk<Doc>()) }
            every { parserProxy.parseSearchResult(any(), any()) } returns mockk()
            val url = "https://www.url.com.br"

            //when
            val result = repository.scrapSearchPage(url, type).first()

            //then
            assertNotNull(result)
        }

    @Test
    fun `given a valid url when scrap search result then should invoke correctly`() =
        runBlocking {
            //given
            coEvery { webDataSource.get(any()) } returns flow { emit(mockk<Doc>()) }
            every { parserProxy.parseSearchResult(any(), any()) } returns mockk()
            val url = "https://www.url.com.br"

            //when
            repository.scrapSearchPage(url, type).first()

            //then
            coVerifyOrder {
                webDataSource.get(any())
                parserProxy.parseSearchResult(any(), any())
            }

            verify(exactly = 0) { parserProxy.parsePropertyDetail(any(), any()) }
        }

    @Test
    fun `given a valid url when scrap property detail then should invoke correctly`() =
        runBlocking {
            //given
            coEvery { webDataSource.get(any()) } returns flow { emit(mockk<Doc>()) }
            every { parserProxy.parsePropertyDetail(any(), any()) } returns mockk()
            val url = "https://www.url.com.br"

            //when
            repository.scrapPropertyDetails(url, type).first()

            //then
            coVerifyOrder {
                webDataSource.get(any())
                parserProxy.parsePropertyDetail(any(), any())
            }

            verify(exactly = 0) { parserProxy.parseSearchResult(any(), any()) }
        }


    @Test
    fun `given a valid url when scrap property details then should return valid result`() =
        runBlocking {
            //given
            coEvery { webDataSource.get(any()) } returns flow { emit(mockk<Doc>()) }
            every { parserProxy.parsePropertyDetail(any(), any()) } returns mockk()
            val url = "https://www.url.com.br"

            //when
            val result = repository.scrapPropertyDetails(url, type).first()

            //then
            assertNotNull(result)
        }
}
