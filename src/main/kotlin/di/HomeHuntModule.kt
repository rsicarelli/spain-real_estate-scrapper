package di

import app.AppInitializer
import app.PropertiesScrapper
import com.google.cloud.firestore.Firestore
import com.google.firebase.cloud.FirestoreClient
import data.FirestoreDataSource
import domain.MarkAsRemovedUseCase
import domain.aproperties.FetchAPropertiesUseCase
import domain.aproperties.FetchPaginatedAPropertiesUseCase
import domain.aproperties.FetchAPropertyDetailsUseCase
import domain.engel.FetchAllEngelUseCase
import domain.engel.FetchPaginatedEngelUseCase
import domain.engel.FetchSingleEngelUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import data.scrapp.Parser
import data.scrapp.Scrapper
import data.scrapp.aproperties.*
import data.scrapp.engelvoelkers.*

val appModules by lazy { listOf(homeHuntModule, dataModule, domainModule) }

val homeHuntModule = module {
    single { PropertiesScrapper(get(), get(), get()) }
    single { AppInitializer("src/main/resources/homehunt-service-account.json") }
}

val dataModule = module {
    single { FirestoreDataSource(get()) }
    single<Firestore> { FirestoreClient.getFirestore() }

    single(named(APROPERTIES_SEARCH_RESULT_PARSER_QUALIFIER)) { APropertiesSearchResultsParser() } bind Parser::class
    single(named(APROPERTIES_PAGINATION_PARSER_QUALIFIER)) { APropertiesPaginationParser() } bind Parser::class
    single(named(APROPERTIES_PROPERTY_DETAIL_PARSER_QUALIFIER)) { APropertiesPropertyDetailParser() } bind Parser::class
    single(named(APROPERTIES_SCRAPPER_QUALIFIER)) {
        APropertiesScrapper(
            searchResultParser = get(named(APROPERTIES_SEARCH_RESULT_PARSER_QUALIFIER)),
            paginationParser = get(named(APROPERTIES_PAGINATION_PARSER_QUALIFIER)),
            propertyDetailsParser = get(named(APROPERTIES_PROPERTY_DETAIL_PARSER_QUALIFIER))
        )
    } bind Scrapper::class

    single(named(ENGEL_SEARCH_RESULTS_PARSER_QUALIFIER)) { EngelSearchResultsParser() } bind Parser::class
    single(named(ENGEL_PAGINATION_PARSER_QUALIFIER)) { EngelPaginationParser() } bind Parser::class
    single(named(ENGEL_PROPERTY_DETAIL_PARSER_QUALIFIER)) { EngelPropertyDetailParser() } bind Parser::class
    single(named(ENGEL_SCRAPPER_QUALIFIER)) {
        EngelScrapper(
            propertiesParser = get(named(ENGEL_SEARCH_RESULTS_PARSER_QUALIFIER)),
            paginationParser = get(named(ENGEL_PAGINATION_PARSER_QUALIFIER)),
            propertyDetailsParser = get(named(ENGEL_PROPERTY_DETAIL_PARSER_QUALIFIER))
        )
    } bind Scrapper::class
}

val domainModule = module {
    single { FetchAPropertyDetailsUseCase(get(named(APROPERTIES_SCRAPPER_QUALIFIER))) }
    single { FetchPaginatedAPropertiesUseCase(get(named(APROPERTIES_SCRAPPER_QUALIFIER))) }
    single {
        FetchAPropertiesUseCase(
            scrapper = get(named(APROPERTIES_SCRAPPER_QUALIFIER)),
            firestoreDataSource = get(),
            fetchAPropertyDetailsUseCase = get(),
            fetchPaginatedPropertiesUseCase = get(),
            markAsRemovedUseCase = get()
        )
    }
    single { MarkAsRemovedUseCase(get()) }

    single { FetchPaginatedEngelUseCase(get(named(ENGEL_SCRAPPER_QUALIFIER))) }
    single { FetchSingleEngelUseCase(get(named(ENGEL_SCRAPPER_QUALIFIER))) }
    single {
        FetchAllEngelUseCase(
            firestoreDataSource = get(),
            paginationUseCase = get(),
            singleUseCase = get(),
            scrapper = get(named(ENGEL_SCRAPPER_QUALIFIER)),
            markAsRemovedUseCase = get()
        )
    }
}