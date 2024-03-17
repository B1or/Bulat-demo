package tech.droi.bulat_demo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScanViewModel: ViewModel() {
    val serialNumber = MutableLiveData<String?>()
    val pairingKey = MutableLiveData<String?>()
    val serverAddress = MutableLiveData<String?>()

    fun editTextChanged(newText: String) {
        if (newText != serialNumber.value) {
            serialNumber.value = newText
            pairingKey.value = null
        }
    }

    fun serialNumber(serialNumber: String?) {
        this.serialNumber.value = serialNumber
    }

    fun pairingKey(pairingKey: String?) {
        this.pairingKey.value = pairingKey
    }

    fun serverAddress(serverAddress: String?) {
        this.serverAddress.value = serverAddress
    }
}
