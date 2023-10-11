package com.example.asdemoapp.data.mapper

import com.example.asdemoapp.data.remote.entity.WorkEntity
import com.example.asdemoapp.domain.model.WorkModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkMapper @Inject constructor(): Mapper<WorkEntity, WorkModel> {
    override fun entityToModel(input: WorkEntity?): WorkModel {
        return WorkModel(input?.occupation)
    }
}