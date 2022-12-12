package br.com.felnanuke.bluetoothChat.views

class AppRoutes {

    companion object Routes {
        const val ROUTE_PAIRS = "pairs"
        const val ROUTE_HOME = "home"
        const val KEY_PAIR_ID = "pairId"
        const val ROUTE_CHAT = "chat/{$KEY_PAIR_ID}"

    }


}
