package com.example.evaluacion99minutos.framework.ui.login

sealed class LoginViewState {

    object Idle: LoginViewState()

    object LoginError: LoginViewState()

    object LoginSuccess: LoginViewState()

}
