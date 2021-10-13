package di

import app.AppInitializer
import domain.service.RentalPropertiesService
import com.google.firebase.cloud.FirestoreClient
import data.datasource.FirestoreDataSource
import data.datasource.FirestoreDataSourceImpl
import data.datasource.WebDataSource
import data.datasource.WebDataSourceImpl
import data.parser.*
import data.repository.PropertyRepositoryImpl
import domain.repository.PropertyRepository
import domain.usecase.*
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val appModules by lazy { listOf(homeHuntModule, dataModule, domainModule) }

val homeHuntModule = module {
    single { RentalPropertiesService(get()) }
    single { AppInitializer("src/main/resources/homehunt-service-account.json") }
}

val dataModule = module {
    single { FirestoreClient.getFirestore() }
    single { FirestoreDataSourceImpl(get()) } bind FirestoreDataSource::class
    single { WebDataSourceImpl() } bind WebDataSource::class
    single { PropertyRepositoryImpl(get(), get(), get()) } bind PropertyRepository::class

    single(named(APROPERTIES_SEARCH_RESULT_PARSER_QUALIFIER)) { APropertiesSearchResultsParser() } bind Parser::class
    single(named(APROPERTIES_PROPERTY_DETAIL_PARSER_QUALIFIER)) { APropertiesPropertyDetailParser() } bind Parser::class
    single(named(ENGEL_SEARCH_RESULTS_PARSER_QUALIFIER)) { EngelSearchResultsParser() } bind Parser::class
    single(named(ENGEL_PROPERTY_DETAIL_PARSER_QUALIFIER)) { EngelPropertyDetailParser() } bind Parser::class
    single<ParserProxy> {
        ParserProxyImpl(
            aPropertiesSearchResultsParser = get(named(APROPERTIES_SEARCH_RESULT_PARSER_QUALIFIER)),
            engelSearchResultsParser = get(named(ENGEL_SEARCH_RESULTS_PARSER_QUALIFIER)),
            aPropertyPropertyDetailParser = get(named(APROPERTIES_PROPERTY_DETAIL_PARSER_QUALIFIER)),
            engelPropertyDetailParser = get(named(ENGEL_PROPERTY_DETAIL_PARSER_QUALIFIER)),
        )
    }
}

val domainModule = module {
    single { GetPropertyUseCase(get()) }
    single { GetPaginatedSearchItemsUseCase(get()) }
    single { TogglePropertyAvailabilityUseCase(get()) }
    single { GetFirstSearchPageUseCase(get()) }
    single { SavePropertiesUseCase(get()) }
    single { FixPropertiesLocationUseCase() }

    single {
        ScrapRealEstateUseCase(
            getFirstResults = get(),
            getPaginatedSearchItems = get(),
            getProperties = get(),
            saveProperties = get(),
            toggleAvailability = get(),
            fixPropertiesLocation = get()
        )
    }
}