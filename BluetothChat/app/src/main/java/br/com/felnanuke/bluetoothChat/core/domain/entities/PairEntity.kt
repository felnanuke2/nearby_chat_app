package br.com.felnanuke.bluetoothChat.core.domain.entities

import br.com.felnanuke.bluetoothChat.core.domain.enums.PairStatus

@kotlinx.serialization.Serializable
data class PairEntity(
    var name: String,
    var address: String,
    val id: String,
    val profileImage: String? = null,
    val me: Boolean = false
) {
    private var _status: PairStatus = PairStatus.Discovered

    var status: PairStatus
        get() = _status
        internal set(value) {
            _status = value
        }


    fun setPairStatus(value: PairStatus) {
        status = value
    }


    override fun equals(other: Any?): Boolean {
        return super.equals(other) || ((other is PairEntity) && (other.id == this.id))
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}