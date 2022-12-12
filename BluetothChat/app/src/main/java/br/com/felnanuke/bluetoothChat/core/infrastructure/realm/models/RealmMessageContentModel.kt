package br.com.felnanuke.bluetoothChat.core.infrastructure.realm.models

import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmMessageContentModel() : RealmObject {

    @PrimaryKey
    var id: ObjectId = ObjectId.create()


}