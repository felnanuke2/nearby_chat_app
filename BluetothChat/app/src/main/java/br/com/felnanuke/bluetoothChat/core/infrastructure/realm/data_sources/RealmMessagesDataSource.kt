package br.com.felnanuke.bluetoothChat.core.infrastructure.realm.data_sources

import br.com.felnanuke.bluetoothChat.core.domain.data_sources.IMessageListener
import br.com.felnanuke.bluetoothChat.core.domain.data_sources.IMessagesDataSource
import br.com.felnanuke.bluetoothChat.core.domain.entities.MessageEntity
import br.com.felnanuke.bluetoothChat.core.domain.entities.PairEntity
import br.com.felnanuke.bluetoothChat.core.domain.exceptions.GetMessageError
import io.realm.kotlin.Realm

class RealmMessagesDataSource(private val realm: Realm) : IMessagesDataSource {


    override fun add(messageListener: IMessageListener) {
        TODO("Not yet implemented")
    }

    override fun remove(messageListener: IMessageListener) {
        TODO("Not yet implemented")
    }

    override fun getMessages(
        recipient: PairEntity,
        offset: Long,
        limit: Long,
        onGetMessages: (messages: List<MessageEntity>) -> Unit,
        onError: (exception: GetMessageError) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun addMessage(message: MessageEntity) {
        TODO("Not yet implemented")
    }

    override fun confirmMessageDelivery(message: MessageEntity) {
        TODO("Not yet implemented")
    }

    override fun confirmMessageRead(message: MessageEntity) {
        TODO("Not yet implemented")
    }
}