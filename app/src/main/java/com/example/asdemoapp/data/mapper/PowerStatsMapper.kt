package com.example.asdemoapp.data.mapper

import com.example.asdemoapp.data.remote.entity.PowerstatsEntity
import com.example.asdemoapp.domain.model.PowerstatsModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PowerStatsMapper @Inject constructor() : Mapper<PowerstatsEntity, PowerstatsModel> {
    override fun entityToModel(input: PowerstatsEntity?): PowerstatsModel {
        return PowerstatsModel(
            input?.intelligence,
            input?.strength,
            input?.speed,
            input?.durability,
            input?.power,
            input?.combat
        )
    }
}