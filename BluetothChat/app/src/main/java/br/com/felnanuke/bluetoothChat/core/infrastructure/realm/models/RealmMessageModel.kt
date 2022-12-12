package br.com.felnanuke.bluetoothChat.core.infrastructure.realm.models

import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.*

class RealmMessageModel() : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.create()
}
