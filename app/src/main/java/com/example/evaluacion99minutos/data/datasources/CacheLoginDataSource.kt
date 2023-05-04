package com.example.evaluacion99minutos.data.datasources

import com.example.evaluacion99minutos.data.Result
import com.example.evaluacion99minutos.domain.LoginRequest

interface CacheLoginDataSource {

    suspend fun login(loginRequest: LoginRequest): Result<String>
}