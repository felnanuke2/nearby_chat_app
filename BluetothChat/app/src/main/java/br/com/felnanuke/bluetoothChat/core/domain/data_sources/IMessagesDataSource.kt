package br.com.felnanuke.bluetoothChat.core.domain.data_sources

import br.com.felnanuke.bluetoothChat.core.domain.entities.MessageEntity
import br.com.felnanuke.bluetoothChat.core.domain.entities.PairEntity
import br.com.felnanuke.bluetoothChat.core.domain.exceptions.GetMessageError

interface IMessagesDataSource {

    fun add(messageListener: IMessageListener)


    fun remove(messageListener: IMessageListener)

    fun getMessages(
        recipient: PairEntity,
        offset: Long = 0,
        limit: Long = 30,
        onGetMessages: (messages: List<MessageEntity>) -> Unit,
        onError: (exception: GetMessageError) -> Unit
    )

    fun addMessage(message: MessageEntity)

    fun confirmMessageDelivery(message: MessageEntity)

    fun confirmMessageRead(message: MessageEntity)


}