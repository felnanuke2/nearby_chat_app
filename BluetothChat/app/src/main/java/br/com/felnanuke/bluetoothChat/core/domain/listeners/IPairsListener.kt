package br.com.felnanuke.bluetoothChat.core.domain.listeners

import br.com.felnanuke.bluetoothChat.core.domain.entities.PairEntity

interface IPairsListener {
    fun onReceiveList(list: List<PairEntity>)
}