package com.roman.androidsecurityassignment1

import android.Manifest
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@ExperimentalPermissionsApi
@Composable
fun MainScreen(
    mainViewModel: MainViewModel
) {
    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
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