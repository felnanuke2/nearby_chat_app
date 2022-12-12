package br.com.felnanuke.bluetoothChat.core.domain.entities

import android.net.Uri
import br.com.felnanuke.bluetoothChat.core.domain.enums.AttachmentType

@kotlinx.serialization.Serializable
data class AttachmentsEntity(
    val type: AttachmentType,
    val fileUri: Uri?,
    var id: String? = null,
    val isCached: Boolean = false,
)