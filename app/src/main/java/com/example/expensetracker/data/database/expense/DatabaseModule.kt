package com.example.expensetracker.data.database.expense

import android.content.Context
import androidx.room.Room
import com.example.expensetracker.data.DataRepositores.DatabaseRepositoryImpl
import com.example.expensetracker.data.mapper.ExpenseMapper
import com.example.expensetracker.domain.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val databaseName = "MyExpenseDatabase"

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ExpenseDatabase {
        return Room.databaseBuilder(
            context,
            ExpenseDatabase::class.java,
            databaseName
        )
        .build()
    }

    @Provides
    @Singleton
    fun provideExpenseDao(db: ExpenseDatabase) = db.ExpenseDao()

    @Provides
    @Singleton
    fun provideExpenseRepository(
        dao: ExpenseDao,
        mapper: ExpenseMapper
    ): DataRepository =
        DatabaseRepositoryImpl (dao,mapper)
}


