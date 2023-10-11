package com.example.asdemoapp.data.mapper

import com.example.asdemoapp.data.remote.entity.AppearanceEntity
import com.example.asdemoapp.domain.model.AppearanceModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppearanceMapper @Inject constructor(): Mapper<AppearanceEntity, AppearanceModel> {
    override fun entityToModel(input: AppearanceEntity?): AppearanceModel {
        return AppearanceModel(input?.gender, input?.race, input?.height, input?.weight)
    }
}