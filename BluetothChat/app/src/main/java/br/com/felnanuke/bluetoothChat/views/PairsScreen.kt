package br.com.felnanuke.bluetoothChat.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.magnifier
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.com.felnanuke.bluetoothChat.view_models.PairsViewModel
import br.com.felnanuke.bluetoothChat.views.components.PairListTile


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PairsScreen(
    pairsViewModel: PairsViewModel = hiltViewModel()
) {
    LazyColumn(
        Modifier.padding(
            16.dp
        )
    ) {
        items(pairsViewModel.pairs.value.count()) {
            PairListTile(
                pairEntity = pairsViewModel.pairs.value[it],
                modifier = Modifier.clickable {
                    pairsViewModel.sendHello(pairsViewModel.pairs.value[it])
                })
        }
    }
}