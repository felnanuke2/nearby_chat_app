package br.com.felnanuke.bluetoothChat.core.infrastructure.realm.models

import br.com.felnanuke.bluetoothChat.core.domain.entities.PairEntity
import br.com.felnanuke.bluetoothChat.core.domain.enums.PairStatus
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmPairModel() : RealmObject {
    @PrimaryKey
    var id: String = RealmUUID.random().toString()
    var name: String = ""
    var address: String = ""
    var status: Int = 0
    var me: Boolean = false


    constructor(pairEntity: PairEntity) : this() {
        this.name = pairEntity.name
        this.address = pairEntity.address
        this.id = pairEntity.id
        this.status = pairEntity.status.ordinal
        this.me = pairEntity.me

    }


    fun asPairEntity(): PairEntity {
        val pair = PairEntity(this.name, this.address, this.id, me = this.me)
        pair.setPairStatus(PairStatus.values()[this.status])
        return pair
    }


}