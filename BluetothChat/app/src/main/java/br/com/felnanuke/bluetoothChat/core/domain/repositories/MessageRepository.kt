package br.com.felnanuke.bluetoothChat.core.domain.repositories

import br.com.felnanuke.bluetoothChat.core.domain.data_sources.IMessagesDataSource
import br.com.felnanuke.bluetoothChat.core.domain.entities.MessageEntity
import br.com.felnanuke.bluetoothChat.core.domain.entities.PairEntity
import br.com.felnanuke.bluetoothChat.core.domain.exceptions.GetMessageError

class MessageRepository(private val messageDataSource: IMessagesDataSource) {


    fun getMessages(
        recipient: PairEntity,
        limit: Long = 30,
        offset: Long = 0,
        onGetMessages: (List<MessageEntity>) -> Unit,
        onError: (exception: GetMessageError) -> Unit,
    ) = messageDataSource.getMessages(
        recipient = recipient,
        limit = limit,
        offset = offset,
        onGetMessages = onGetMessages,
        onError = onError
    )

}