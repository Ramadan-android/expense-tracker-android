package com.example.Usertracker.data.database.Database

import android.content.Context
import androidx.room.Room
import com.example.expensetracker.data.DataRepositores.UserRepositryImp
import com.example.expensetracker.data.database.user.UserDao
import com.example.expensetracker.data.database.user.UserDatabase
import com.example.expensetracker.data.mapper.UserMapper
import com.example.expensetracker.domain.repository.UserRepositry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val databaseName = "MyUserDatabase"

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): UserDatabase {
        return Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            databaseName
        )
        .build()
    }

    @Provides
    fun provideUserDao(db: UserDatabase) = db.UserDao()

    @Provides
    @Singleton
    fun provideUserRepository(
        dao: UserDao,
        mapper: UserMapper
    ): UserRepositry =
        UserRepositryImp (dao,mapper)
}


