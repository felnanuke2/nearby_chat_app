package br.com.felnanuke.bluetoothChat.core.domain.entities

import br.com.felnanuke.bluetoothChat.core.domain.enums.MessageContentType

@kotlinx.serialization.Serializable
open class MessageContent(
    open val messageType: MessageContentType,
    val text: String? = null,
    val replyToMessageId: String? = null,
    val attachments: List<AttachmentsEntity>? = null,

) {


    companion object Static {
        fun none(): MessageContent {
            return MessageContent(MessageContentType.NONE)
        }
    }
}
