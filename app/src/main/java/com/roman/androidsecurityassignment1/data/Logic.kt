package com.roman.androidsecurityassignment1.data

import android.database.Cursor
import android.os.BatteryManager
import android.util.Log

object Logic {

    private const val TAG = "Logic"

    private const val PASSWORD = "1234"

    private const val MIN_LONGITUDE = 34.234628
    private const val MAX_LONGITUDE = 35.512548
    private const val MIN_LATITUDE = 29.493805
    private const val MAX_LATITUDE = 33.282008

    // The required password
    fun checkPassword(input: String) : Boolean {
        Log.d(TAG, "checkPassword - current password = $input")
        return input == PASSWORD
    }

    // Location has to be in Israel
    fun checkLocation(input: DoubleArray) : Boolean {
        Log.d(TAG, "checkLocation - current location = (${input[0]},${input[1]})")
        if (input.isEmpty() || input.size != 2)
            return false
        return input[0] in MIN_LATITUDE..MAX_LATITUDE &&
                input[1] in MIN_LONGITUDE..MAX_LONGITUDE
    }

    // The phone has to have more than 4 contacts
    fun checkContacts(cursor: Cursor?) : Boolean {
        val contactCount: Int = cursor?.count ?: 0
        cursor?.close()
        Log.d(TAG, "checkContacts - number of contacts found = $contactCount")
        return contactCount > 4
    }

    // Phone has to be charging
    fun checkChargingState(batteryStatus: Int) : Boolean {
        Log.d(TAG, "checkChargingState - battery status code = $batteryStatus")
        return batteryStatus == BatteryManager.BATTERY_STATUS_CHARGING
                    || batteryStatus == BatteryManager.BATTERY_STATUS_FULL
    }
}