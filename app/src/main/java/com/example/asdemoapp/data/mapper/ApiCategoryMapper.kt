package com.example.asdemoapp.data.mapper

import com.example.asdemoapp.data.remote.entity.ApiCategoryEntity
import com.example.asdemoapp.domain.model.ApiCategoryModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiCategoryMapper @Inject constructor() : Mapper<ApiCategoryEntity, ApiCategoryModel> {
    override fun entityToModel(input: ApiCategoryEntity?): ApiCategoryModel {
        val httpsValue = input?.https ?: false
        return ApiCategoryModel(
            input?.api,
            input?.description,
            input?.auth,
            httpsValue,
            input?.cors,
            input?.link,
            input?.category
        )
    }
}