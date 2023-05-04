package com.example.evaluacion99minutos.data.repositories

import com.example.evaluacion99minutos.data.Result
import com.example.evaluacion99minutos.domain.LoginRequest

interface LoginRepository {

    suspend fun validateLogin(loginRequest: LoginRequest): Result<String>
}