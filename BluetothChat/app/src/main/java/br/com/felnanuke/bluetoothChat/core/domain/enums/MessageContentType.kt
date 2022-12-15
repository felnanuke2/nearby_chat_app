package br.com.felnanuke.bluetoothChat.core.domain.enums

@kotlinx.serialization.Serializable
enum class MessageContentType {
    TEXT, TEXT_WITH_ATTACHMENT, DELIVERY_CONFIRMATION, READ_CONFIRMATION, NONE
}