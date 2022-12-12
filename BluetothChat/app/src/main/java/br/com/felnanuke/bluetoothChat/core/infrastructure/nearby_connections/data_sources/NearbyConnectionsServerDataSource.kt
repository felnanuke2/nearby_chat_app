package br.com.felnanuke.bluetoothChat.core.infrastructure.nearby_connections.data_sources

import android.app.Application
import br.com.felnanuke.bluetoothChat.core.domain.data_sources.IMessagesDataSource
import br.com.felnanuke.bluetoothChat.core.domain.data_sources.IServerDataSource
import br.com.felnanuke.bluetoothChat.core.domain.data_sources.IPairDataSource
import br.com.felnanuke.bluetoothChat.core.domain.entities.MessageContentTextAndAttachments
import br.com.felnanuke.bluetoothChat.core.domain.entities.MessageEntity
import br.com.felnanuke.bluetoothChat.core.domain.entities.PairEntity
import br.com.felnanuke.bluetoothChat.core.domain.enums.PairStatus
import br.com.felnanuke.bluetoothChat.core.domain.exceptions.ConnectionException
import com.google.android.gms.common.api.Status
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class NearbyConnectionsServerDataSource(
    private val application: Application,
    private val pairsDataSource: IPairDataSource,
    private val messageDataSource: IMessagesDataSource,
) : IServerDataSource {

    private val strategy = Strategy.P2P_STAR
    private val serviceId = application.packageName

    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
            pairsDataSource.addPair(info.toPairEntity().apply {
                setPairStatus(PairStatus.Discovered)
            })

        }

        override fun onEndpointLost(endpointId: String) {
            pairsDataSource.updatePair(PairStatus.Disconnected, endpointId.toPairEntityFromId())
        }

    }

    private val payloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
            TODO("Not yet implemented")
        }

        override fun onPayloadTransferUpdate(
            endpointId: String, payloadTransferUpdate: PayloadTransferUpdate
        ) {
            TODO("Not yet implemented")
        }

    }

    private val discoveryCallback = object : ConnectionLifecycleCallback() {

        override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
            pairsDataSource.addPair(connectionInfo.toPairEntity().apply {
                setPairStatus(PairStatus.Connecting)
            })
            Nearby.getConnectionsClient(application).acceptConnection(endpointId, payloadCallback)
        }

        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            when (result.status) {
                Status.RESULT_SUCCESS -> {
                    pairsDataSource.updatePair(
                        PairStatus.Connected, endpointId.toPairEntityFromId()
                    )
                }
                else -> {
                    pairsDataSource.updatePair(
                        PairStatus.ConnectionFailed, endpointId.toPairEntityFromId()
                    )
                }
            }
        }

        override fun onDisconnected(endpointId: String) {
            pairsDataSource.updatePair(PairStatus.Disconnected, endpointId.toPairEntityFromId())
        }

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


        val bytes = Json.encodeToString(message).toByteArray()
        val payload = Payload.fromBytes(bytes)
        Nearby.getConnectionsClient(application).sendPayload(message.owner.id, payload)
        sentMessageAttachment(message)
        messageDataSource.addMessage(message)
        onMessageSent(message)

    }


    private fun sentMessageAttachment(
        message: MessageEntity,
    ) {
        if (message.content is MessageContentTextAndAttachments && (message.content as MessageContentTextAndAttachments).attachments.isNotEmpty()) {
            (message.content as MessageContentTextAndAttachments).attachments.forEach { attachment ->
                val file = application.contentResolver.openFileDescriptor(attachment.fileUri!!, "r")
                val payload = Payload.fromFile(file!!)
                attachment.id = payload.id.toString()
                Nearby.getConnectionsClient(application).sendPayload(
                    message.recipient.id, payload,
                )
                file.close()
            }
        }
    }


    private fun startAdvertising(
        onConnect: () -> Unit, onError: (exception: ConnectionException) -> Unit
    ) {
        val advertisingOptions = AdvertisingOptions.Builder().setStrategy(strategy).build()
        pairsDataSource.getMe { me ->
            Nearby.getConnectionsClient(application).startAdvertising(
                me.name, serviceId, discoveryCallback, advertisingOptions
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
    }


    private fun DiscoveredEndpointInfo.toPairEntity(): PairEntity {
        return PairEntity(
            endpointName, serviceId, serviceId
        )
    }

    private fun ConnectionInfo.toPairEntity(): PairEntity {
        return PairEntity(
            endpointName, serviceId, serviceId
        )
    }

    private fun String.toPairEntityFromId(): PairEntity {
        return PairEntity(
            "", this, this
        )
    }

}