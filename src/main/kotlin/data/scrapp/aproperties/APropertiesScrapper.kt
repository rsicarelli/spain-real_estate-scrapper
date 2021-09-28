package data.scrapp.aproperties

import domain.valueobjects.APropertiesPagination
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

const val APROPERTIES_SCRAPPER_QUALIFIER = "APropertiesScrapper"

class APropertiesScrapper(
    private val searchResultParser: Parser<List<PropertySearchResult>>,
    private val paginationParser: Parser<APropertiesPagination>,
    private val propertyDetailsParser: Parser<PropertyDetail>
) : Scrapper<APropertiesScrapper.Output> {

    override suspend fun scrapSearchPage(url: String, getPagination: Boolean): Flow<Result<Output>> {
        return flow {
            emit(scrapper(url) {
                val pagination = if (getPagination) paginationParser.parse(it) else null
                val results = searchResultParser.parse(it)
                Output.SearchResult(pagination, results)
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun scrapPropertyDetails(url: String): Flow<Result<Output>> {
        return flow {
            emit(scrapper(url) {
                Output.SingleProperty(propertyDetailsParser.parse(it))
            })
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun scrapper(
        url: String,
        action: (it.skrape.fetcher.Result) -> Output
    ): Result<Output> {
        return skrape(AsyncFetcher) {
            request {
                this.url = url
                userAgent = "HomeHunt Scrapper"
                timeout = 1000000 * 2 * 100
            }

            return@skrape response {
                runCatching { action(this) }
            }
        }
    }

    sealed class Output {
        data class SearchResult(
            val pagination: APropertiesPagination?,
            val results: List<PropertySearchResult>
        ) : Output()

        data class SingleProperty(val propertyDetail: PropertyDetail) : Output()
    }

}

