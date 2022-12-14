package br.com.felnanuke.bluetoothChat

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class BluetoothChatServices : Service() {

    private val binder = LocalBinder()

    override fun onBind(p0: Intent?): IBinder {
        return binder
    }


    inner class LocalBinder : Binder() {
        fun getService(): BluetoothChatServices = this@BluetoothChatServices
    }
}