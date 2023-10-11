package com.example.asdemoapp.data.mapper

import com.example.asdemoapp.data.remote.entity.SuperHeroEntity
import com.example.asdemoapp.domain.model.SuperHeroModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SuperHeroMapper @Inject constructor(
    private val powerStatsMapper : PowerStatsMapper,
    private val appearanceMapper : AppearanceMapper,
    private val biographyMapper : BiographyMapper,
    private val workMapper : WorkMapper,
    private val connectionsMapper : ConnectionsMapper,
    private val imagesMapper : ImagesMapper,
) : Mapper<SuperHeroEntity, SuperHeroModel> {


    override fun entityToModel(input: SuperHeroEntity?): SuperHeroModel {
        return SuperHeroModel(
            input?.id,
            input?.name,
            input?.slug,
            powerStatsMapper.entityToModel(input?.powerstats),
            appearanceMapper.entityToModel(input?.appearance),
            biographyMapper.entityToModel(input?.biography),
            workMapper.entityToModel(input?.work),
            connectionsMapper.entityToModel(input?.connections),
            imagesMapper.entityToModel(input?.images)
        )
    }
}