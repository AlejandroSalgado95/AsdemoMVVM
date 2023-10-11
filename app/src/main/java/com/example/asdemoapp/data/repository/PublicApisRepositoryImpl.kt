package com.example.asdemoapp.data.repository

import com.example.asdemoapp.data.remote.PublicApisApi
import com.example.asdemoapp.domain.model.ApiCategoryModel
import com.example.asdemoapp.data.mapper.Mapper
import com.example.asdemoapp.data.remote.entity.ApiCategoryEntity
import com.example.asdemoapp.domain.repository.PublicApisRepository
import com.example.asdemoapp.utils.ErrorType
import com.example.asdemoapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton

class PublicApisRepositoryImpl @Inject constructor(
    private val remoteDataSource : PublicApisApi,
    private val apiCategoryMapper : Mapper<ApiCategoryEntity, ApiCategoryModel>
):PublicApisRepository {

    override suspend fun getApiCategories(): Resource<List<ApiCategoryModel>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteDataSource.getApiCategories()
                var apiCategoryModelList = listOf<ApiCategoryModel>()
                response.body()?.let {
                    apiCategoryModelList = it.entries.map { apiCategoryMapper.entityToModel(it) }
                }
                Resource.Success(apiCategoryModelList)
            } catch (e: HttpException) {
                Resource.Error(ErrorType.UnknownError())
            } catch (e: IOException) {
                Resource.Error(ErrorType.ServerError())
            } catch (e: SocketTimeoutException) {
                Resource.Error(ErrorType.NetworkError())
            }
        }
    }
}