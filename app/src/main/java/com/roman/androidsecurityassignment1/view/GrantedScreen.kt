package com.roman.androidsecurityassignment1.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.roman.androidsecurityassignment1.MainActivity
import com.roman.androidsecurityassignment1.viewModel.MainViewModel
import com.roman.androidsecurityassignment1.viewModel.PermissionEvent

@ExperimentalPermissionsApi
@Composable
fun PermissionsGranted(
    mainViewModel: MainViewModel
) {
    mainViewModel.persistPermissionState(false)

    val context  = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.padding(top = 50.dp))

        TextField(value = mainViewModel.passwordText, onValueChange = { mainViewModel.updatePasswordText(it) })

        Spacer(modifier = Modifier.padding(top = 20.dp))

        Button(
            onClick = {
                Log.d(MainActivity.TAG, "Submit clicked")
                mainViewModel.handleEvent(PermissionEvent.Submit(context = context))
            }
        ) {
            Text(text = "Submit")
        }

        Spacer(modifier = Modifier.padding(top = 20.dp))

        Text(text = mainViewModel.resultText)
    }
}