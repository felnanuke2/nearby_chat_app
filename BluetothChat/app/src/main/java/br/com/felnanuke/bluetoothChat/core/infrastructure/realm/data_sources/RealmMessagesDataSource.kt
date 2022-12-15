package br.com.felnanuke.bluetoothChat.core.infrastructure.realm.data_sources

import br.com.felnanuke.bluetoothChat.core.domain.listeners.IMessageListener
import br.com.felnanuke.bluetoothChat.core.domain.data_sources.IMessagesDataSource
import br.com.felnanuke.bluetoothChat.core.domain.entities.MessageEntity
import br.com.felnanuke.bluetoothChat.core.domain.entities.PairEntity
import br.com.felnanuke.bluetoothChat.core.domain.exceptions.GetMessageError
import br.com.felnanuke.bluetoothChat.core.infrastructure.realm.models.RealmMessageModel
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

class RealmMessagesDataSource(private val realm: Realm) : IMessagesDataSource {

    private val listeners = mutableListOf<IMessageListener>()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)


    override fun add(messageListener: IMessageListener) {
        listeners.add(messageListener)
    }

    override fun remove(messageListener: IMessageListener) {
        listeners.remove(messageListener)
    }

    override fun getMessages(
        recipient: PairEntity,
        offset: Int,
        limit: Int,
        onGetMessages: (messages: List<MessageEntity>) -> Unit,
        onError: (exception: GetMessageError) -> Unit
    ) {
        val flow = realm.query<RealmMessageModel>(
            query = "recipient.id == $0 AND content.type IN $1) ",
            arrayOf(recipient.id, arrayOf(0, 1),)
        ).sort("createdAt", sortOrder = Sort.DESCENDING).limit(limit).asFlow().drop(offset)
        coroutineScope.launch {
            flow.collect { result ->
                result.list.map { model ->
                    model.toMessageEntity()

                }
            }
        }
    }

    override fun addMessage(message: MessageEntity) {

        realm.writeBlocking {
            copyToRealm(RealmMessageModel(message), UpdatePolicy.ALL)
        }
        notifyReceiveMessage(message)
    }

    override fun confirmMessageDelivery(message: MessageEntity) {

        realm.writeBlocking {
            copyToRealm(RealmMessageModel(message), UpdatePolicy.ALL)
        }
        notifyReceiveMessage(message)
    }

    override fun confirmMessageRead(message: MessageEntity) {
        realm.writeBlocking {
            copyToRealm(RealmMessageModel(message), UpdatePolicy.ALL)
        }
        notifyReceiveMessage(message)
    }

    private fun notifyReceiveMessage(message: MessageEntity) {
        listeners.forEach { listener ->
            listener.onMessageReceived(message)
        }

    }
}