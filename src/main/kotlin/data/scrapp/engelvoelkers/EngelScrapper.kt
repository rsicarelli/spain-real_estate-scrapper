package data.scrapp.engelvoelkers

import data.scrapp.Parser
import data.scrapp.Scrapper
import data.scrapp.Skraper
import data.scrapp.engelvoelkers.EngelScrapper.Output
import domain.valueobjects.EngelPagination
import domain.valueobjects.PropertyDetail
import domain.valueobjects.PropertySearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

const val ENGEL_SCRAPPER_QUALIFIER = "EngelScrapper"

class EngelScrapper(
    private val propertiesParser: Parser<List<PropertySearchResult>>,
    private val paginationParser: Parser<EngelPagination?>,
    private val propertyDetailsParser: Parser<PropertyDetail>,
    private val skraper: Skraper
) : Scrapper<Output> {

    override suspend fun scrapSearchPage(url: String, getPagination: Boolean): Flow<Output> {
        return flow {
            emit(skraper.get(url) { result ->
                val pagination = if (getPagination) paginationParser.parse(result) else null
                val results = propertiesParser.parse(result)
                    ?: throw RuntimeException("Parser returned null value") //TODO: better error handling
                Output.SearchResult(pagination, results)
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun scrapPropertyDetails(url: String): Flow<Output> {
        return flow {
            emit(skraper.get(url) {
                val propertyDetail = propertyDetailsParser.parse(it)
                    ?: throw RuntimeException("Parser returned null") //TODO: better error handling
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

fun Output.asSearchResult() = this as Output.SearchResult
fun Output.asSingleProperty() = this as Output.SingleProperty