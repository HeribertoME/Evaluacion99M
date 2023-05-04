package com.example.evaluacion99minutos.framework.ui.login

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion99minutos.data.repositories.LoginRepository
import com.example.evaluacion99minutos.domain.LoginRequest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.evaluacion99minutos.data.Result
import com.example.evaluacion99minutos.framework.ui.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel(), LifecycleObserver {

    val email = MutableLiveData(Constants.userEmail)
    val psw = MutableLiveData(Constants.userPassword)
    val progress = MutableLiveData(false)

    private val _viewState = MutableLiveData<LoginViewState>()
    val viewState: LiveData<LoginViewState> = _viewState

    fun onLogin() {
        viewModelScope.launch {
            progress.value = true
            loginRepository.validateLogin(
                LoginRequest(
                    email = email.value ?: "",
                    password = psw.value ?: ""
                )
            ).let { result ->
                when (result) {
                    is Result.Success -> {
                        val token = result.data
                        Log.d(LoginViewModel::class.java.simpleName, "Token=$token")
                        _viewState.value = LoginViewState.LoginSuccess
                    }
                    is Result.Error -> _viewState.value = LoginViewState.LoginError
                    else -> {}
                }
                progress.value = false
            }
        }
    }
}