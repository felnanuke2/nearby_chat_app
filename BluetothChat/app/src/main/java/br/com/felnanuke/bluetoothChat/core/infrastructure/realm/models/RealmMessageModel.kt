package br.com.felnanuke.bluetoothChat.core.infrastructure.realm.models

import br.com.felnanuke.bluetoothChat.core.domain.entities.MessageEntity
import br.com.felnanuke.bluetoothChat.core.domain.enums.MessageContentType
import br.com.felnanuke.bluetoothChat.core.domain.enums.MessageType
import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.*

class RealmMessageModel() : RealmObject {

    @PrimaryKey
    var id: ObjectId = ObjectId.create()
    var createdAt: Long = 0
    var deliveredAt: Long? = null
    var type: Int = 1
    var content: RealmMessageContentModel? = null
    var owner: RealmPairModel? = null
    var recipient: RealmPairModel? = null


    constructor(messageEntity: MessageEntity) : this() {
        this.id = ObjectId.create()
        this.createdAt = messageEntity.createdAt
        this.deliveredAt = messageEntity.deliveredAt
        this.type = messageEntity.type.ordinal
        this.owner = RealmPairModel(messageEntity.owner)
        this.recipient = RealmPairModel(messageEntity.recipient)


        when (messageEntity.content.messageType) {
            MessageContentType.TEXT -> {
                this.content = RealmMessageContentModel(messageEntity.content)
            }
            MessageContentType.TEXT_WITH_ATTACHMENT -> TODO()
            MessageContentType.DELIVERY_CONFIRMATION -> TODO()
            MessageContentType.READ_CONFIRMATION -> TODO()
            MessageContentType.NONE -> RealmMessageContentModel()
        }
    }

    fun toMessageEntity(): MessageEntity {

        return MessageEntity(
            this.id.toString(),
            this.createdAt,
            this.deliveredAt,
            MessageType.values()[this.type],
            this.content!!.toMessageContent(),
            this.owner?.asPairEntity()!!,
            this.recipient?.asPairEntity()!!
        )
    }


}
