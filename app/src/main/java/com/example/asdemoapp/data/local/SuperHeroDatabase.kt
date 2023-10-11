package com.example.asdemoapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.asdemoapp.data.remote.entity.SuperHeroEntity

@Database(
    entities = [SuperHeroEntity::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class SuperHeroDatabase : RoomDatabase() {
    abstract fun getSuperHeroDao(): SuperHeroDao
}