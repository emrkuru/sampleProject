package com.emrekuru.example.ui.home

import androidx.lifecycle.viewModelScope
import com.emrekuru.example.base.BaseViewModel
import com.emrekuru.example.network.NetworkClient
import com.emrekuru.example.network.response.EmployeeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val networkClient: NetworkClient
): BaseViewModel() {

    private val mutableViewState = MutableStateFlow<EmployeeStateSealed>(
        EmployeeStateSealed.IDLE
    )
    val viewState : StateFlow<EmployeeStateSealed> = mutableViewState

    fun getEmployees(){
        viewModelScope.launch {
            runCatching {
                mutableViewState.value = EmployeeStateSealed.LOADING
                networkClient.getAllEmployees()
            }.onFailure {
                loading.value = false
                mutableViewState.value = EmployeeStateSealed.ERROR
            }.onSuccess {
                loading.value = false
                mutableViewState.value = EmployeeStateSealed.SUCCESS(it)
            }
        }
    }

    sealed class EmployeeStateSealed{
        object IDLE : EmployeeStateSealed()
        object LOADING : EmployeeStateSealed()
        object ERROR : EmployeeStateSealed()
        data class SUCCESS(val employeeList: ArrayList<EmployeeResponse>) : EmployeeStateSealed()

    }

}