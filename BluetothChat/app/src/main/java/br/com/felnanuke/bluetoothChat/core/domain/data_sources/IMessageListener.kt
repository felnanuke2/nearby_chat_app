package br.com.felnanuke.bluetoothChat.core.domain.data_sources

import br.com.felnanuke.bluetoothChat.core.domain.entities.MessageEntity

interface IMessageListener {

    fun onMessageReceived(message: MessageEntity)

    fun onMessageUpdateStatus(message: MessageEntity)



}