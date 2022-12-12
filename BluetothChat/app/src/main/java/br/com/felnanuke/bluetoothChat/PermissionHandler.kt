package br.com.felnanuke.bluetoothChat

import android.app.Activity
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionHandler(private val activity: Activity) {
    var isBluetoothPermissionAllowed = ContextCompat.checkSelfPermission(
        activity, android.Manifest.permission.BLUETOOTH
    ) == android.content.pm.PackageManager.PERMISSION_GRANTED
    var isBluetoothAdminPermissionAllowed = ContextCompat.checkSelfPermission(
        activity, android.Manifest.permission.BLUETOOTH_ADMIN
    ) == android.content.pm.PackageManager.PERMISSION_GRANTED
    var isBluetoothConnectPermissionAllowed = ContextCompat.checkSelfPermission(
        activity, android.Manifest.permission.BLUETOOTH_CONNECT
    ) == android.content.pm.PackageManager.PERMISSION_GRANTED
    var isBluetothAdvertisePermissionAllowed = ContextCompat.checkSelfPermission(
        activity, android.Manifest.permission.BLUETOOTH_ADVERTISE
    ) == android.content.pm.PackageManager.PERMISSION_GRANTED
    var isBluetootScanPermissionAllowed = ContextCompat.checkSelfPermission(
        activity, android.Manifest.permission.BLUETOOTH_SCAN
    ) == android.content.pm.PackageManager.PERMISSION_GRANTED

    var isReadExternalStoragePermissionAllowed = ContextCompat.checkSelfPermission(
        activity, android.Manifest.permission.READ_EXTERNAL_STORAGE
    ) == android.content.pm.PackageManager.PERMISSION_GRANTED

    var isAccessWifiStatePermissionAllowed = ContextCompat.checkSelfPermission(
        activity, android.Manifest.permission.ACCESS_WIFI_STATE
    ) == android.content.pm.PackageManager.PERMISSION_GRANTED

    var isChangeWifiStatePermissionAllowed = ContextCompat.checkSelfPermission(
        activity, android.Manifest.permission.CHANGE_WIFI_STATE
    ) == android.content.pm.PackageManager.PERMISSION_GRANTED

    var isAccessCoarseLocationPermissionAllowed = ContextCompat.checkSelfPermission(
        activity, android.Manifest.permission.ACCESS_COARSE_LOCATION
    ) == android.content.pm.PackageManager.PERMISSION_GRANTED

    var isAccessFineLocationPermissionAllowed = ContextCompat.checkSelfPermission(
        activity, android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == android.content.pm.PackageManager.PERMISSION_GRANTED

    private val activityResultLauncher: ActivityResultLauncher<List<String>>? = null

    fun requestPermissionsForAll() {
        val permissions = mutableListOf<String>()
        if (!isBluetoothPermissionAllowed) {
            permissions.add(android.Manifest.permission.BLUETOOTH)
        }
        if (!isBluetoothAdminPermissionAllowed) {
            permissions.add(android.Manifest.permission.BLUETOOTH_ADMIN)
        }
        if (!isBluetoothConnectPermissionAllowed) {
            permissions.add(android.Manifest.permission.BLUETOOTH_CONNECT)
        }
        if (!isBluetothAdvertisePermissionAllowed) {
            permissions.add(android.Manifest.permission.BLUETOOTH_ADVERTISE)
        }
        if (!isBluetootScanPermissionAllowed) {
            permissions.add(android.Manifest.permission.BLUETOOTH_SCAN)
        }
        if (!isReadExternalStoragePermissionAllowed) {
            permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!isAccessWifiStatePermissionAllowed) {
            permissions.add(android.Manifest.permission.ACCESS_WIFI_STATE)
        }
        if (!isChangeWifiStatePermissionAllowed) {
            permissions.add(android.Manifest.permission.CHANGE_WIFI_STATE)
        }
        if (!isAccessCoarseLocationPermissionAllowed) {
            permissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (!isAccessFineLocationPermissionAllowed) {
            permissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
        ActivityCompat.requestPermissions(
            activity, permissions.toTypedArray(), 1
        )


    }


}