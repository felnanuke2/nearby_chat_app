package br.com.felnanuke.bluetoothChat.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.felnanuke.bluetoothChat.view_models.ProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileView(
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val localFocus = LocalFocusManager.current
    Column {
        TextField(value = profileViewModel.name,
            modifier = Modifier.fillMaxWidth(),
            textStyle = androidx.compose.material3.MaterialTheme.typography.bodySmall,
            onValueChange = { newText ->
                profileViewModel.name = newText

            })
        Spacer(Modifier.weight(1f))
        Button(onClick = {

            profileViewModel.saveChanges()
            localFocus.clearFocus()
        }) {
            Text(text = "Save")

        }
    }

}


@Preview
@Composable
fun ProfileViewPreview() {
    ProfileView()
}