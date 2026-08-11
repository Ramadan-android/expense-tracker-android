package com.example.expensetracker.data.DataRepositores

import com.example.expensetracker.data.database.user.UserDao
import com.example.expensetracker.data.mapper.UserMapper
import com.example.expensetracker.domain.model.User
import com.example.expensetracker.domain.repository.UserRepositry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositryImp @Inject constructor(
    private val userDao: UserDao,
    private val mapper: UserMapper
): UserRepositry {
    override fun getUser(): Flow<User?> = mapper.toDomainFlow(userDao.getUser())

    override suspend fun addUser(user: User) = userDao.insertUser(mapper.toEntity(user))

    override suspend fun updateUser(user: User) = userDao.updateUser(mapper.toEntity(user))

    override suspend fun deleteUser(user: User) = userDao.deleteUser(mapper.toEntity(user))

}