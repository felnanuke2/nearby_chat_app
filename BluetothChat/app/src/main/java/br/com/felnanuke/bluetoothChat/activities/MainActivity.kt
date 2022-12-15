package br.com.felnanuke.bluetoothChat.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomAppBar
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.felnanuke.bluetoothChat.PermissionHandler
import br.com.felnanuke.bluetoothChat.ui.theme.BluetoothChatTheme
import br.com.felnanuke.bluetoothChat.views.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var navController: NavHostController

    var pageIndex by mutableStateOf(0)

    @OptIn(ExperimentalPagerApi::class)
    private lateinit var pagerState: PagerState


    @OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        PermissionHandler(this).requestPermissionsForAll()
        super.onCreate(savedInstanceState)
        setContent {

            navController = rememberNavController()
            pagerState = rememberPagerState()
            val scope = rememberCoroutineScope()


            LaunchedEffect(key1 = pagerState.currentPage) {

            }
            BluetoothChatTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    LaunchedEffect(key1 = pageIndex) {
                        while (true) {
                            pageIndex = pagerState.currentPage
                            delay(100)
                        }
                    }


                    Scaffold(content = {
                        Column {
                            HorizontalPager(
                                count = 2, state = pagerState, modifier = Modifier.weight(1f)

                            ) {
                                when (it) {
                                    0 -> {
                                        PairsScreen()
                                    }
                                    1 -> {
                                        ProfileView()
                                    }
                                }
                            }

                            BottomAppBar {
                                IconButton(onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(0)
                                    }

                                }) {
                                    Icon(
                                        imageVector = androidx.compose.ui.graphics.vector.ImageVector.vectorResource(
                                            id = br.com.felnanuke.bluetoothChat.R.drawable.ic_baseline_supervised_user_circle_24
                                        ),
                                        contentDescription = "Contacts",
                                        tint = if (pageIndex == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                                androidx.compose.foundation.layout.Spacer(
                                    modifier = Modifier.weight(
                                        1f
                                    )
                                )
                                IconButton(onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(1)
                                    }
                                }) {
                                    Icon(
                                        imageVector = androidx.compose.material.icons.Icons.Default.Settings,
                                        contentDescription = "Settings",
                                        tint = if (pageIndex == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }

                            }

                        }


                    })


                }
            }
        }
    }
}
