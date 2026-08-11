package com.example.expensetracker.domain.repository

import com.example.expensetracker.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepositry {
    fun getUser(): Flow<User?>
    suspend fun addUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
}
