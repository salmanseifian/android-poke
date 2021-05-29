package com.salmanseifian.androidpoke.data.di

import com.salmanseifian.androidpoke.data.repository.PokeRepository
import com.salmanseifian.androidpoke.data.repository.PokeRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRepository(impl: PokeRepositoryImp): PokeRepository
}