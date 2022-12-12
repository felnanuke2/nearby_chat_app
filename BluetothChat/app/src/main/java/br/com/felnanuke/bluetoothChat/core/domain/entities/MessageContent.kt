package br.com.felnanuke.bluetoothChat.core.domain.entities

import br.com.felnanuke.bluetoothChat.core.domain.enums.MessageContentType

@kotlinx.serialization.Serializable
open  class MessageContent(open val type: MessageContentType)