package data.datasource

import it.skrape.core.document
import it.skrape.fetcher.AsyncFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.Doc
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface WebDataSource {
    suspend fun get(
        url: String
    ): Flow<Doc>
}

class WebDataSourceImpl : WebDataSource {
    override suspend fun get(url: String): Flow<Doc> {
        return flow {
            val result = skrape(AsyncFetcher) {
                request {
                    this.url = url
                    userAgent = "HomeHunt Scrapper"
                    timeout = 1000000 * 2 * 100
                }

                return@skrape response {
                    this.document
                }
            }
            emit(result)
        }
    }
}