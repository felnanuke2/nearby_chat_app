package br.com.felnanuke.bluetoothChat.view_models

import android.app.Application
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import br.com.felnanuke.bluetoothChat.core.domain.entities.MessageContent
import br.com.felnanuke.bluetoothChat.core.domain.listeners.IMessageListener
import br.com.felnanuke.bluetoothChat.core.domain.listeners.IPairsListener
import br.com.felnanuke.bluetoothChat.core.domain.entities.MessageEntity
import br.com.felnanuke.bluetoothChat.core.domain.entities.PairEntity
import br.com.felnanuke.bluetoothChat.core.domain.enums.MessageContentType
import br.com.felnanuke.bluetoothChat.core.domain.enums.MessageType
import br.com.felnanuke.bluetoothChat.core.domain.repositories.MessageRepository
import br.com.felnanuke.bluetoothChat.core.domain.repositories.PairsRepository
import br.com.felnanuke.bluetoothChat.core.domain.repositories.ServerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PairsViewModel @Inject constructor(
    private val pairsRepository: PairsRepository,
    private val serverRepository: ServerRepository,
    private val messageRepository: MessageRepository,
    private val application: Application,
) : ViewModel(), IMessageListener {

    val pairs = mutableStateOf<List<PairEntity>>(mutableListOf())

    private val pairsListener = object : IPairsListener {
        override fun onReceiveList(list: List<PairEntity>) {
            pairs.value = list
        }
    }

    init {
        messageRepository.add(this)
        pairsRepository.add(pairsListener)
        serverRepository.startServer(onError = {}, onConnect = {})


    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun sendHello(pairEntity: PairEntity) {
        pairsRepository.me { me ->
            serverRepository.sendMessage(message = MessageEntity(
                UUID.randomUUID().toString(),
                content = MessageContent(
                    MessageContentType.TEXT,
                    "Hello World from another device",
                ),
                recipient = pairEntity,
                owner = me,
                createdAt = System.currentTimeMillis(),
                deliveredAt = null,
                type = MessageType.MESSAGE_RECEIVED

            ), {

            }, {

            })
        }

    }


    override fun onMessageReceived(message: MessageEntity) {
        Toast.makeText(application, "message received", Toast.LENGTH_SHORT).show()
        if (message.content.messageType == MessageContentType.TEXT) {
            Toast.makeText(
                application, message.content.text, Toast.LENGTH_SHORT
            ).show()
        }


    }

    override fun onMessageUpdateStatus(message: MessageEntity) {
        TODO("Not yet implemented")
    }


}