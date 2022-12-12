package br.com.felnanuke.bluetoothChat.core.domain.exceptions

data class GetMessageError(override val message: String) : Exception()