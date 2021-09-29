package domain.usecases

import domain.entity.Property
import domain.entity.Property.Type
import domain.repositories.PropertyRepository
import domain.valueobjects.PropertyItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import mu.KotlinLogging
import org.apache.http.conn.HttpHostConnectException
import java.time.Duration

private val logger = KotlinLogging.logger("GetPropertiesUseCase")

class GetPropertyUseCase(
    private val propertyRepository: PropertyRepository
) {
    suspend operator fun invoke(request: Request): Flow<List<Property>> {
        return flow {
            val (propertyItems, type) = request
            val properties = mutableListOf<Property>()
            val failures = mutableListOf<PropertyItem>()

            propertyItems.forEach { propertyItem ->
                logger.info { "Fetching ${propertyItem.propertyUrl}" }
                propertyRepository.scrapPropertyDetails(propertyItem.propertyUrl, type)
                    .retry { cause ->
                        when (cause) {
                            is HttpHostConnectException -> {
                                delay(Duration.ofSeconds(2).toMillis())
                                logger.info { "Retrying ${propertyItem.propertyUrl}" }
                                return@retry true
                            }
                            else -> return@retry false
                        }
                    }
                    .catch { failures.add(propertyItem) }
                    .collect { propertyDetail ->
                        properties.add(Property.combine(propertyItem, propertyDetail))
                    }

                logger.info { "Done. Succeeds: ${properties.size}, failures: ${failures.size}" }
            }

            emit(properties)
        }
    }

    data class Request(
        val propertyItems: List<PropertyItem>,
        val type: Type
    )
}