package com.example.expensetracker.data.database.expense

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.expensetracker.data.converters.Converters

@Database(entities = [Expense::class], version = 1)
@TypeConverters(Converters::class)

abstract class ExpenseDatabase: RoomDatabase(){
    abstract fun ExpenseDao(): ExpenseDao
}