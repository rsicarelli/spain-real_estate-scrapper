package data.scrapp

import it.skrape.selects.Doc

interface Parser<T> {
    fun parse(document: Doc): T?
}