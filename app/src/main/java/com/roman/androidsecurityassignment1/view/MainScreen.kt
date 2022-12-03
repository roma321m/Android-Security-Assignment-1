package com.roman.androidsecurityassignment1.view

import android.Manifest
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.roman.androidsecurityassignment1.viewModel.MainViewModel


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel
) {
    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS,
        )
    )

    if (multiplePermissionsState.allPermissionsGranted) {
        PermissionsGranted(
            mainViewModel = mainViewModel
        )
    } else {
        AskForPermissions(
            mainViewModel = mainViewModel,
            multiplePermissionsState = multiplePermissionsState
        )
    }
}