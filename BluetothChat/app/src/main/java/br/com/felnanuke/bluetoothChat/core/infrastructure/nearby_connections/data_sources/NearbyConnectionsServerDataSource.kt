package br.com.felnanuke.bluetoothChat.core.infrastructure.nearby_connections.data_sources

import android.app.Application
import android.net.Uri
import br.com.felnanuke.bluetoothChat.core.domain.data_sources.IServerDataSource
import br.com.felnanuke.bluetoothChat.core.domain.data_sources.IPairDataSource
import br.com.felnanuke.bluetoothChat.core.domain.entities.MessageEntity
import br.com.felnanuke.bluetoothChat.core.domain.entities.MessageSerializable
import br.com.felnanuke.bluetoothChat.core.domain.entities.PairEntity
import br.com.felnanuke.bluetoothChat.core.domain.enums.PairStatus
import br.com.felnanuke.bluetoothChat.core.domain.exceptions.ConnectionException
import br.com.felnanuke.bluetoothChat.core.domain.listeners.IServerListener
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString


class NearbyConnectionsServerDataSource(
    private val application: Application,
    private val pairDataSource: IPairDataSource,
) : IServerDataSource {

    private val strategy = Strategy.P2P_CLUSTER
    private val serviceId = application.packageName
    private val listeners = mutableSetOf<IServerListener>()

    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
            if (info.serviceId != serviceId) return

//            notifyListenersConnect(PairEntity(info.endpointName, endpointId, endpointId).apply {
//                setPairStatus(PairStatus.Discovered)
//            })

            Nearby.getConnectionsClient(application).requestConnection(
                "felipe", endpointId, connectionLifecycleCallback
            )


        }

        override fun onEndpointLost(endpointId: String) {
            notifyListenersDisconnect(endpointId.toPairEntityFromId())
        }

    }

    private val payloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {

            when (payload.type) {
                Payload.Type.BYTES -> {
                    MessageSerializable.json.decodeFromString<MessageEntity>(String(payload.asBytes()!!))
                        .let { message ->
                            notifyListeners(message.apply {
                                owner.address = endpointId
                            })
                        }
                }

            }
        }

        override fun onPayloadTransferUpdate(
            endpointId: String, payloadTransferUpdate: PayloadTransferUpdate
        ) {
            print("onPayloadTransferUpdate")
        }

    }

    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {

        override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {

//            notifyListenersConnect(PairEntity(
//                connectionInfo.endpointName, endpointId, endpointId
//            ).apply {
//                setPairStatus(PairStatus.Connecting)
//            })

            Nearby.getConnectionsClient(application).acceptConnection(endpointId, payloadCallback)

        }

        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            when (result.status.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    notifyListenersConnec(endpointId.toPairEntityFromId().apply {
                        setPairStatus(PairStatus.Connected)
                    })
                }
                else -> {

                }
            }
        }

        override fun onDisconnected(endpointId: String) {
            notifyListenersDisconnect(endpointId.toPairEntityFromId())
        }

    }

    override fun add(listener: IServerListener) {
        listeners.add(listener)
    }

    override fun remove(listener: IServerListener) {
        listeners.remove(listener)
    }


    override fun startServer(
        onError: (exception: ConnectionException) -> Unit, onConnect: () -> Unit
    ) {
        startAdvertising(onConnect, onError)
        startDiscovery()
    }

    override fun stopServer(
        onError: (exception: ConnectionException) -> Unit, onDisconnect: () -> Unit
    ) {
        stopAdvertising(onDisconnect)
        stopDiscovery()
    }

    private fun stopAdvertising(
        onDisconnect: () -> Unit
    ) {
        Nearby.getConnectionsClient(application).stopAdvertising()
        onDisconnect()

    }

    private fun stopDiscovery() {
        Nearby.getConnectionsClient(application).stopDiscovery()
    }

    override fun sendMessage(
        message: MessageEntity,
        onError: (exception: ConnectionException) -> Unit,
        onMessageSent: (message: MessageEntity) -> Unit
    ) {


        val bytes = MessageSerializable.json.encodeToString(message).toByteArray()
        val payload = Payload.fromBytes(bytes)
        Nearby.getConnectionsClient(application).sendPayload(message.recipient.address, payload)
            .addOnSuccessListener {
                print(it)
            }.addOnFailureListener {
                print(it)
            }
        sentMessageAttachment(message)
        notifyListeners(message)
        onMessageSent(message)

    }


    private fun sentMessageAttachment(
        message: MessageEntity,
    ) {
//        if (message.content is MessageContentTextAndAttachments && (message.content as MessageContentTextAndAttachments).attachments.isNotEmpty()) {
//            (message.content as MessageContentTextAndAttachments).attachments.forEach { attachment ->
//                val file = application.contentResolver.openFileDescriptor(
//                    Uri.parse(attachment.fileUri!!), "r"
//                )
//                val payload = Payload.fromFile(file!!)
//                attachment.id = payload.id.toString()
//                Nearby.getConnectionsClient(application).sendPayload(
//                    message.recipient.id, payload,
//                ).addOnFailureListener {
//                    print(it)
//                }.addOnSuccessListener {
//                    print(it)
//                }
//                file.close()
//            }
//        }
    }


    private fun startAdvertising(
        onConnect: () -> Unit, onError: (exception: ConnectionException) -> Unit
    ) {
        val advertisingOptions = AdvertisingOptions.Builder().setStrategy(strategy).build()
        pairDataSource.getMe { me ->
            Nearby.getConnectionsClient(application).startAdvertising(
                me.name, serviceId, connectionLifecycleCallback, advertisingOptions
            ).addOnSuccessListener(OnSuccessListener {
                onConnect()
            }).addOnFailureListener(OnFailureListener { e: Exception? ->
                onError(ConnectionException(e?.message ?: "Unknown error"))
            })

        }
    }

    private fun startDiscovery() {
        val discoveryOptions = DiscoveryOptions.Builder().setStrategy(strategy).build()
        Nearby.getConnectionsClient(application)
            .startDiscovery(serviceId, endpointDiscoveryCallback, discoveryOptions)
            .addOnFailureListener {
                print(it)
            }.addOnSuccessListener {
                print(it)
            }
    }


    private fun String.toPairEntityFromId(): PairEntity {
        return PairEntity(
            "", this, this
        )
    }

    private fun notifyListeners(message: MessageEntity) {
        listeners.forEach {
            it.onReceiverMessage(message)
        }
    }

    private fun notifyListenersConnec(pair: PairEntity) {
        listeners.forEach {
            it.onConnectPair(pair)
        }
    }

    private fun notifyListenersDisconnect(pair: PairEntity) {
        listeners.forEach {
            it.onDisconnectPair(pair)
        }
    }


}