package app

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import mu.KotlinLogging
import java.io.FileInputStream
import java.io.InputStream

private val logger = KotlinLogging.logger("AppInitializer")

class AppInitializer(
    private val firebaseServiceAccountPath: String
) {
    operator fun invoke() {
        val serviceAccount: InputStream = FileInputStream(firebaseServiceAccountPath)
        val credentials = GoogleCredentials.fromStream(serviceAccount)
        val options = FirebaseOptions.builder()
            .setCredentials(credentials)
            .build()
        FirebaseApp.initializeApp(options)
        logger.info { "✅ FirebaseApp initialized" }
    }
}