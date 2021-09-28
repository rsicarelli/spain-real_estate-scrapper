package data.scrapp.aproperties

import data.scrapp.Parser
import data.scrapp.Scrapper
import data.scrapp.Skraper
import data.scrapp.aproperties.APropertiesScrapper.Output
import domain.valueobjects.APropertiesPagination
import domain.valueobjects.PropertyDetail
import domain.valueobjects.PropertySearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

const val APROPERTIES_SCRAPPER_QUALIFIER = "APropertiesScrapper"

class APropertiesScrapper(
    private val searchResultParser: Parser<List<PropertySearchResult>>,
    private val paginationParser: Parser<APropertiesPagination>,
    private val propertyDetailsParser: Parser<PropertyDetail>,
    private val skraper: Skraper
) : Scrapper<Output> {

    override suspend fun scrapSearchPage(url: String, getPagination: Boolean): Flow<Output> {
        return flow {
            emit(skraper.get(url) {
                val pagination = if (getPagination) paginationParser.parse(it) else null
                val results = searchResultParser.parse(it)
                    ?: throw RuntimeException("Parser returned null") //TODO: better error handling
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
            val pagination: APropertiesPagination?,
            val results: List<PropertySearchResult>
        ) : Output()

        data class SingleProperty(val propertyDetail: PropertyDetail) : Output()

    }
}

fun Output.asSearchResult() = this as Output.SearchResult
fun Output.asSingleProperty() = this as Output.SingleProperty

