package br.com.felnanuke.bluetoothChat.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import br.com.felnanuke.bluetoothChat.core.domain.data_sources.IPairsListener
import br.com.felnanuke.bluetoothChat.core.domain.entities.PairEntity
import br.com.felnanuke.bluetoothChat.core.domain.repositories.PairsRepository
import br.com.felnanuke.bluetoothChat.core.domain.repositories.ServerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PairsViewModel @Inject constructor(
    private val pairsRepository: PairsRepository, private val serverRepository: ServerRepository
) : ViewModel() {

    val pairs = mutableStateOf<List<PairEntity>>(mutableListOf())

    private val pairsListener = object : IPairsListener {
        override fun onReceiveList(list: List<PairEntity>) {
            pairs.value = list
        }

    }

    init {
        pairsRepository.add(pairsListener)
        serverRepository.startServer(onError = {}, onConnect = {})
    }


}