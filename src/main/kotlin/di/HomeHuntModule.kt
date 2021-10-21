package di

import data.datasource.WebDataSource
import data.datasource.WebDataSourceImpl
import data.parser.*
import data.repository.PropertyRepositoryImpl
import domain.repository.PropertyRepository
import domain.service.PropertyService
import domain.usecase.*
import me.rsicarelli.data.repository.RatingsRepositoryImpl
import me.rsicarelli.data.repository.UserRepositoryImpl
import me.rsicarelli.data.repository.ViewedPropertiesRepositoryImpl
import me.rsicarelli.domain.repository.RatingsRepository
import me.rsicarelli.domain.repository.UserRepository
import me.rsicarelli.domain.repository.ViewedPropertiesRepository
import me.rsicarelli.domain.service.AuthService
import me.rsicarelli.domain.service.RatingsService
import me.rsicarelli.domain.service.ScrapperService
import me.rsicarelli.domain.service.ViewedPropertiesService
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import org.litote.kmongo.KMongo

val appModules by lazy { listOf(homeHuntModule, dataModule, domainModule) }

val homeHuntModule = module(createdAtStart = true) {
    factory { KMongo.createClient(System.getenv("MONGO_URI") ?: "") }
}

val dataModule = module {
    single { WebDataSourceImpl() } bind WebDataSource::class
    single { PropertyRepositoryImpl(get(), get(), get()) } bind PropertyRepository::class
    single { RatingsRepositoryImpl(get()) } bind RatingsRepository::class
    single { UserRepositoryImpl(get()) } bind UserRepository::class
    single { ViewedPropertiesRepositoryImpl(get()) } bind ViewedPropertiesRepository::class

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
    single { ReportUnknownLocationsUseCase(get()) }

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

    single { PropertyService() }
    single { ScrapperService() }
    single { AuthService() }
    single { RatingsService() }
    single { ViewedPropertiesService() }
}