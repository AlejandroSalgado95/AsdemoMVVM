package com.example.asdemoapp.data.mapper

import com.example.asdemoapp.data.remote.entity.BiographyEntity
import com.example.asdemoapp.domain.model.BiographyModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BiographyMapper @Inject constructor(): Mapper<BiographyEntity, BiographyModel> {
    override fun entityToModel(input: BiographyEntity?): BiographyModel {
        return BiographyModel(input?.fullName)
    }
}