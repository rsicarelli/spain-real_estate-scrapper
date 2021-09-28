package data.scrapp

import it.skrape.core.document
import it.skrape.fetcher.AsyncFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.Doc

class Skraper {

    suspend fun <T> get(
        url: String,
        action: (Doc) -> T
    ): T {
        return skrape(AsyncFetcher) {
            request {
                this.url = url
                userAgent = "HomeHunt Scrapper"
                timeout = 1000000 * 2 * 100
            }

            return@skrape response {
                action(this.document)
            }
        }
    }
}