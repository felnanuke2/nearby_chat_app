package br.com.felnanuke.bluetoothChat.core.infrastructure.realm.data_sources

import android.net.wifi.p2p.WifiP2pDevice.CONNECTED
import br.com.felnanuke.bluetoothChat.core.domain.data_sources.IPairDataSource
import br.com.felnanuke.bluetoothChat.core.domain.data_sources.IPairsListener
import br.com.felnanuke.bluetoothChat.core.domain.entities.PairEntity
import br.com.felnanuke.bluetoothChat.core.domain.enums.PairStatus
import br.com.felnanuke.bluetoothChat.core.domain.exceptions.ConnectionException
import br.com.felnanuke.bluetoothChat.core.infrastructure.realm.models.RealmPairModel
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RealmPairDataSource(private val realm: Realm) : IPairDataSource {
    private val listeners = mutableSetOf<IPairsListener>()


    override fun add(pairsListener: IPairsListener) {
        listeners.add(pairsListener)

    }

    override fun remove(pairsListener: IPairsListener) {
        listeners.remove(pairsListener)
    }

    override fun getMe(onSuccess: (PairEntity) -> Unit) {
        onSuccess(PairEntity("Luiz Felipe", "1212", "1212"))
    }

    override fun addPair(
        pair: PairEntity, onSuccess: ((PairEntity) -> Unit)?, onError: ((Exception) -> Unit)?
    ) {
        realm.writeBlocking {
            copyToRealm(RealmPairModel(pair))
        }
        onSuccess?.invoke(pair)
        notifyAllPairsChanged()
    }

    override fun getPairs(
        onError: (exception: ConnectionException) -> Unit,
        onGetPairs: (pairs: List<PairEntity>) -> Unit
    ) {

        realm.writeBlocking {
            val pairs = query<RealmPairModel>().find()
            onGetPairs.invoke(pairs.map { it.asPairEntity() })
        }
    }

    override fun updatePair(
        pair: PairEntity, onSuccess: (PairEntity) -> Unit, onError: (Exception) -> Unit
    ) {
        realm.writeBlocking {
            copyToRealm(RealmPairModel(pair))
        }
        onSuccess.invoke(pair)
    }

    override fun updatePair(
        status: PairStatus,
        pair: PairEntity,
        onSuccess: ((PairEntity) -> Unit)?,
        onError: ((Exception) -> Unit)?
    ) {
        pair.setPairStatus(status)
        realm.writeBlocking {
            copyToRealm(RealmPairModel(pair))
        }
        onSuccess?.invoke(pair)
    }

    override fun deletePair(
        pair: PairEntity, onSuccess: (PairEntity) -> Unit, onError: (Exception) -> Unit
    ) {
        realm.writeBlocking {
            delete(RealmPairModel(pair))
        }
        onSuccess.invoke(pair)
    }

    private fun notifyAllPairsChanged() {
        CoroutineScope(Dispatchers.Main).launch {
            getPairs(onGetPairs = { pairs ->
                listeners.forEach { it.onReceiveList(pairs) }
            }, onError = {})
        }
    }
}