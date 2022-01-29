package me.rsicarelli.data.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import domain.entity.User
import domain.valueobject.UserInput
import me.rsicarelli.domain.service.AuthService

fun SchemaBuilder.authSchema(authService: AuthService) {
    mutation("signIn") {
        description = "Authenticate an existing user"
        resolver { userInput: UserInput ->
            try {
                authService.signIn(userInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("signUp") {
        description = "Authenticate a new user"
        resolver { userInput: UserInput ->
            try {
                authService.signUp(userInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    type<User> {
        User::hashedPass.ignore()
    }
}