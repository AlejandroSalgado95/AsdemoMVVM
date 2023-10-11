package com.example.asdemoapp.di

import com.example.asdemoapp.data.repository.PublicApisRepositoryImpl
import com.example.asdemoapp.data.repository.SuperHeroRepositoryImpl
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
abstract class RepositoryModule {
    @Binds
    @ViewModelScoped
    abstract fun bindPublicApisRepository(publicApisRepositoryImpl: PublicApisRepositoryImpl): PublicApisRepository

    @Binds
    @ViewModelScoped
    abstract fun bindSuperHeroRepository(superHeroRepositoryImpl: SuperHeroRepositoryImpl): SuperHeroRepository
}

