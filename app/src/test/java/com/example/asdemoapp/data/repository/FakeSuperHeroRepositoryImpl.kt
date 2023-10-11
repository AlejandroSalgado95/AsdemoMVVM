package com.example.asdemoapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.asdemoapp.data.mapper.*
import com.example.asdemoapp.data.remote.entity.SuperHeroEntity
import com.example.asdemoapp.domain.model.ApiCategoryModel
import com.example.asdemoapp.domain.model.SuperHeroModel
import com.example.asdemoapp.domain.repository.PublicApisRepository
import com.example.asdemoapp.domain.repository.SuperHeroRepository
import com.example.asdemoapp.utils.ErrorType
import com.example.asdemoapp.utils.Resource

class FakeSuperHeroRepositoryImpl() : SuperHeroRepository {

    private val superHeroMapper = SuperHeroMapper(
        PowerStatsMapper(),
        AppearanceMapper(),
        BiographyMapper(),
        WorkMapper(),
        ConnectionsMapper(),
        ImagesMapper()
    )
    private val superHeroEntities = mutableListOf<SuperHeroEntity>()
    private val observableSuperHeroEntities = MutableLiveData<List<SuperHeroEntity>>(superHeroEntities)
    private var errorType: ErrorType? = null

    private fun refreshLiveData() {
        observableSuperHeroEntities.postValue(superHeroEntities)
    }

    fun setErrorType(typeOfError: ErrorType) {
        errorType = typeOfError
    }

    override suspend fun getSuperHero(superHeroId: String): Resource<SuperHeroModel> {

        return when (errorType) {
            is ErrorType.NetworkError -> {
                Resource.Error(ErrorType.NetworkError())
            }
            is ErrorType.ServerError -> {
                Resource.Error(ErrorType.ServerError())
            }
            is ErrorType.UnknownError -> {
                Resource.Error(ErrorType.UnknownError())
            }
            else -> {
                val newSuperHeroEntity = SuperHeroEntity(id = superHeroId)
                superHeroEntities.add(newSuperHeroEntity)
                refreshLiveData()
                Resource.Success(superHeroMapper.entityToModel(newSuperHeroEntity))
            }
        }
    }

    override fun observeAllSuperHeroesFromDb(): LiveData<List<SuperHeroModel>> {
        return Transformations.map(observableSuperHeroEntities) { it.map { superHeroMapper.entityToModel(it) } }
    }

}