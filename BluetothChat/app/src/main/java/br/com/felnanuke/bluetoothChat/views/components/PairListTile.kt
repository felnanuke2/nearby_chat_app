package br.com.felnanuke.bluetoothChat.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.felnanuke.bluetoothChat.R
import br.com.felnanuke.bluetoothChat.core.domain.entities.PairEntity
import br.com.felnanuke.bluetoothChat.core.domain.enums.PairStatus
import coil.compose.AsyncImage

@Composable
fun PairListTile(pairEntity: PairEntity, modifier: Modifier = Modifier.padding(0.dp) ) {
    Row(modifier = modifier) {
        AsyncImage(

            model = pairEntity.profileImage,
            modifier = Modifier
                .size(58.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .border(
                    if (pairEntity.status == PairStatus.Connected) 2.dp else 0.dp,
                    Color.Green,
                    CircleShape
                )
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(8.dp),

            contentDescription = null,
            placeholder = painterResource(
                id = R.drawable.ic_baseline_person_24,

                )
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            Text(text = pairEntity.name)

        }


    }


}

@Preview
@Composable
fun PairListTilePreview() {
    PairListTile(PairEntity(
        "Jose Da Silva Junior",
        "123",
        "123",
    ).apply { setPairStatus(PairStatus.Connected) })
}