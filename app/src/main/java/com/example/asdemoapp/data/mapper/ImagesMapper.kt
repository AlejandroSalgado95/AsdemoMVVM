package com.example.asdemoapp.data.mapper

import com.example.asdemoapp.data.remote.entity.ImagesEntity
import com.example.asdemoapp.domain.model.ImagesModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesMapper @Inject constructor(): Mapper<ImagesEntity, ImagesModel> {
    override fun entityToModel(input: ImagesEntity?): ImagesModel {
        return ImagesModel(input?.sm)
    }
}