package data.scrapp.engelvoelkers

import domain.valueobjects.EngelPagination
import domain.valueobjects.PropertyDetail
import domain.valueobjects.PropertySearchResult
import it.skrape.fetcher.AsyncFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import data.scrapp.Parser
import data.scrapp.Scrapper
import data.scrapp.Skraper
import it.skrape.core.document

const val ENGEL_SCRAPPER_QUALIFIER = "EngelScrapper"

class EngelScrapper(
    private val propertiesParser: Parser<List<PropertySearchResult>>,
    private val paginationParser: Parser<EngelPagination>,
    private val propertyDetailsParser: Parser<PropertyDetail>,
    private val skraper: Skraper
) : Scrapper<EngelScrapper.Output> {

    override suspend fun scrapSearchPage(url: String, getPagination: Boolean): Flow<Output> {
        return flow {
            emit(skraper.get(url) { result ->
                val pagination = if (getPagination) paginationParser.parse(result) else null
                val results =
                    propertiesParser.parse(result)?.filter { it.reference.isNotEmpty() }
                        ?: throw RuntimeException("Parser returned null value") //TODO: better error handling
                Output.SearchResult(pagination, results)
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun scrapPropertyDetails(url: String): Flow<Output> {
        return flow {
            emit(skraper.get(url) {
                val propertyDetail = propertyDetailsParser.parse(it) ?: throw RuntimeException("Parser returned null") //TODO: better error handling
                Output.SingleProperty(propertyDetail)
            })
        }.flowOn(Dispatchers.IO)
    }

    sealed class Output {
        data class SearchResult(
            val pagination: EngelPagination?,
            val results: List<PropertySearchResult>
        ) : Output()

        data class SingleProperty(val propertyDetail: PropertyDetail) : Output()
    }


}