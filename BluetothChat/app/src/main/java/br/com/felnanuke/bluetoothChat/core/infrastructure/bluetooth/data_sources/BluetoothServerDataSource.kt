package br.com.felnanuke.bluetoothChat.core.infrastructure.bluetooth.data_sources

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.Context
import androidx.annotation.RequiresPermission
import br.com.felnanuke.bluetoothChat.R
import br.com.felnanuke.bluetoothChat.core.domain.data_sources.IMessageListener
import br.com.felnanuke.bluetoothChat.core.domain.data_sources.IPairsListener
import br.com.felnanuke.bluetoothChat.core.domain.data_sources.IServerDataSource
import br.com.felnanuke.bluetoothChat.core.domain.entities.MessageEntity
import br.com.felnanuke.bluetoothChat.core.domain.entities.PairEntity
import br.com.felnanuke.bluetoothChat.core.domain.exceptions.ConnectionException
import java.io.IOException
import java.util.UUID

class BluetoothServerDataSource(private val application: Application) : IServerDataSource {
    private val messageListners = mutableSetOf<IMessageListener>()
    private val pairsListeners = mutableSetOf<IPairsListener>()
    private val blSockets = mutableMapOf<PairEntity, BluetoothSocket>()
    private val blAdapter: BluetoothAdapter
    private val blName: String
    private val blUUID: UUID
    private var bluetoothServerSocket: BluetoothServerSocket? = null
    private var isRunning = true
    private var listenThread: Thread? = null


    init {
        val bluetoothManager =
            application.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        blAdapter = bluetoothManager.adapter
        blName = application.getString(R.string.app_name)
        blUUID = UUID.fromString(application.getString(R.string.app_uuid))

    }



    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    override fun startServer(
        onError: (exception: ConnectionException) -> Unit, onConnect: () -> Unit
    ) {

        if (!blAdapter.isEnabled) {
            onError(ConnectionException("Bluetooth is disabled"))
            return
        }


        try {
            bluetoothServerSocket = blAdapter.listenUsingRfcommWithServiceRecord(blName, blUUID)
        } catch (e: IOException) {
            onError(ConnectionException(e.message ?: "Error while creating server socket"))
            return
        }

        startAcceptLoop()
        onConnect()
    }

    override fun stopServer(
        onError: (exception: ConnectionException) -> Unit, onDisconnect: () -> Unit
    ) {
        isRunning = false
        bluetoothServerSocket?.close()
        listenThread?.interrupt()
    }




    override fun sendMessage(
        message: MessageEntity,
        onError: (exception: ConnectionException) -> Unit,
        onMessageSent: (message: MessageEntity) -> Unit
    ) {

    }

    @RequiresPermission(value = "android.permission.BLUETOOTH_CONNECT")
    private fun startAcceptLoop() {
        listenThread = Thread {
            while (isRunning) {
                try {
                    val socket = bluetoothServerSocket?.accept()

                    socket?.let {
                        val pair = PairEntity(
                            it.remoteDevice.name, it.remoteDevice.address, it.remoteDevice.address
                        )
                        blSockets[pair] = it
                        pairsListeners.forEach { listener ->
                            listener.onReceiveList(blSockets.keys.toList())
                        }
                        listenSocket(socket)
                    }


                } catch (e: IOException) {
                    println("Error while accepting connection")
                    break
                }
            }
        }
        listenThread?.start()

    }

    private fun listenSocket(socket: BluetoothSocket) {


    }

}