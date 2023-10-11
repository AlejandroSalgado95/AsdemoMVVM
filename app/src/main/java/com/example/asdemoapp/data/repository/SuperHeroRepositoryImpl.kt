package com.example.asdemoapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.example.asdemoapp.data.local.SuperHeroDao
import com.example.asdemoapp.data.mapper.Mapper
import com.example.asdemoapp.data.remote.SuperHeroApi
import com.example.asdemoapp.domain.model.SuperHeroModel
import com.example.asdemoapp.data.remote.entity.SuperHeroEntity
import com.example.asdemoapp.domain.repository.SuperHeroRepository
import com.example.asdemoapp.utils.ErrorType
import com.example.asdemoapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

class SuperHeroRepositoryImpl @Inject constructor(
    private val remoteDataSource: SuperHeroApi,
    private val localDataSource: SuperHeroDao,
    private val superHeroMapper: Mapper<SuperHeroEntity, SuperHeroModel>
) : SuperHeroRepository {

    override suspend fun getSuperHero(superHeroId: String): Resource<SuperHeroModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getSuperHero(superHeroId)
                var superHeroModel: SuperHeroModel? = null
                response.body()?.let {
                    localDataSource.saveSuperHeroToDb(it)
                    superHeroModel = superHeroMapper.entityToModel(it)
                }
                Resource.Success(superHeroModel)
            } catch (e: HttpException) {
                Resource.Error(ErrorType.UnknownError())
            } catch (e: IOException) {
                Resource.Error(ErrorType.ServerError())
            } catch (e: SocketTimeoutException) {
                Resource.Error(ErrorType.NetworkError())
            }
        }
    }

    override fun observeAllSuperHeroesFromDb(): LiveData<List<SuperHeroModel>> {
        val result = localDataSource.observeAllSuperHeroesFromDb()
        val resultToDomain: LiveData<List<SuperHeroModel>> =
            Transformations.map(result) { it.map { superHeroMapper.entityToModel(it) } }
        return resultToDomain
    }
}