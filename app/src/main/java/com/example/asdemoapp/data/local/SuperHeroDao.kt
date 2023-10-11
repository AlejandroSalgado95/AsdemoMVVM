package com.example.asdemoapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.asdemoapp.data.remote.entity.SuperHeroEntity

@Dao
interface SuperHeroDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSuperHeroToDb(superHeroEntity: SuperHeroEntity)

    /*
    @Query("SELECT * FROM super_hero WHERE id LIKE :id || '%' ")
    fun observeSuperHeroListByIdFromDb(id: String) : LiveData<List<SuperHeroEntity>>
     */

    @Query("SELECT * FROM super_hero")
    fun observeAllSuperHeroesFromDb(): LiveData<List<SuperHeroEntity>>

}