package com.example.myapplication.utility

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

import android.net.NetworkCapabilities

import android.os.Build




object Utils{
    /**
    Check the internet activity in the application
     **/
    @SuppressLint("ServiceCast")
    fun  isConnectedToNetwork(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting() ?: false
    }


}