package me.rsicarelli.domain.repository

import domain.entity.User
import me.rsicarelli.data.repository.Repository

interface UserRepository : Repository<User> {
    fun getUserByUserName(userName: String? = null): User?
}