package com.example.evaluacion99minutos.framework.ui.commons

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T : Any, L : LiveData<T>?> LifecycleOwner.observe(liveData: L, body: (T) -> Unit) {
    removeObservers(liveData)
    liveData?.observe(this, Observer(body))
}

fun <T : Any, L : LiveData<T>?> LifecycleOwner.removeObservers(liveData: L) {
    liveData?.removeObservers(this)
}