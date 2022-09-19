package com.emrekuru.example.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

open class BaseViewModel : ViewModel() {
    var loading = MutableStateFlow(false)
}