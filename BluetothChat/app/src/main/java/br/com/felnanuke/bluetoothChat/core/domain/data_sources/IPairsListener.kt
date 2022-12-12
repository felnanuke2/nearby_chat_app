package br.com.felnanuke.bluetoothChat.core.domain.data_sources

import br.com.felnanuke.bluetoothChat.core.domain.entities.PairEntity

interface IPairsListener {
    fun onReceiveList(list: List<PairEntity>)
}