package com.example.asdemoapp.domain.repository

import com.example.asdemoapp.domain.model.ApiCategoryModel
import com.example.asdemoapp.utils.Resource

interface PublicApisRepository {
    suspend fun getApiCategories(): Resource<List<ApiCategoryModel>>
}