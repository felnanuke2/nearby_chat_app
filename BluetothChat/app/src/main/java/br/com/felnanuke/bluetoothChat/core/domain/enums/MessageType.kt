package br.com.felnanuke.bluetoothChat.core.domain.enums

@kotlinx.serialization.Serializable
enum class MessageType {
    DELIVERY_CONFIRMATION_RECEIVED, MESSAGE_RECEIVED, READ_CONFIRMATION_RECEIVED,
}