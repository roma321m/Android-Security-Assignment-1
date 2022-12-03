package com.roman.androidsecurityassignment1.viewModel

import android.content.Context

sealed class PermissionEvent {
    class Submit(val context: Context) : PermissionEvent()
}