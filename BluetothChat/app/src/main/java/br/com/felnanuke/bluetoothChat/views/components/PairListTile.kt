package br.com.felnanuke.bluetoothChat.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.felnanuke.bluetoothChat.R
import br.com.felnanuke.bluetoothChat.core.domain.entities.PairEntity
import coil.compose.AsyncImage

@Composable
fun PairListTile(pairEntity: PairEntity) {
    Row {
        AsyncImage(
            model = pairEntity.profileImage,
            modifier = Modifier
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onBackground),
            contentDescription = null,
            placeholder = painterResource(
                id = R.drawable.ic_baseline_person_24,

                )
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(text = pairEntity.name)
        }
    }


}

@Preview
@Composable
fun PairListTilePreview() {
    PairListTile(PairEntity("Jose Da Silva Junior", "123", "123"))
}