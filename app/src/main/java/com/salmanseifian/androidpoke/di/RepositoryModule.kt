package com.salmanseifian.androidpoke.di

import com.salmanseifian.androidpoke.data.repository.PokeRepository
import com.salmanseifian.androidpoke.data.repository.RemotePokeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped


@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class RepositoryModule {

    @ActivityRetainedScoped
    @Binds
    abstract fun bingRepository(repository: RemotePokeRepository): PokeRepository
}