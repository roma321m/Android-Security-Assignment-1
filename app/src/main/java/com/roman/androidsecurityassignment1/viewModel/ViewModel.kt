package com.roman.androidsecurityassignment1.viewModel

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.database.Cursor
import android.location.Location
import android.location.LocationManager
import android.os.BatteryManager
import android.provider.ContactsContract
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roman.androidsecurityassignment1.data.DataStoreRepository
import com.roman.androidsecurityassignment1.data.Logic
import com.roman.androidsecurityassignment1.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    companion object {
        const val TAG = "MainViewModel"
    }

    var passwordText by mutableStateOf("")
        private set

    var resultText by mutableStateOf("Submit Password To Login")
        private set

    private val _permissionState = MutableStateFlow<RequestState<Boolean>>(RequestState.Idle)
    val permissionState: StateFlow<RequestState<Boolean>> = _permissionState

    init {
        readPermissionState()
    }

    private fun readPermissionState() {
        _permissionState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readPermissionState
                    .map { state -> state }
                    .collect { state ->
                        _permissionState.value = RequestState.Success(state)
                    }
            }
        } catch (e: Exception) {
            _permissionState.value = RequestState.Error(e)
        }
    }

    fun persistPermissionState(state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistPermissionState(state)
        }
    }

    fun updatePasswordText(newPassword: String) {
        passwordText = newPassword
    }

    fun handleEvent(event: PermissionEvent) {
        when (event) {
            is PermissionEvent.Submit -> submitClickedEvent(context = event.context)
        }
    }

    private fun submitClickedEvent(context: Context) {
        resultText = handleChecks(context)
        passwordText = ""
    }

    private fun handleChecks(context: Context): String {
        if (!Logic.checkPassword(passwordText)) {
            return "Wrong password, please try again"
        }
        if (!Logic.checkLocation(getLocation(context))) {
            return "Not in Israel, please try again"
        }
        if (!Logic.checkChargingState(getBatteryStatus(context))) {
            return "Not connected to the charger, \nplease try again"
        }
        if(!Logic.checkContacts(getNumberOfContacts(context))) {
            return "Not having at lest 5 contacts in the phone, \n please try again"
        }
        Log.d(TAG, "Logged in")
        return "You are logged in!!"
    }

    private fun getNumberOfContacts(context: Context) : Cursor? {
        val contentResolver: ContentResolver = context.contentResolver
        return contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )
    }

    private fun getBatteryStatus(context: Context): Int {
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { filter ->
            context.registerReceiver(null, filter)
        }
        return batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
    }

    private fun getLocation(context: Context): DoubleArray {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers = lm.getProviders(true)

        var l: Location? = null
        for (i in providers.indices.reversed()) {
            if (
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                l = lm.getLastKnownLocation(providers[i])
            }

            if (l != null) break
        }
        val gps = DoubleArray(2)
        if (l != null) {
            gps[0] = l.latitude
            gps[1] = l.longitude
        }
        return gps
    }
}