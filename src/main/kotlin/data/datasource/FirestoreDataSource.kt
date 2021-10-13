package data.datasource

import com.google.api.core.ApiFuture
import com.google.cloud.firestore.*
import data.datasource.FirestoreDataSourceImpl.FirestoreMap.IS_ACTIVE
import data.datasource.FirestoreDataSourceImpl.FirestoreMap.PROPERTY_COLLECTION
import data.datasource.FirestoreDataSourceImpl.FirestoreMap.UNKNOWN_LOCATIONS_COLLECTION
import domain.model.*
import domain.model.Property.Type
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import mu.KotlinLogging


private val logger = KotlinLogging.logger("FirestoreDataSource")

interface FirestoreDataSource {
    suspend fun addAll(properties: List<Property>, type: Type): Flow<List<Property>>
    suspend fun getAllFromType(type: Type): Flow<List<Property>>
    suspend fun getAll(): Flow<List<Property>>
    suspend fun saveUnknownLocations(locations: List<Location>): Flow<Unit>
    suspend fun markAvailability(removed: List<String>, active: List<String>, type: Type): Flow<Unit>
}

class FirestoreDataSourceImpl(private val db: Firestore) : FirestoreDataSource {
    override suspend fun addAll(properties: List<Property>, type: Type): Flow<List<Property>> {
        return flow {
            val all = getAllFromType(type).first()

            logger.info { "Adding properties do Firestore ${properties.size}" }
            val batch: WriteBatch = db.batch()

            properties.forEach { property ->
                val docRef: DocumentReference =
                    db.collection(PROPERTY_COLLECTION)
                        .document(property.reference)

                var propertyToSave: Property = property
                all.find { it.reference == property.reference }?.let {
                    propertyToSave = property.copy(viewedBy = it.viewedBy, isFavourited = it.isFavourited)
                }

                batch.set(docRef, propertyToSave.toMap())
            }

            val future: ApiFuture<MutableList<WriteResult>> = batch.commit()
            for (result in future.get()) {
                logger.info { "Update time : " + result.updateTime }
            }

            emit(properties)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getAllFromType(type: Type): Flow<List<Property>> {
        return flow {
            val docRef = db.collection(PROPERTY_COLLECTION).whereEqualTo(Mapper.ORIGIN, type.tag)
            val future = docRef.get()

            val properties = future.get()
                .map { it.data.toProperty() }

            logger.info { "Got ${properties.size} properties" }

            emit(properties)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getAll(): Flow<List<Property>> {
        return flow {
            val docRef = db.collection(PROPERTY_COLLECTION)
            val future = docRef.get()

            val properties = future.get()
                .map { it.data.toProperty() }

            logger.info { "Got ${properties.size} properties" }

            emit(properties)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun saveUnknownLocations(locations: List<Location>): Flow<Unit> = flow<Unit> {
        logger.info { "Reporting unknown locations ${locations.size}" }

        val batch: WriteBatch = db.batch()

        val docRef: CollectionReference = db.collection(UNKNOWN_LOCATIONS_COLLECTION)

        locations.forEach {
            batch.set(docRef.document(it.name), it)
        }

        batch.commit()

        logger.info { "Done reporting locations ${locations.size}" }

        emit(Unit)
    }.flowOn(Dispatchers.IO)

    override suspend fun markAvailability(removed: List<String>, active: List<String>, type: Type) = flow {
        val batch: WriteBatch = db.batch()
        logger.info { "Changing property availability. Removed: ${removed.size}, active: ${active.size}" }

        removed.forEach { reference ->
            val docRef: DocumentReference = db.collection(PROPERTY_COLLECTION).document(reference)

            val data = mapOf(IS_ACTIVE to false)

            batch.update(docRef, data)
        }

        active.forEach { reference ->
            val docRef: DocumentReference = db.collection(PROPERTY_COLLECTION).document(reference)

            val data = mapOf(IS_ACTIVE to true)

            batch.update(docRef, data)
        }

        batch.commit()

        logger.info { "Done marking visibility" }

        emit(Unit)
    }.flowOn(Dispatchers.IO)

    private object FirestoreMap {
        const val PROPERTY_COLLECTION = "properties"
        const val UNKNOWN_LOCATIONS_COLLECTION = "unknownLocations"
        const val IS_ACTIVE = "isActive"
    }
}