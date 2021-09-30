package domain.usecase

import domain.model.Property
import domain.model.PropertySearchResult
import domain.repository.PropertyRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertNotNull

@ExtendWith(MockKExtension::class)
class GetFirstSearchPageUseCaseTest {

    @MockK
    private lateinit var repository: PropertyRepository

    @InjectMockKs
    private lateinit var useCase: GetFirstSearchPageUseCase

    @MockK
    private lateinit var type: Property.Type

    @Test
    fun `given a request when invoke use case then should return object`(): Unit = runBlocking {
        //given
        coEvery { repository.scrapSearchPage(any(), any()) } returns flow { emit(mockk<PropertySearchResult>()) }
        val request = GetFirstSearchPageUseCase.Request("https://aurl.com", type)

        //when
        val result = useCase.invoke(request).first()

        //then
        coVerify(exactly = 1) { repository.scrapSearchPage(any(), any()) }
        assertNotNull(result)
    }

    @Test
    fun `given a request when invoke and repository throws an exception then should throw exception`(): Unit =
        runBlocking {
            //given
            coEvery { repository.scrapSearchPage(any(), any()) } throws Exception()
            val request = GetFirstSearchPageUseCase.Request("https://aurl.com", type)

            //when
            //then
            assertThrows<Exception> {
                useCase.invoke(request)
            }
        }

}