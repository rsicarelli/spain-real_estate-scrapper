package data.parser

import domain.entity.Property
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import it.skrape.core.htmlDocument
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ParserProxyTest {

    @MockK
    private lateinit var aPropertiesSearchResultsParser: APropertiesSearchResultsParser

    @MockK
    private lateinit var engelSearchResultsParser: EngelSearchResultsParser

    @MockK
    private lateinit var aPropertyPropertyDetailParser: APropertiesPropertyDetailParser

    @MockK
    private lateinit var engelPropertyDetailParser: EngelPropertyDetailParser

    @InjectMockKs
    private lateinit var proxy: ParserProxyImpl

    @BeforeEach
    fun setup() {
        every { aPropertiesSearchResultsParser.parse(any()) } returns mockk()
        every { engelSearchResultsParser.parse(any()) } returns mockk()
        every { aPropertyPropertyDetailParser.parse(any()) } returns mockk()
        every { engelPropertyDetailParser.parse(any()) } returns mockk()
    }

    @Test
    fun `given a document when parse search result and type is 'aproperties' then should proxy to aproperties parser and not engel`() {
        //given
        val document = htmlDocument("")

        //when
        proxy.parseSearchResult(document, Property.Type.APROPERTIES)

        //then
        verify(exactly = 1) { aPropertiesSearchResultsParser.parse(any()) }
        verify(exactly = 0) { engelSearchResultsParser.parse(any()) }
    }

    @Test
    fun `given a document when parse search result and type is 'engel' then should proxy to engel parser and not aproperties`() {
        //given
        val document = htmlDocument("")

        //when
        proxy.parseSearchResult(document, Property.Type.ENGELS)

        //then
        verify(exactly = 1) { engelSearchResultsParser.parse(any()) }
        verify(exactly = 0) { aPropertiesSearchResultsParser.parse(any()) }
    }

    @Test
    fun `given a document when parse property and type is 'engel' then should proxy to engel parser and not aproperties`() {
        //given
        val document = htmlDocument("")

        //when
        proxy.parsePropertyDetail(document, Property.Type.ENGELS)

        //then
        verify(exactly = 1) { engelPropertyDetailParser.parse(any()) }
        verify(exactly = 0) { aPropertyPropertyDetailParser.parse(any()) }
    }

    @Test
    fun `given a document when parse property and type is 'aproperties' then should proxy to aproperties parser and not engel`() {
        //given
        val document = htmlDocument("")

        //when
        proxy.parsePropertyDetail(document, Property.Type.APROPERTIES)

        //then
        verify(exactly = 1) { aPropertyPropertyDetailParser.parse(any()) }
        verify(exactly = 0) { engelPropertyDetailParser.parse(any()) }
    }
}