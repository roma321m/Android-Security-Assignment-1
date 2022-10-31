package com.roman.androidsecurityassignment1

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.roman.androidsecurityassignment1.MainActivity.Companion.TAG
import com.roman.androidsecurityassignment1.ui.theme.AndroidSecurityAssignment1Theme

class MainActivity : ComponentActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate")

        setContent {
            AndroidSecurityAssignment1Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    App(mainViewModel)
                }
            }
        }
    }
}

@Composable
fun App(mainViewModel: MainViewModel) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.padding(top = 20.dp))

        TextField(value = mainViewModel.passwordText, onValueChange = { mainViewModel.updatePasswordText(it) })

        Spacer(modifier = Modifier.padding(top = 20.dp))

        Button(
            onClick = {
                Log.d(TAG, "Submit clicked")
                mainViewModel.submitClicked(context)
            }
        ) {
            Text(text = "Submit")
        }

        Spacer(modifier = Modifier.padding(top = 20.dp))

        Text(text = mainViewModel.resultText)

        Permission()
    }
}


@Composable
fun Permission() {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted: Do something
            Log.d(TAG,"PERMISSION GRANTED")

        } else {
            // Permission Denied: Do something
            Log.d(TAG,"PERMISSION DENIED")
        }
    }
    val context = LocalContext.current

    Button(
        onClick = {
            // Check permission
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) -> {
                    // Some works that require permission
                    Log.d(TAG,"Code requires permission")
                }
                else -> {
                    // Asking for permission
                    launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }
    ) {
        Text(text = "Check and Request Permission")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidSecurityAssignment1Theme {

    }
}