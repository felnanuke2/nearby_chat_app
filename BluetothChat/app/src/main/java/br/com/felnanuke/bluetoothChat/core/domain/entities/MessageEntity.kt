package br.com.felnanuke.bluetoothChat.core.domain.entities

import br.com.felnanuke.bluetoothChat.core.domain.enums.MessageType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.time.LocalDate
import java.util.Date

@Serializable


open class MessageEntity(
    open val id: String,
    open val createdAt: Long,
    open val deliveredAt: Long? = null,
    open val type: MessageType,
    open val content: MessageContent,
    open val owner: PairEntity,
    open val recipient: PairEntity,
) {
    val sent: Boolean
        get() = deliveredAt != null
}

object MessageSerializable {
    val json = Json {
        serializersModule = SerializersModule {
            ignoreUnknownKeys = true



        }

    }
}