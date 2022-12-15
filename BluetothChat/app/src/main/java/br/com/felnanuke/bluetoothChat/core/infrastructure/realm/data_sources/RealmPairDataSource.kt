package br.com.felnanuke.bluetoothChat.core.infrastructure.realm.data_sources

import br.com.felnanuke.bluetoothChat.core.domain.data_sources.IPairDataSource
import br.com.felnanuke.bluetoothChat.core.domain.listeners.IPairsListener
import br.com.felnanuke.bluetoothChat.core.domain.entities.PairEntity
import br.com.felnanuke.bluetoothChat.core.domain.enums.PairStatus
import br.com.felnanuke.bluetoothChat.core.domain.exceptions.ConnectionException
import br.com.felnanuke.bluetoothChat.core.infrastructure.realm.models.RealmPairModel
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.find
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RealmPairDataSource(private val realm: Realm) : IPairDataSource {

    private val pairs = mutableSetOf<PairEntity>()

    private val listeners = mutableSetOf<IPairsListener>()


    override fun add(pairsListener: IPairsListener) {
        listeners.add(pairsListener)

    }

    override fun remove(pairsListener: IPairsListener) {
        listeners.remove(pairsListener)
    }

    override fun getMe(onSuccess: (PairEntity) -> Unit) {
        realm.query<RealmPairModel>("me == true").find().let { result ->
            if (result.isEmpty()) {
                realm.writeBlocking {
                    val newPair = RealmPairModel().apply {
                        me = true
                    }
                    copyToRealm(newPair, UpdatePolicy.ALL)
                    onSuccess(newPair.asPairEntity())
                }
            } else {
                onSuccess(result.first().asPairEntity())
            }
        }

    }

    override fun addPair(
        pair: PairEntity, onSuccess: ((PairEntity) -> Unit)?, onError: ((Exception) -> Unit)?
    ) {
        pairs.add(pair)
//        realm.writeBlocking {
//            copyToRealm(RealmPairModel(pair), UpdatePolicy.ALL)
//        }
//        onSuccess?.invoke(pair)
        notifyAllPairsChanged()
    }

    override fun getPairs(
        onError: (exception: ConnectionException) -> Unit,
        onGetPairs: (pairs: List<PairEntity>) -> Unit
    ) {

        realm.writeBlocking {
            val pairs = query<RealmPairModel>("me == false").find()
            onGetPairs.invoke(pairs.map { it.asPairEntity() })
        }
    }

    override fun getPairsSilently() {

        realm.query<RealmPairModel>("me == false").find { realmResult ->
            val pairs = realmResult.map { realmPairModel ->
                realmPairModel.asPairEntity()
            }
            notifyAllListeners(pairs)
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
            copyToRealm(RealmPairModel(pair), UpdatePolicy.ALL)
        }
        onSuccess?.invoke(pair)
    }

    override fun deletePair(
        pair: PairEntity, onSuccess: ((PairEntity) -> Unit)?, onError: ((Exception) -> Unit)?
    ) {
        realm.writeBlocking {
            delete(RealmPairModel(pair))
        }
        onSuccess?.invoke(pair)
    }

    private fun notifyAllListeners(pairs: List<PairEntity>) {
        listeners.forEach { it.onReceiveList(pairs) }
    }

    private fun notifyAllPairsChanged() {
        CoroutineScope(Dispatchers.Main).launch {
            listeners.forEach { it.onReceiveList(pairs.toList()) }
        }
    }
}