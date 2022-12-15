package br.com.felnanuke.bluetoothChat.core.domain.repositories

import br.com.felnanuke.bluetoothChat.core.domain.data_sources.IMessagesDataSource
import br.com.felnanuke.bluetoothChat.core.domain.data_sources.IPairDataSource
import br.com.felnanuke.bluetoothChat.core.domain.data_sources.IServerDataSource
import br.com.felnanuke.bluetoothChat.core.domain.entities.MessageContent
import br.com.felnanuke.bluetoothChat.core.domain.entities.MessageEntity
import br.com.felnanuke.bluetoothChat.core.domain.entities.PairEntity
import br.com.felnanuke.bluetoothChat.core.domain.enums.MessageContentType
import br.com.felnanuke.bluetoothChat.core.domain.enums.MessageType
import br.com.felnanuke.bluetoothChat.core.domain.exceptions.ConnectionException
import br.com.felnanuke.bluetoothChat.core.domain.listeners.IServerListener
import br.com.felnanuke.bluetoothChat.core.infrastructure.realm.data_sources.RealmPairDataSource
import br.com.felnanuke.bluetoothChat.core.infrastructure.realm.models.RealmMessageContentModel
import java.util.UUID

class ServerRepository(
    private val serverDataSource: IServerDataSource,
    private val messageDataSource: IMessagesDataSource,
    private val pairDataSource: IPairDataSource
) : IServerListener {

    init {
        serverDataSource.add(this)
    }


    fun startServer(onConnect: () -> Unit, onError: (exception: ConnectionException) -> Unit) {
        serverDataSource.startServer(onConnect = onConnect, onError = onError)
    }

    fun stopServer(onDisconnect: () -> Unit, onError: (exception: ConnectionException) -> Unit) {
        serverDataSource.stopServer(onDisconnect = onDisconnect, onError = onError)
    }

    fun sendMessage(
        message: MessageEntity,
        onMessageSent: (message: MessageEntity) -> Unit,
        onError: (exception: ConnectionException) -> Unit
    ) {
        serverDataSource.sendMessage(message, onMessageSent = onMessageSent, onError = onError)
    }

    override fun onReceiverMessage(message: MessageEntity) {
        when (message.type) {
            MessageType.MESSAGE_RECEIVED -> {
                messageDataSource.addMessage(message)
            }
            MessageType.DELIVERY_CONFIRMATION_RECEIVED -> TODO()
            MessageType.READ_CONFIRMATION_RECEIVED -> TODO()
            MessageType.CONNECT_PAIR -> {
                pairDataSource.addPair(message.owner)
            }
        }
    }

    override fun onConnectPair(pair: PairEntity) {
        sendConnectionMessage(pair)
    }

    override fun onDisconnectPair(pair: PairEntity) {
//        pairDataSource.deletePair(pair, null, null)
    }


    private fun sendConnectionMessage(pair: PairEntity) {
        pairDataSource.getMe { me ->
            val message = MessageEntity(
                owner = me, recipient = pair, type = MessageType.CONNECT_PAIR,
                content = MessageContent.none(),
                deliveredAt = null,
                createdAt = System.currentTimeMillis(),
                id = UUID.randomUUID().toString(),
            )
            sendMessage(message, {}, {})
        }

    }

}