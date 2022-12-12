package br.com.felnanuke.bluetoothChat.core.domain.entities

import br.com.felnanuke.bluetoothChat.core.domain.enums.MessageContentType

@kotlinx.serialization.Serializable
 class MessageContentTextAndAttachments(
    val text: String, val replyToMessageId: String?, val attachments: List<AttachmentsEntity>,
) : MessageContent(
    MessageContentType.TEXT_WITH_ATTACHMENT
)
