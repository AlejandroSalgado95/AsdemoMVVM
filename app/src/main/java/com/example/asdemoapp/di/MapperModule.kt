package com.example.asdemoapp.di

import com.example.asdemoapp.data.mapper.ApiCategoryMapper
import com.example.asdemoapp.data.mapper.Mapper
import com.example.asdemoapp.data.mapper.SuperHeroMapper
import com.example.asdemoapp.data.remote.entity.ApiCategoryEntity
import com.example.asdemoapp.data.remote.entity.SuperHeroEntity
import com.example.asdemoapp.data.repository.PublicApisRepositoryImpl
import com.example.asdemoapp.data.repository.SuperHeroRepositoryImpl
import com.example.asdemoapp.domain.model.ApiCategoryModel
import com.example.asdemoapp.domain.model.SuperHeroModel
import com.example.asdemoapp.domain.repository.PublicApisRepository
import com.example.asdemoapp.domain.repository.SuperHeroRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class MapperModule {
    @Binds
    @ViewModelScoped
    abstract fun bindApiCategoryMapper(apiCategoryMapper: ApiCategoryMapper): Mapper<ApiCategoryEntity, ApiCategoryModel>

    @Binds
    @ViewModelScoped
    abstract fun bindSuperHeroMapper(superHeroMapper: SuperHeroMapper): Mapper<SuperHeroEntity, SuperHeroModel>
}
