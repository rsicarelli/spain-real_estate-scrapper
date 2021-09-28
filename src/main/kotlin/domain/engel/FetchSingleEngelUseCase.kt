package domain.engel

import domain.entity.Property
import domain.valueobjects.PropertySearchResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import mu.KotlinLogging
import org.apache.http.conn.HttpHostConnectException
import data.scrapp.Scrapper
import data.scrapp.engelvoelkers.EngelScrapper

private val logger = KotlinLogging.logger("FetchSingleEngelUseCase")

class FetchSingleEngelUseCase(
    private val scrapper: Scrapper<EngelScrapper.Output>
) {
    suspend operator fun invoke(searchResults: List<PropertySearchResult>): Flow<Output> {
        return flow {
            val successResultMap = mutableListOf<Property>()
            val failureResultMap = mutableListOf<PropertySearchResult>()

            searchResults.forEach { searchResult ->
                logger.info { "Fetching ${searchResult.propertyUrl}" }
                scrapper.scrapPropertyDetails(searchResult.propertyUrl)
                    .retry { cause ->
                        when (cause) {
                            is HttpHostConnectException -> {
                                delay(2000)
                                logger.info { "Retrying ${searchResult.propertyUrl}" }
                                return@retry true
                            }
                            else -> return@retry false
                        }
                    }
                    .catch { failureResultMap.add(searchResult) }
                    .map { it as EngelScrapper.Output.SingleProperty }
                    .collect { propertyDetail ->
                        successResultMap.add(Property.combine(searchResult, propertyDetail.propertyDetail))
                        logger.info { "Success" }
                    }
            }
            emit(Output(successResultMap, failureResultMap))
        }
    }

    data class Output(
        val properties: List<Property>,
        val failed: List<PropertySearchResult>
    )
}