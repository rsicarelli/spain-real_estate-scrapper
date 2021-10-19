package domain.valueobject

import domain.entity.User


data class UserInput(val email: String, val password: String)

data class UserResponse(val token: String, val user: User)