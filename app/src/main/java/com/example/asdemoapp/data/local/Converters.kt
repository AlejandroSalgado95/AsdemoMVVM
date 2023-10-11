package com.example.asdemoapp.data.local

import androidx.room.TypeConverter
import com.example.asdemoapp.data.remote.entity.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {

    @TypeConverter
    fun stringToPowerstatsEntity(value: String): PowerstatsEntity {
        val type: Type = object : TypeToken<PowerstatsEntity?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun powerstatsEntityToString(value: PowerstatsEntity): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun stringToAppearanceEntity(value: String): AppearanceEntity {
        val type: Type = object : TypeToken<AppearanceEntity?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun appearanceEntityToString(value: AppearanceEntity): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun stringToBiographyEntity(value: String): BiographyEntity {
        val type: Type = object : TypeToken<BiographyEntity?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun biographyEntityToString(value: BiographyEntity): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun stringToWorkEntity(value: String): WorkEntity {
        val type: Type = object : TypeToken<WorkEntity?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun workEntityToString(value: WorkEntity): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun stringToConnectionsEntity(value: String): ConnectionsEntity {
        val type: Type = object : TypeToken<ConnectionsEntity?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun connectionsEntityToString(value: ConnectionsEntity): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun stringToImagesEntity(value: String): ImagesEntity {
        val type: Type = object : TypeToken<ImagesEntity?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun imagesEntityToString(value: ImagesEntity): String {
        val gson = Gson()
        return gson.toJson(value)
    }
}