package me.rsicarelli.domain.usecase

import domain.entity.Property
import domain.repository.PropertyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetRemoteListingsUseCase(
    val propertyRepository: PropertyRepository
) {
    suspend operator fun invoke(): Flow<List<Property>> {
        val (url, headers) = RemoteSources.alameda10dt

        return flow {
            emit(propertyRepository.getProperties(url, headers))
        }.catch {
            println(it)
        }
            .flowOn(Dispatchers.IO)
    }
}

private object RemoteSources {
    val alameda10dt: Pair<String, Map<String, String>>
        get() {
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
            return Pair(url, headers)
        }
}
