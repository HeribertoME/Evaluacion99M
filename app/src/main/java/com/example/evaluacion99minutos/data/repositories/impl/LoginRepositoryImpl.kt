package com.example.evaluacion99minutos.data.repositories.impl

import com.example.evaluacion99minutos.data.Result
import com.example.evaluacion99minutos.data.datasources.CacheLoginDataSource
import com.example.evaluacion99minutos.domain.LoginRequest
import com.example.evaluacion99minutos.data.repositories.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val cacheLoginDataSource: CacheLoginDataSource
) : LoginRepository {

    override suspend fun validateLogin(loginRequest: LoginRequest): Result<String> =
        cacheLoginDataSource.login(loginRequest)
}