package com.example.evaluacion99minutos.framework.hilt

import com.example.evaluacion99minutos.data.datasources.CacheLoginDataSource
import com.example.evaluacion99minutos.data.datasources.impl.CacheLoginDataSourceImpl
import com.example.evaluacion99minutos.data.repositories.LoginRepository
import com.example.evaluacion99minutos.data.repositories.impl.LoginRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    @Singleton
    fun provideCacheLoginDataSource(): CacheLoginDataSource = CacheLoginDataSourceImpl()

    @Provides
    @Singleton
    fun provideLoginRepository(
        cacheLoginDataSource: CacheLoginDataSource
    ): LoginRepository = LoginRepositoryImpl(cacheLoginDataSource)
}