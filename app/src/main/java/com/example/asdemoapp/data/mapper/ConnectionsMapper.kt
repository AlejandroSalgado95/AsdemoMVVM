package com.example.asdemoapp.data.mapper

import com.example.asdemoapp.data.remote.entity.ConnectionsEntity
import com.example.asdemoapp.domain.model.ConnectionsModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectionsMapper @Inject constructor(): Mapper<ConnectionsEntity, ConnectionsModel> {
    override fun entityToModel(input: ConnectionsEntity?): ConnectionsModel {
        return ConnectionsModel(input?.relatives)
    }
}