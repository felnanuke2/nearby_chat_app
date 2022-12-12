package br.com.felnanuke.bluetoothChat.core.domain.enums

@kotlinx.serialization.Serializable
enum class PairStatus {
    Typing, Connected, Disconnected, Connecting, Discovered, ConnectionFailed
}