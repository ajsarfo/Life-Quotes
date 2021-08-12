package com.sarftec.lifequotes.data.injection

import com.sarftec.lifequotes.data.repository.Repository
import com.sarftec.lifequotes.data.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractInjection {

    @Binds
    abstract fun repository(repositoryImpl: RepositoryImpl) : Repository
}