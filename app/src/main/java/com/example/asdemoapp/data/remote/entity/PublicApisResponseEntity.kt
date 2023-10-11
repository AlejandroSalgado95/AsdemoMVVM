package com.example.asdemoapp.data.remote.entity

data class PublicApisResponseEntity(
    val count : Int,
    val entries : List<ApiCategoryEntity> = listOf()
)