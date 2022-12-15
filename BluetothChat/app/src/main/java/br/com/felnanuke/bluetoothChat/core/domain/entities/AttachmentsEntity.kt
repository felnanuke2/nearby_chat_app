package br.com.felnanuke.bluetoothChat.core.domain.entities

import android.net.Uri
import br.com.felnanuke.bluetoothChat.core.domain.enums.AttachmentType
import java.util.UUID

@kotlinx.serialization.Serializable
data class AttachmentsEntity(
    val type: AttachmentType,
    val bytes: ByteArray,
    var id: String = UUID.randomUUID().toString(),
)