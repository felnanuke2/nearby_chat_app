package br.com.felnanuke.bluetoothChat.core.domain.repositories

import br.com.felnanuke.bluetoothChat.core.domain.data_sources.IPairDataSource
import br.com.felnanuke.bluetoothChat.core.domain.data_sources.IPairsListener

class PairsRepository(private val pairsDataSource: IPairDataSource) {


    fun add(pairListener: IPairsListener) = pairsDataSource.add(pairListener)

    fun remove(pairListener: IPairsListener) = pairsDataSource.remove(pairListener)


}