package br.com.felnanuke.bluetoothChat.core.domain.repositories

import br.com.felnanuke.bluetoothChat.core.domain.data_sources.IPairDataSource
import br.com.felnanuke.bluetoothChat.core.domain.listeners.IPairsListener
import br.com.felnanuke.bluetoothChat.core.domain.entities.PairEntity

class PairsRepository(private val pairsDataSource: IPairDataSource) {

    fun add(pairListener: IPairsListener) = pairsDataSource.add(pairListener)

    fun remove(pairListener: IPairsListener) = pairsDataSource.remove(pairListener)

    fun getPairsSilently() = pairsDataSource.getPairsSilently()

    fun me(onSuccess: (pair: PairEntity) -> Unit) = pairsDataSource.getMe(onSuccess)

    fun save(pair: PairEntity) {
        pairsDataSource.addPair(pair, null, null)

    }


}