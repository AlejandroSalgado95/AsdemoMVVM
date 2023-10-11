package com.example.asdemoapp.data.remote.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "super_hero"
)
data class SuperHeroEntity(
    @PrimaryKey
    @NonNull
    val id: String,
    val name: String? = null,
    val slug: String? = null,
    val powerstats: PowerstatsEntity? = PowerstatsEntity(),
    val appearance: AppearanceEntity? = AppearanceEntity(),
    val biography: BiographyEntity? = BiographyEntity(),
    val work: WorkEntity? = WorkEntity(),
    val connections: ConnectionsEntity? = ConnectionsEntity(),
    val images: ImagesEntity? = ImagesEntity()
)
data class PowerstatsEntity(
    val intelligence: Int? = null,
    val strength: Int? = null,
    val speed: Int? = null,
    val durability: Int? = null,
    val power: Int? = null,
    val combat: Int? = null
)
data class AppearanceEntity(
    val gender: String? = null,
    val race: String? = null,
    val height: List<String>? = listOf(),
    val weight: List<String>? = listOf(),
    val eyeColor: String? = null,
    val hairColor: String? = null
)
data class BiographyEntity(
    val fullName: String? = null,
    val alterEgos: String? = null,
    val aliases: List<String>? = listOf(),
    val placeOfBirth: String? = null,
    val firstAppearance: String? = null,
    val publisher: String? = null,
    val alignment: String? = null
)
data class WorkEntity(
    val occupation: String? = null,
    val base: String? = null
)
data class ConnectionsEntity(
    val groupAffiliation: String? = null,
    val relatives: String? = null
)
data class ImagesEntity(
    val xs: String? = null,
    val sm: String? = null,
    val md: String? = null,
    val lg: String? = null
)