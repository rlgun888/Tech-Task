package com.ryangunn.techtask.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryangunn.techtask.login.model.UserBody
import com.ryangunn.techtask.login.model.UserResponse
import com.ryangunn.techtask.repository.Result
import com.ryangunn.techtask.repository.TechTaskRetroFitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel:ViewModel() {
    private val _invalidForm:MutableStateFlow<Boolean> = MutableStateFlow(false)
    val invalidForm:StateFlow<Boolean> = _invalidForm

    private val _loginResult:MutableStateFlow<Result<UserResponse>> = MutableStateFlow(Result.Loading(false))
    val loginResult:StateFlow<Result<UserResponse>> = _loginResult


    fun login(username:String,password:String){
        // In production I would do some type of encryption before sending password to server.
        if (username.isEmpty() || password.isEmpty()){
            _invalidForm.value = true
        }else{
            _invalidForm.value = false
            viewModelScope.launch {
                try {
                    _loginResult.value = Result.Loading(false)
                    val userBody = UserBody(username,password)
                   val userResponse =  TechTaskRetroFitClient.instance.login(userBody)
                    _loginResult.value = Result.Success(userResponse)
                }catch (ex:Exception){
                    _loginResult.value = Result.Error(ex)
                }
            }
        }
    }

    fun resetInvalidForm(){
        _invalidForm.value = false
    }
}