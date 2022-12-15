package br.com.felnanuke.bluetoothChat.core.domain.listeners

import br.com.felnanuke.bluetoothChat.core.domain.entities.MessageEntity
import br.com.felnanuke.bluetoothChat.core.domain.entities.PairEntity

interface IServerListener {

    fun onReceiverMessage(message: MessageEntity)

    fun onConnectPair(pair: PairEntity)

    fun onDisconnectPair(pair: PairEntity)


}