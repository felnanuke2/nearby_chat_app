package br.com.felnanuke.bluetoothChat.core.infrastructure.realm

import br.com.felnanuke.bluetoothChat.core.infrastructure.realm.models.RealmMessageContentModel
import br.com.felnanuke.bluetoothChat.core.infrastructure.realm.models.RealmMessageModel
import br.com.felnanuke.bluetoothChat.core.infrastructure.realm.models.RealmPairModel
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

const val REALM_NAME = "bluetooth_chat.realm3"
const val REALM_SCHEMA_VERSION = 15L

class AppRealm {

    val realm: Realm

    init {
        val config = RealmConfiguration.Builder(
            schema = setOf(
                RealmMessageModel::class, RealmPairModel::class, RealmMessageContentModel::class
            )
        ).name(REALM_NAME).schemaVersion(REALM_SCHEMA_VERSION).build()
        this.realm = Realm.open(config)
    }


}