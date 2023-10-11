package com.example.asdemoapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.asdemoapp.domain.model.SuperHeroModel
import com.example.asdemoapp.utils.Resource

interface SuperHeroRepository {
    suspend fun getSuperHero(superHeroId: String): Resource<SuperHeroModel>
    fun observeAllSuperHeroesFromDb(): LiveData<List<SuperHeroModel>>
}