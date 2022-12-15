package br.com.felnanuke.bluetoothChat.core.domain.data_sources

import br.com.felnanuke.bluetoothChat.core.domain.entities.PairEntity
import br.com.felnanuke.bluetoothChat.core.domain.enums.PairStatus
import br.com.felnanuke.bluetoothChat.core.domain.exceptions.ConnectionException
import br.com.felnanuke.bluetoothChat.core.domain.listeners.IPairsListener

interface IPairDataSource {


    fun add(pairsListener: IPairsListener)
    fun remove(pairsListener: IPairsListener)

    fun getMe(onSuccess: (PairEntity) -> Unit)

    fun addPair(
        pair: PairEntity,
        onSuccess: ((PairEntity) -> Unit)? = null,
        onError: ((Exception) -> Unit)? = null
    )

    fun getPairs(
        onError: (exception: ConnectionException) -> Unit,
        onGetPairs: (pairs: List<PairEntity>) -> Unit
    )

    fun getPairsSilently()

    fun updatePair(pair: PairEntity, onSuccess: (PairEntity) -> Unit, onError: (Exception) -> Unit)

    fun updatePair(
        status: PairStatus,
        pair: PairEntity,
        onSuccess: ((PairEntity) -> Unit)? = null,
        onError: ((Exception) -> Unit)? = null
    )

    fun deletePair(
        pair: PairEntity,
        onSuccess: ((PairEntity) -> Unit)?,
        onError: ((Exception) -> Unit)?
    )

}