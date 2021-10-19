package me.rsicarelli.domain.service

import at.favre.lib.crypto.bcrypt.BCrypt
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.mongodb.client.MongoClient
import domain.entity.User
import domain.valueobject.UserInput
import domain.valueobject.UserResponse
import io.ktor.application.*
import me.rsicarelli.data.repository.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.nio.charset.StandardCharsets
import java.util.*

class AuthService : KoinComponent {
    private val client: MongoClient by inject()
    private val repo: UserRepository = UserRepository(client)
    private val secret: String = "secret"
    private val algorithm: Algorithm = Algorithm.HMAC256(secret)
    private val verifier: JWTVerifier = JWT.require(algorithm).build()

    fun signIn(userInput: UserInput): UserResponse {
        val user = repo.getUserByEmail(userInput.email) ?: error("No such user by that email")
        // hash incoming password and compare it to saved
        if (!BCrypt.verifyer()
                .verify(
                    userInput.password.toByteArray(Charsets.UTF_8),
                    user.hashedPass
                ).verified
        ) {
            error("Password incorrect")
        }

        val token = signAccessToken(user._id)
        return UserResponse(token, user)
    }

    fun signUp(userInput: UserInput): UserResponse {
        val hashedPassword = BCrypt.withDefaults().hash(10, userInput.password.toByteArray(StandardCharsets.UTF_8))
        val id = UUID.randomUUID().toString()
        val emailUser = repo.getUserByEmail(userInput.email)
        if (emailUser != null) {
            error("Email already in use")
        }
        val newUser = repo.add(
            User(
                _id = id,
                email = userInput.email,
                hashedPass = hashedPassword,
            )
        )
        val token = signAccessToken(newUser._id)
        return UserResponse(token, newUser)
    }

    private fun signAccessToken(id: String): String {
        return JWT.create()
            .withIssuer(id)
            .withClaim("userId", id)
            .sign(algorithm)
    }

    fun verifyToken(call: ApplicationCall): User? {
        return try {
            val authHeader = call.request.headers["Authorization"] ?: ""
            val token = authHeader.split("Bearer ").last()
            val accessToken = verifier.verify(JWT.decode(token))
            val userId = accessToken.getClaim("userId").asString()
            return User(_id = userId, email = "", hashedPass = ByteArray(0))
        } catch (e: Exception) {
            null
        }
    }

}