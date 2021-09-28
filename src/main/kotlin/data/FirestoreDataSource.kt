package data

import com.google.api.core.ApiFuture
import com.google.cloud.firestore.DocumentReference
import com.google.cloud.firestore.Firestore
import com.google.cloud.firestore.WriteBatch
import com.google.cloud.firestore.WriteResult
import com.google.firebase.cloud.FirestoreClient
import data.FirestoreDataSource.FirestoreMap.IS_ACTIVE
import data.FirestoreDataSource.FirestoreMap.LISTINGS_DOC
import data.FirestoreDataSource.FirestoreMap.PROPERTY_COLLECTION
import domain.entity.Property
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import mu.KotlinLogging


private val logger = KotlinLogging.logger("FirestoreDataSource")

class FirestoreDataSource(private val db: Firestore) {
    fun addAll(properties: List<Property>, type: Type): Flow<List<Property>> {
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

            val future: ApiFuture<MutableList<WriteResult>> = batch.commit()
            for (result in future.get()) {
                logger.info { "Update time : " + result.updateTime }
            }
            emit(properties)
        }.flowOn(Dispatchers.IO)
    }

    fun getAll(type: Type): Flow<List<Property>> {
        return flow {
            val docRef = db.collection(PROPERTY_COLLECTION)
                .document(LISTINGS_DOC)
                .collection(type.tag)
            val future = docRef.get()

            emit(future.get()
                .map { Property.fromMap(it.data) }
                .also { logger.info { "Got ${it.size} properties" } })
        }.flowOn(Dispatchers.IO)
    }

    fun markAvailability(removed: List<String>, active: List<String>, type: Type) = flow {
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

        emit(Unit)
            .also { logger.info { "Done" } }

    }.flowOn(Dispatchers.IO)

    object FirestoreMap {
        const val PROPERTY_COLLECTION = "properties"
        const val LISTINGS_DOC = "listings"
        const val IS_ACTIVE = "isActive"
    }

    sealed class Type(val tag: String) {
        object APROPERTIES : Type("aProperties")
        object ENGELS : Type("engels")
    }
}