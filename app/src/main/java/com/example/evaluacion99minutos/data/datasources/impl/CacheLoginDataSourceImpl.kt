package com.example.evaluacion99minutos.data.datasources.impl

import com.example.evaluacion99minutos.data.Result
import com.example.evaluacion99minutos.data.datasources.CacheLoginDataSource
import com.example.evaluacion99minutos.domain.LoginRequest
import com.example.evaluacion99minutos.framework.ui.utils.Constants.userEmail
import com.example.evaluacion99minutos.framework.ui.utils.Constants.userPassword
import kotlinx.coroutines.delay

class CacheLoginDataSourceImpl : CacheLoginDataSource {

    override suspend fun login(loginRequest: LoginRequest): Result<String> {
        delay(1_500)
        return if (isValidCredentials(loginRequest)) {
            val token = (1..150).map {
                "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789".random()
            }.joinToString("")
            Result.Success(token)
        } else {
            Result.Error(Exception())
        }
    }

    private fun isValidCredentials(loginRequest: LoginRequest) =
        loginRequest.email == userEmail && loginRequest.password == userPassword

}