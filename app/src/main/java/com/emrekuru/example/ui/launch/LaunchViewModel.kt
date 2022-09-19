package com.emrekuru.example.ui.launch

import androidx.lifecycle.viewModelScope
import com.emrekuru.example.base.BaseViewModel
import com.emrekuru.example.network.response.EmployeeResponse
import com.emrekuru.example.ui.home.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(): BaseViewModel() {

    private val mutableViewState = MutableStateFlow<DoWorkSealed>(
        DoWorkSealed.IDLE
    )
    val viewState : StateFlow<DoWorkSealed> = mutableViewState

    fun doSomeWork(){
        var operationResult = true
        viewModelScope.launch {
            runCatching {
                mutableViewState.value = DoWorkSealed.LOADING
                // do some logic
            }.onFailure {
                loading.value = false
                mutableViewState.value = DoWorkSealed.ERROR(operationResult)
            }.onSuccess {
                loading.value = false
                mutableViewState.value = DoWorkSealed.SUCCESS(operationResult)
            }
        }
    }

    sealed class DoWorkSealed{
        object IDLE : DoWorkSealed()
        object LOADING : DoWorkSealed()
        data class ERROR(val result: Boolean) : DoWorkSealed()
        data class SUCCESS(val result: Boolean) : DoWorkSealed()

    }
}