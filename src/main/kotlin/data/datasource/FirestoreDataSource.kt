package data.datasource

import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.WriteBatch
import data.datasource.FirestoreDataSourceImpl.FirestoreMap.IS_ACTIVE
import data.datasource.FirestoreDataSourceImpl.FirestoreMap.LISTINGS_DOC
import data.datasource.FirestoreDataSourceImpl.FirestoreMap.PROPERTY_COLLECTION
import domain.model.Property
import domain.model.Property.Type
import domain.model.toMap
import domain.model.toProperty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import mu.KotlinLogging


private val logger = KotlinLogging.logger("FirestoreDataSource")

interface FirestoreDataSource {
    suspend fun addAll(properties: List<Property>, type: Type): Flow<List<Property>>
    suspend fun getAll(type: Type): Flow<List<Property>>
    suspend fun markAvailability(removed: List<String>, active: List<String>, type: Type): Flow<Unit>
}

class FirestoreDataSourceImpl(private val db: Firestore) : FirestoreDataSource {
    override suspend fun addAll(properties: List<Property>, type: Type): Flow<List<Property>> {
        return flow {
            logger.info { "Adding properties do Firestore ${properties.size}" }
            val batch: WriteBatch = db.batch()

            properties.forEach { property ->
                val docRef: DocumentReference =
                    db.collection(PROPERTY_COLLECTION)
                        .document(LISTINGS_DOC)
                        .collection(type.tag)
                        .document(property.reference)
                val data = property.toMap()

                batch.set(docRef, data)
            }

            batch.commit()

            emit(properties)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getAll(type: Type): Flow<List<Property>> {
        return flow {
            val docRef = db.collection(PROPERTY_COLLECTION)
                .document(LISTINGS_DOC)
                .collection(type.tag)
            val future = docRef.get()

            val properties = future.get()
                .map { it.data.toProperty() }

            logger.info { "Got ${properties.size} properties" }

            emit(properties)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun markAvailability(removed: List<String>, active: List<String>, type: Type) = flow {
        val batch: WriteBatch = db.batch()
        logger.info { "Changing property availability. Removed: ${removed.size}, active: ${active.size}" }

        removed.forEach { reference ->
            val docRef: DocumentReference = db.collection(PROPERTY_COLLECTION)
                .document(LISTINGS_DOC)
                .collection(type.tag)
                .document(reference)

            val data = mapOf(IS_ACTIVE to false)

            batch.update(docRef, data)
        }

        active.forEach { reference ->
            val docRef: DocumentReference = db.collection(PROPERTY_COLLECTION)
                .document(LISTINGS_DOC)
                .collection(type.tag)
                .document(reference)

            val data = mapOf(IS_ACTIVE to true)

            batch.update(docRef, data)
        }

        batch.commit()

        logger.info { "Done marking visibility" }

        emit(Unit)
    }.flowOn(Dispatchers.IO)

    private object FirestoreMap {
        const val PROPERTY_COLLECTION = "properties"
        const val LISTINGS_DOC = "listings"
        const val IS_ACTIVE = "isActive"
    }
}