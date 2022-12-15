package br.com.felnanuke.bluetoothChat.core.infrastructure.realm.models

import br.com.felnanuke.bluetoothChat.core.domain.entities.MessageContent
import br.com.felnanuke.bluetoothChat.core.domain.enums.MessageContentType
import io.realm.kotlin.types.EmbeddedRealmObject

class RealmMessageContentModel() : EmbeddedRealmObject {
    var type: Int = MessageContentType.NONE.ordinal

    var text: String? = null

    var replyToMessageId: String? = null

    var attachmentUri: String? = null

    constructor(messageContentText: MessageContent) : this() {

        this.type = messageContentText.messageType.ordinal
        this.text = messageContentText.text
        this.replyToMessageId = messageContentText.replyToMessageId

    }

    fun toMessageContent(): MessageContent {
        var content: MessageContent
        when (MessageContentType.values()[type]) {
            MessageContentType.TEXT -> {
                content = MessageContent(MessageContentType.TEXT, text, replyToMessageId)
            }
            MessageContentType.TEXT_WITH_ATTACHMENT -> TODO()
            MessageContentType.DELIVERY_CONFIRMATION -> TODO()
            MessageContentType.READ_CONFIRMATION -> TODO()
            MessageContentType.NONE -> {
                content = MessageContent.none()
            }
        }

        return content

    }


}