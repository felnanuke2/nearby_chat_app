package br.com.felnanuke.bluetoothChat.core.domain.repositories

import br.com.felnanuke.bluetoothChat.core.domain.data_sources.IServerDataSource
import br.com.felnanuke.bluetoothChat.core.domain.entities.MessageEntity
import br.com.felnanuke.bluetoothChat.core.domain.exceptions.ConnectionException

class ServerRepository(
    private val serverDataSource: IServerDataSource,
) {


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

}