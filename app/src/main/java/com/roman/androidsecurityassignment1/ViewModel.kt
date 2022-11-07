package com.roman.androidsecurityassignment1

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    companion object {
        const val TAG = "MainViewModel"
    }

    var passwordText by mutableStateOf("")
        private set

    var resultText by mutableStateOf("Submit Password To Login")
        private set

    var afterRationale by mutableStateOf(false)
        private set

    fun updateAfterRationale(newState: Boolean) {
        afterRationale = newState
    }

    fun submitClicked() {
        resultText = if (handleChecks()) {
            "Logged In"
        } else {
            "Wrong Password"
        }
        passwordText = ""
    }

    private fun handleChecks(): Boolean {

        return false // TODO
    }

    fun updatePasswordText(newPassword: String) {
        passwordText = newPassword
    }
}