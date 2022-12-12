package br.com.felnanuke.bluetoothChat.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.felnanuke.bluetoothChat.PermissionHandler
import br.com.felnanuke.bluetoothChat.ui.theme.BluetothChatTheme
import br.com.felnanuke.bluetoothChat.views.AppRoutes
import br.com.felnanuke.bluetoothChat.views.ChatScreen
import br.com.felnanuke.bluetoothChat.views.HomeScreen
import br.com.felnanuke.bluetoothChat.views.PairsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        PermissionHandler(this).requestPermissionsForAll()
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            BluetothChatTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController, startDestination = AppRoutes.ROUTE_PAIRS
                    ) {
                        composable(AppRoutes.ROUTE_HOME) {
                            HomeScreen()
                        }

                        composable(AppRoutes.ROUTE_PAIRS) {
                            PairsScreen()
                        }

                        composable(AppRoutes.ROUTE_CHAT) { stack ->
                            ChatScreen(
                                pairId = stack.arguments?.getString(AppRoutes.KEY_PAIR_ID) ?: ""
                            )
                        }
                    }

                }
            }
        }
    }
}
