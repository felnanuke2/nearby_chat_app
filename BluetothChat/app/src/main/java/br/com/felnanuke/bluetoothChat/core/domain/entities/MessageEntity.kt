package br.com.felnanuke.bluetoothChat.core.domain.entities

import br.com.felnanuke.bluetoothChat.core.domain.enums.MessageType
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
open class MessageEntity(
    open val id: String,
    open val createdAt: Date,
    open val deliveredAt: Date? = null,
    open val type: MessageType,
    open val content: MessageContent,
    open val owner: PairEntity,
    open val recipient: PairEntity,
    open val isMine: () -> Boolean = { false },

    ) {


    val sent: Boolean
        get() = deliveredAt != null


}