package com.peter.pezesha.data.db.appdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.peter.pezesha.data.db.dao.ProductDao
import com.peter.pezesha.data.db.entities.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
