package br.com.felnanuke.bluetoothChat.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import br.com.felnanuke.bluetoothChat.core.domain.entities.PairEntity
import br.com.felnanuke.bluetoothChat.core.domain.repositories.PairsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val pairRepository: PairsRepository) :
    ViewModel() {
    private lateinit var pair: PairEntity
    var name: String by mutableStateOf("Name")


    init {
        pairRepository.me { pair ->
            this.pair = pair
            name = pair.name
        }
    }


    fun saveChanges() {
        pair.name = name
        pairRepository.save(pair)
    }
}