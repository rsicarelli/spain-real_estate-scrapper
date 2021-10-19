package me.rsicarelli.domain.repository

import domain.entity.User
import me.rsicarelli.data.repository.Repository

interface UserRepository : Repository<User> {
    fun getUserByEmail(email: String? = null): User?
}