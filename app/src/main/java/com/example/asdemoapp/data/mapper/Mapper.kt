package com.example.asdemoapp.data.mapper

interface Mapper<Entity, Model> {
    fun entityToModel(input: Entity?): Model
}