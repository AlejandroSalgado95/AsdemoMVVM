package com.example.asdemoapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SuperHeroModel (
    val id: String? = null,
    val name: String? = null,
    val slug: String? = null,
    val powerstats: PowerstatsModel? = PowerstatsModel(),
    val appearance: AppearanceModel? = AppearanceModel(),
    val biography: BiographyModel? = BiographyModel(),
    val work: WorkModel? = WorkModel(),
    val connections: ConnectionsModel? = ConnectionsModel(),
    val images: ImagesModel? = ImagesModel()
): Parcelable

@Parcelize
data class PowerstatsModel(
    val intelligence: Int? = null,
    val strength: Int? = null,
    val speed: Int? = null,
    val durability: Int? = null,
    val power: Int? = null,
    val combat: Int? = null
): Parcelable

@Parcelize
data class AppearanceModel(
    val gender: String? = null,
    val race: String? = null,
    val height: List<String>? = listOf(),
    val weight: List<String>? = listOf()
): Parcelable

@Parcelize
data class ImagesModel(
    val sm: String? = null
): Parcelable

@Parcelize
data class BiographyModel(
    val fullName: String? = null
): Parcelable

@Parcelize
data class WorkModel(
    val occupation: String? = null
): Parcelable

@Parcelize
data class ConnectionsModel(
    val relatives: String? = null
): Parcelable