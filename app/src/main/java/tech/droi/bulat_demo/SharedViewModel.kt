package tech.droi.bulat_demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDateTime

class SharedViewModel: ViewModel() {
    private val _manufacturer = MutableLiveData<String>().apply { value = "" }
    private val _model = MutableLiveData<String>().apply { value = "" }
    private val _serial = MutableLiveData<String>().apply { value = "" }
    private val _hardware = MutableLiveData<String>().apply { value = "" }
    private val _firmware = MutableLiveData<String>().apply { value = "" }
    private val _software = MutableLiveData<String>().apply { value = "" }
    private val _application = MutableLiveData<String>().apply { value = "" }
    private val _dateTime = MutableLiveData<LocalDateTime>().apply { value = LocalDateTime.now() }

    private val _detectVoltage = MutableLiveData<Boolean>().apply { value = false }
    private val _voltage = MutableLiveData<Int>().apply { value = 0 }
    private val _goodFrequency = MutableLiveData<Boolean>().apply { value = false }
    private val _relayState = MutableLiveData<Boolean>().apply { value = false }
    private val _caseState = MutableLiveData<Boolean>().apply { value = false }
    private val _connectState = MutableLiveData<Boolean>().apply { value = false }
    private val _magneticImpact = MutableLiveData<Boolean>().apply { value = false }
    private val _activeTariff = MutableLiveData<Int>().apply { value = -1 }

    private val _activePower = MutableLiveData<Float>().apply { value = 0f }
    private val _reactivePower = MutableLiveData<Float>().apply { value = 0f }
    private val _voltagePhase = MutableLiveData<Float>().apply { value = 0f }
    private val _currentPhase = MutableLiveData<Float>().apply { value = 0f }
    private val _currentNeutral = MutableLiveData<Float>().apply { value = 0f }
    private val _frequency = MutableLiveData<Float>().apply { value = 0f }

    private val _activeForwardSum = MutableLiveData<Float>().apply { value = 0f }
    private val _reactiveForwardSum = MutableLiveData<Float>().apply { value = 0f }
    private val _activeReverseSum = MutableLiveData<Float>().apply { value = 0f }
    private val _reactiveReverseSum = MutableLiveData<Float>().apply { value = 0f }
    private val _activeForwardT1 = MutableLiveData<Float>().apply { value = 0f }
    private val _reactiveForwardT1 = MutableLiveData<Float>().apply { value = 0f }
    private val _activeReverseT1 = MutableLiveData<Float>().apply { value = 0f }
    private val _reactiveReverseT1 = MutableLiveData<Float>().apply { value = 0f }
    private val _activeForwardT2 = MutableLiveData<Float>().apply { value = 0f }
    private val _reactiveForwardT2 = MutableLiveData<Float>().apply { value = 0f }
    private val _activeReverseT2 = MutableLiveData<Float>().apply { value = 0f }
    private val _reactiveReverseT2 = MutableLiveData<Float>().apply { value = 0f }
    private val _activeForwardT3 = MutableLiveData<Float>().apply { value = 0f }
    private val _reactiveForwardT3 = MutableLiveData<Float>().apply { value = 0f }
    private val _activeReverseT3 = MutableLiveData<Float>().apply { value = 0f }
    private val _reactiveReverseT3 = MutableLiveData<Float>().apply { value = 0f }
    private val _activeForwardT4 = MutableLiveData<Float>().apply { value = 0f }
    private val _reactiveForwardT4 = MutableLiveData<Float>().apply { value = 0f }
    private val _activeReverseT4 = MutableLiveData<Float>().apply { value = 0f }
    private val _reactiveReverseT4 = MutableLiveData<Float>().apply { value = 0f }

    val manufacturer: LiveData<String> = _manufacturer
    val model: LiveData<String> = _model
    val serial: LiveData<String> = _serial
    val hardware: LiveData<String> = _hardware
    val firmware: LiveData<String> = _firmware
    val software: LiveData<String> = _software
    val application: LiveData<String> = _application
    val dateTime: LiveData<LocalDateTime> = _dateTime

    val detectVoltage: LiveData<Boolean> = _detectVoltage
    val voltage: LiveData<Int> = _voltage
    val goodFrequency: LiveData<Boolean> = _goodFrequency
    val relayState: LiveData<Boolean> = _relayState
    val caseState: LiveData<Boolean> = _caseState
    val connectState: LiveData<Boolean> = _connectState
    val magneticImpact: LiveData<Boolean> = _magneticImpact
    val activeTariff: LiveData<Int> = _activeTariff

    val activePower: LiveData<Float> = _activePower
    val reactivePower: LiveData<Float> = _reactivePower
    val voltagePhase: LiveData<Float> = _voltagePhase
    val currentPhase: LiveData<Float> = _currentPhase
    val currentNeutral: LiveData<Float> = _currentNeutral
    val frequency: LiveData<Float> = _frequency

    val activeForwardSum: LiveData<Float> = _activeForwardSum
    val reactiveForwardSum: LiveData<Float> = _reactiveForwardSum
    val activeReverseSum: LiveData<Float> = _activeReverseSum
    val reactiveReverseSum: LiveData<Float> = _reactiveReverseSum
    val activeForwardT1: LiveData<Float> = _activeForwardT1
    val reactiveForwardT1: LiveData<Float> = _reactiveForwardT1
    val activeReverseT1: LiveData<Float> = _activeReverseT1
    val reactiveReverseT1: LiveData<Float> = _reactiveReverseT1
    val activeForwardT2: LiveData<Float> = _activeForwardT2
    val reactiveForwardT2: LiveData<Float> = _reactiveForwardT2
    val activeReverseT2: LiveData<Float> = _activeReverseT2
    val reactiveReverseT2: LiveData<Float> = _reactiveReverseT2
    val activeForwardT3: LiveData<Float> = _activeForwardT3
    val reactiveForwardT3: LiveData<Float> = _reactiveForwardT3
    val activeReverseT3: LiveData<Float> = _activeReverseT3
    val reactiveReverseT3: LiveData<Float> = _reactiveReverseT3
    val activeForwardT4: LiveData<Float> = _activeForwardT4
    val reactiveForwardT4: LiveData<Float> = _reactiveForwardT4
    val activeReverseT4: LiveData<Float> = _activeReverseT4
    val reactiveReverseT4: LiveData<Float> = _reactiveReverseT4

    fun manufacturer(manufacturer: String) { _manufacturer.value = manufacturer }
    fun model(model: String) { _model.value = model }
    fun serial(serial: String) { _serial.value = serial }
    fun hardware(hardware: String) { _hardware.value = hardware }
    fun firmware(firmware: String) { _firmware.value = firmware }
    fun software(software: String) { _software.value = software }
    fun application(application: String) { _application.value = application }
    fun dateTime(dateTime: LocalDateTime) { _dateTime.value = dateTime }
    fun plusDateTime() { _dateTime.postValue(_dateTime.value?.plusSeconds(1)) }

    fun detectVoltage(detectVoltage: Boolean) { _detectVoltage.value = detectVoltage }
    fun voltage(voltage: Int) { _voltage.value = voltage }
    fun goodFrequency(goodFrequency: Boolean) { _goodFrequency.value = goodFrequency }
    fun relayState(relayState: Boolean) { _relayState.value = relayState }
    fun caseState(caseState: Boolean) { _caseState.value = caseState }
    fun connectState(connectState: Boolean) { _connectState.value = connectState }
    fun magneticImpact(magneticImpact: Boolean) { _magneticImpact.value = magneticImpact }
    fun activeTariff(tariff: Int) { _activeTariff.value = tariff }

    fun activePower(power: Float) { _activePower.value = power }
    fun reactivePower(power: Float) { _reactivePower.value = power }
    fun voltagePhase(voltage: Float) { _voltagePhase.value = voltage }
    fun currentPhase(current: Float) { _currentPhase.value = current }
    fun currentNeutral(current: Float) { _currentNeutral.value = current }
    fun frequency(frequency: Float) { _frequency.value = frequency }

    fun activeForwardSum(energy: Float) { _activeForwardSum.value = energy }
    fun reactiveForwardSum(energy: Float) { _reactiveForwardSum.value = energy }
    fun activeReverseSum(energy: Float) { _activeReverseSum.value = energy }
    fun reactiveReverseSum(energy: Float) { _reactiveReverseSum.value = energy }
    fun activeForwardT1(energy: Float) { _activeForwardT1.value = energy }
    fun reactiveForwardT1(energy: Float) { _reactiveForwardT1.value = energy }
    fun activeReverseT1(energy: Float) { _activeReverseT1.value = energy }
    fun reactiveReverseT1(energy: Float) { _reactiveReverseT1.value = energy }
    fun activeForwardT2(energy: Float) { _activeForwardT2.value = energy }
    fun reactiveForwardT2(energy: Float) { _reactiveForwardT2.value = energy }
    fun activeReverseT2(energy: Float) { _activeReverseT2.value = energy }
    fun reactiveReverseT2(energy: Float) { _reactiveReverseT2.value = energy }
    fun activeForwardT3(energy: Float) { _activeForwardT3.value = energy }
    fun reactiveForwardT3(energy: Float) { _reactiveForwardT3.value = energy }
    fun activeReverseT3(energy: Float) { _activeReverseT3.value = energy }
    fun reactiveReverseT3(energy: Float) { _reactiveReverseT3.value = energy }
    fun activeForwardT4(energy: Float) { _activeForwardT4.value = energy }
    fun reactiveForwardT4(energy: Float) { _reactiveForwardT4.value = energy }
    fun activeReverseT4(energy: Float) { _activeReverseT4.value = energy }
    fun reactiveReverseT4(energy: Float) { _reactiveReverseT4.value = energy }
}
