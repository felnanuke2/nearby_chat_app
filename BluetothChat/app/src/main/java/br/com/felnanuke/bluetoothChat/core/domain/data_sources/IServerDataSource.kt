package br.com.felnanuke.bluetoothChat.core.domain.data_sources

import br.com.felnanuke.bluetoothChat.core.domain.entities.MessageEntity
import br.com.felnanuke.bluetoothChat.core.domain.exceptions.ConnectionException
import br.com.felnanuke.bluetoothChat.core.domain.listeners.IServerListener

interface IServerDataSource {

    fun add(listener: IServerListener)

    fun remove(listener: IServerListener)

    fun startServer(onError: (exception: ConnectionException) -> Unit, onConnect: () -> Unit)

    fun stopServer(onError: (exception: ConnectionException) -> Unit, onDisconnect: () -> Unit)

    fun sendMessage(
        message: MessageEntity,
        onError: (exception: ConnectionException) -> Unit,
        onMessageSent: (message: MessageEntity) -> Unit
    )

}