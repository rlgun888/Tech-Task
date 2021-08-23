package com.ryangunn.techtask.driver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryangunn.techtask.driver.model.Driver
import com.ryangunn.techtask.repository.Result
import com.ryangunn.techtask.repository.TechTaskRetroFitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DriversViewModel : ViewModel() {
    private val _drivers: MutableStateFlow<Result<List<Driver>>> =
        MutableStateFlow(Result.Loading(false))
    val drivers: StateFlow<Result<List<Driver>>> = _drivers

    fun getDrivers() {
        viewModelScope.launch {
            try {
                _drivers.value = Result.Loading(true)
                val drivers = TechTaskRetroFitClient.instance.getDrivers()
                _drivers.value = Result.Success(drivers)
            } catch (ex: Exception) {
                _drivers.value = Result.Error(ex)
            }
        }
    }
}