package domain.usecase

import domain.entity.Property
import domain.repository.PropertyRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import utils.fixtures.searchResultWithNoPagination
import utils.fixtures.searchResultsWithPagination
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class GetPaginatedSearchItemsUseCaseTest {
    @MockK
    private lateinit var repository: PropertyRepository

    @InjectMockKs
    private lateinit var useCase: GetPaginatedSearchItemsUseCase

    @MockK
    private lateinit var type: Property.Type

    @Test
    fun `given pagination is empty when invoke use case then should return original results`(): Unit =
        runBlocking {
            //given
            val searchResult = searchResultWithNoPagination
            val request = GetPaginatedSearchItemsUseCase.Request(searchResult, type)

            //when
            val result = useCase.invoke(request).first()

            //then
            coVerify(exactly = 0) { repository.scrapSearchPage(any(), any()) }
            assertEquals(searchResult.items, result)
        }

    @Test
    fun `given pagination when invoke then should return combined results`(): Unit =
        runBlocking {
            //given
            val (searchResult, expectedObject) = searchResultsWithPagination
            coEvery { repository.scrapSearchPage(any(), any()) } returns flow { emit(searchResultWithNoPagination) }
            val request = GetPaginatedSearchItemsUseCase.Request(searchResult, type)

            //when
            val result = useCase.invoke(request).first()

            //then
            coVerify(exactly = searchResult.pagination.pagesUrl.size) { repository.scrapSearchPage(any(), any()) }
            assertEquals(expectedObject, result)
        }
}