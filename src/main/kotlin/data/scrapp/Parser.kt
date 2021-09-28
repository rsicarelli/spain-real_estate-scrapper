package data.scrapp

import it.skrape.fetcher.Result

interface Parser<T> {
    fun parse(result: Result): T
}