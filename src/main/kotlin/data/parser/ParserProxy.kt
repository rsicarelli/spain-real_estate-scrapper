package data.parser

import domain.model.Property.Type
import domain.model.PropertyDetail
import domain.model.PropertySearchResult
import it.skrape.selects.Doc

interface ParserProxy {
    fun parseSearchResult(doc: Doc, type: Type): PropertySearchResult
    fun parsePropertyDetail(doc: Doc, type: Type): PropertyDetail
}

class ParserProxyImpl(
    private val aPropertiesSearchResultsParser: Parser<PropertySearchResult>,
    private val engelSearchResultsParser: Parser<PropertySearchResult>,
    private val aPropertyPropertyDetailParser: Parser<PropertyDetail>,
    private val engelPropertyDetailParser: Parser<PropertyDetail>
) : ParserProxy {


    override fun parseSearchResult(doc: Doc, type: Type): PropertySearchResult {
        return when (type) {
            Type.APROPERTIES -> aPropertiesSearchResultsParser.parse(doc)
            Type.ENGELS -> engelSearchResultsParser.parse(doc)
        }
    }

    override fun parsePropertyDetail(doc: Doc, type: Type): PropertyDetail {
        return when (type) {
            Type.APROPERTIES -> aPropertyPropertyDetailParser.parse(doc)
            Type.ENGELS -> engelPropertyDetailParser.parse(doc)
        }
    }

}