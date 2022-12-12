package br.com.felnanuke.bluetoothChat.core.domain.entities

import br.com.felnanuke.bluetoothChat.core.domain.enums.MessageContentType

@kotlinx.serialization.Serializable
open class MessageContentText(val text: String, val replyToMessageId: String?) : MessageContent(
    MessageContentType.TEXT
) {

}
