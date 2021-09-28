package data.scrapp

import kotlinx.coroutines.flow.Flow

interface Scrapper<T> {
    suspend fun scrapSearchPage(url: String, getPagination: Boolean): Flow<Result<T>>
    suspend fun scrapPropertyDetails(url: String): Flow<Result<T>>
}