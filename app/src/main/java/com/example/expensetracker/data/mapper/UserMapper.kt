package com.example.expensetracker.data.mapper

import com.example.expensetracker.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.example.expensetracker.data.database.user.User as UserEntity

class UserMapper @Inject constructor() {
    fun toDomain(entity: UserEntity): User =
        User(
            name = entity.name,
            monthlyBudget = entity.monthlyBudget

        )
    fun toEntity(domain: User): UserEntity =
        UserEntity(
            name = domain.name,
            monthlyBudget = domain.monthlyBudget
        )
    fun toDomainFlow(flow: Flow<UserEntity?>): Flow<User?> =
        flow.map {
            it?:return@map null
            toDomain(it)
        }

}