package br.com.felnanuke.bluetoothChat.views

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.felnanuke.bluetoothChat.view_models.PairsViewModel
import br.com.felnanuke.bluetoothChat.views.components.PairListTile


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PairsScreen(pairsViewModel: PairsViewModel = hiltViewModel()) {


    Scaffold {
        LazyColumn {
            items(pairsViewModel.pairs.value.count()) {
                PairListTile(pairEntity = pairsViewModel.pairs.value[it])
            }
        }
    }
}