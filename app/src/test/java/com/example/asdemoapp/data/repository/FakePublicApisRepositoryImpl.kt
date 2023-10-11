package com.example.asdemoapp.data.repository

import com.example.asdemoapp.data.mapper.ApiCategoryMapper
import com.example.asdemoapp.data.mapper.Mapper
import com.example.asdemoapp.data.remote.entity.ApiCategoryEntity
import com.example.asdemoapp.domain.model.ApiCategoryModel
import com.example.asdemoapp.domain.repository.PublicApisRepository
import com.example.asdemoapp.utils.ErrorType
import com.example.asdemoapp.utils.Resource

class FakePublicApisRepositoryImpl() : PublicApisRepository {

    private val apiCategoryMapper = ApiCategoryMapper()
    private var errorType: ErrorType? = null

    fun setErrorType(typeOfError: ErrorType) {
        errorType = typeOfError
    }

    override suspend fun getApiCategories(): Resource<List<ApiCategoryModel>> {

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
                Resource.Success(listOf(ApiCategoryEntity(api = "1")).map { apiCategoryMapper.entityToModel(it) })
            }
        }
    }
}