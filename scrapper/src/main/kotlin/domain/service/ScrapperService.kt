package me.rsicarelli.domain.service

import app.launchPeriodicAsync
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import domain.entity.Property
import domain.usecase.ScrapWebRealEstateUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import me.rsicarelli.domain.usecase.ScrapApiRealEstateUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class, InternalCoroutinesApi::class)
class ScrapperService : KoinComponent {
    private val scrapWebRealEstate: ScrapWebRealEstateUseCase by inject()
    private val scrapApiRealEstate: ScrapApiRealEstateUseCase by inject()

    operator fun invoke() {
        CoroutineScope(Dispatchers.Default).launchPeriodicAsync(Duration.hours(6)) {
            scrapWebRealEstate.invoke(
                ScrapWebRealEstateUseCase.Request(
                    APROPERTIES_DEFAULT_URL,
                    Property.Type.APROPERTIES
                )
            ).collect()
            scrapWebRealEstate.invoke(
                ScrapWebRealEstateUseCase.Request(
                    ENGEL_DEFAULT_URL,
                    Property.Type.ENGELS
                )
            ).collect()
            scrapApiRealEstate.invoke(
                request = ScrapApiRealEstateUseCase.Request(
                    alameda10dt.first,
                    alameda10dt.second,
                    alameda10dt.third,
                    Property.Type.ALAMEDA10
                )
            )
        }
    }

    companion object {
        private const val APROPERTIES_DEFAULT_URL =
            "https://www.aproperties.es/en/search?searchbar=1&mod=rental&zone=3&area=7&group=&loc=42&dis=&p=1"

        private const val ENGEL_DEFAULT_URL =
            "https://www.engelvoelkers.com/en/search/?q=&startIndex=0&businessArea=residential&sortOrder=DESC&sortField=sortPrice&pageSize=238&facets=bsnssr%3Aresidential%3Bcntry%3Aspain%3Brgn%3Avalencia%3Btyp%3Arent%3B"

        private val alameda10dt: Triple<String, Map<String, String>, JsonObject>
            get() {
                val bodyRaw =
                    "{\"currentPage\":0,\"itemsPerPage\":20,\"order\":\"desc\",\"orderfield\":\"creationDate\",\"ids\":[],\"UserContactId\":null,\"showAddress\":1,\"adOperationId\":\"2\",\"adScopeId\":null,\"adTypologyId\":\"0\",\"priceMin\":null,\"priceMax\":null,\"CreationDateMin\":null,\"CreationDateMax\":null,\"locationId\":[],\"drawShapePath\":null,\"homes\":null,\"chalets\":null,\"countryhouses\":null,\"isDuplex\":null,\"isPenthouse\":null,\"isStudio\":null,\"isIndependentHouse\":null,\"isSemidetachedHouse\":null,\"isTerracedHouse\":null,\"constructedAreaMin\":null,\"constructedAreaMax\":null,\"rooms_0\":null,\"rooms_1\":null,\"rooms_2\":null,\"rooms_3\":null,\"rooms_4\":null,\"baths_1\":null,\"baths_2\":null,\"baths_3\":null,\"builtTypeId\":null,\"isTopFloor\":null,\"isIntermediateFloor\":null,\"isGroundFloor\":null,\"isFirstFloor\":null,\"hasAirConditioning\":null,\"hasWardrobe\":null,\"hasGarage\":null,\"hasLift\":null,\"hasTerrace\":null,\"hasBoxRoom\":null,\"hasSwimmingPool\":null,\"hasGarden\":null,\"flatLocationId\":null,\"hasKitchen\":null,\"hasAutomaticDoor\":null,\"hasPersonalSecurity\":null,\"HasSecurity24h\":null,\"garageCapacityId\":null,\"hasHotWater\":null,\"hasExterior\":null,\"hasSuspendedFloor\":null,\"hasHeating\":null,\"isFurnish\":null,\"isBankOwned\":null,\"distributionId\":null,\"isOnlyOfficeBuilding\":null,\"ubicationId\":null,\"warehouseType_1\":null,\"warehouseType_2\":null,\"isATransfer\":null,\"isCornerLocated\":null,\"hasSmokeExtractor\":null,\"landType_1\":null,\"landType_2\":null,\"landType_3\":null,\"HasAllDayAccess\":null,\"HasLoadingDockAccess\":null,\"HasTenant\":null,\"addressVisible\":null,\"mlsIncluded\":null,\"freeText\":null,\"RefereceText\":null,\"isLowered\":null,\"priceDropDateFrom\":0,\"priceDropDateTo\":0,\"arePetsAllowed\":null,\"Equipment\":null,\"OperationStatus\":null,\"AdContract\":null,\"IsRent\":true,\"IsSale\":false,\"IsAuction\":false,\"AdState\":null}"

                val url = "http://www.alameda10dt.es/api/AdsSearch/PostMiniFichasAdsMaps"
                val headers = mutableMapOf<String, String>().apply {
                    set("Connection", "keep-alive")
                    set("Accept", "*/*")
                    set("Dnt", "1")
                    set("X-Requested-With", "XMLHttpRequest")
                    set(
                        "User-Agent",
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Safari/537.36"
                    )
                    set("Content-Type", "application/json; charset=UTF-8")
                    set("Origin", "http://www.alameda10dt.es")
                    set("Referer", "http://www.alameda10dt.es/ads/alquiler/inmueble")
                    set("Accept-Language", "en-US,en;q=0.9,pt-BR;q=0.8,pt;q=0.7,es-ES;q=0.6,es;q=0.5")
                    set(
                        "Cookie",
                        "isAdsOffice=; search_buscador_pro=%7b%22currentPage%22%3a0%2c%22itemsPerPage%22%3a20%2c%22order%22%3a%22desc%22%2c%22orderfield%22%3a%22creationDate%22%2c%22ids%22%3a%5b%5d%2c%22UserContactId%22%3anull%2c%22showAddress%22%3a1%2c%22adOperationId%22%3a%222%22%2c%22adScopeId%22%3anull%2c%22adTypologyId%22%3a%220%22%2c%22priceMin%22%3anull%2c%22priceMax%22%3anull%2c%22CreationDateMin%22%3anull%2c%22CreationDateMax%22%3anull%2c%22locationId%22%3a%5b%5d%2c%22drawShapePath%22%3anull%2c%22homes%22%3anull%2c%22chalets%22%3anull%2c%22countryhouses%22%3anull%2c%22isDuplex%22%3anull%2c%22isPenthouse%22%3anull%2c%22isStudio%22%3anull%2c%22isIndependentHouse%22%3anull%2c%22isSemidetachedHouse%22%3anull%2c%22isTerracedHouse%22%3anull%2c%22constructedAreaMin%22%3anull%2c%22constructedAreaMax%22%3anull%2c%22rooms_0%22%3anull%2c%22rooms_1%22%3anull%2c%22rooms_2%22%3anull%2c%22rooms_3%22%3anull%2c%22rooms_4%22%3anull%2c%22baths_1%22%3anull%2c%22baths_2%22%3anull%2c%22baths_3%22%3anull%2c%22builtTypeId%22%3anull%2c%22isTopFloor%22%3anull%2c%22isIntermediateFloor%22%3anull%2c%22isGroundFloor%22%3anull%2c%22isFirstFloor%22%3anull%2c%22hasAirConditioning%22%3anull%2c%22hasWardrobe%22%3anull%2c%22hasGarage%22%3anull%2c%22hasLift%22%3anull%2c%22hasTerrace%22%3anull%2c%22hasBoxRoom%22%3anull%2c%22hasSwimmingPool%22%3anull%2c%22hasGarden%22%3anull%2c%22flatLocationId%22%3anull%2c%22hasKitchen%22%3anull%2c%22hasAutomaticDoor%22%3anull%2c%22hasPersonalSecurity%22%3anull%2c%22HasSecurity24h%22%3anull%2c%22garageCapacityId%22%3anull%2c%22hasHotWater%22%3anull%2c%22hasExterior%22%3anull%2c%22hasSuspendedFloor%22%3anull%2c%22hasHeating%22%3anull%2c%22isFurnish%22%3anull%2c%22isBankOwned%22%3anull%2c%22distributionId%22%3anull%2c%22isOnlyOfficeBuilding%22%3anull%2c%22ubicationId%22%3anull%2c%22warehouseType_1%22%3anull%2c%22warehouseType_2%22%3anull%2c%22isATransfer%22%3anull%2c%22isCornerLocated%22%3anull%2c%22hasSmokeExtractor%22%3anull%2c%22landType_1%22%3anull%2c%22landType_2%22%3anull%2c%22landType_3%22%3anull%2c%22HasAllDayAccess%22%3anull%2c%22HasLoadingDockAccess%22%3anull%2c%22HasTenant%22%3anull%2c%22addressVisible%22%3anull%2c%22mlsIncluded%22%3anull%2c%22freeText%22%3anull%2c%22RefereceText%22%3anull%2c%22isLowered%22%3anull%2c%22priceDropDateFrom%22%3a0%2c%22priceDropDateTo%22%3a0%2c%22arePetsAllowed%22%3anull%2c%22Equipment%22%3anull%2c%22OperationStatus%22%3anull%2c%22AdContract%22%3anull%2c%22IsRent%22%3atrue%2c%22IsSale%22%3afalse%2c%22IsAuction%22%3afalse%2c%22AdState%22%3anull%7d; selected_buscador_pro=96535185%2c96534735%2c96534515%2c96534148%2c96516038%2c96519776%2c96511243%2c38224441%2c96481554%2c96481457%2c96480923%2c96480857%2c96480577%2c96480319%2c96479999%2c96479884%2c96479669%2c96479603%2c96479360%2c96479270; search_buscador_offices=null; __atuvc=1%7C4; __atuvs=61f1a99f35c948a5000; URLListadoFriendly=http%3A%2F%2Fwww.alameda10dt.es%2Fads%2Falquiler%2Finmueble%23; isMap="
                    )
                }
                return Triple(url, headers, JsonParser.parseString(bodyRaw).asJsonObject)
            }
    }
}