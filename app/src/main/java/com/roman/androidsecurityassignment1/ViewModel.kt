package com.roman.androidsecurityassignment1

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var passwordText by mutableStateOf("")
        private set

    var resultText by mutableStateOf("Submit Password To Login")
        private set

    fun submitClicked(context: Context) {
        resultText = if (handleChecks(context)) {
            "Logged In"
        } else {
            "Wrong Password"
        }
        passwordText = ""
    }

    private fun handleChecks(context: Context): Boolean {

        return false // TODO
    }

    fun updatePasswordText(newPassword: String) {
        passwordText = newPassword
    }
}