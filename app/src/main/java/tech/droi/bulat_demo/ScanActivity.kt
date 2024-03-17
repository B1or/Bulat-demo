package tech.droi.bulat_demo

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import tech.droi.bulat_demo.MainActivity.Companion.TAG
import tech.droi.bulat_demo.databinding.ActivityScanBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlinx.serialization.json.*
import tech.droi.bulat_demo.MainActivity.Companion.KEY_PK
import tech.droi.bulat_demo.MainActivity.Companion.KEY_SERIAL
import tech.droi.bulat_demo.MainActivity.Companion.KEY_SERVER

class ScanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanBinding
    private val model: ScanViewModel by viewModels()
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            startCamera()
        } else {
            Snackbar.make(binding.clScan, R.string.permissions_refuse, Snackbar.LENGTH_LONG).setAction(R.string.close) {
                finish()
            }.show()
        }
    }
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService
    private var sharedPreferences: SharedPreferences? = null
    private var serverAddress: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        intent.let {
            serverAddress = it.getStringExtra(MainActivity.KEY_SERVER)
            model.serverAddress(serverAddress)
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        model.serialNumber.observe(this) {
            binding.etScan.setText(it)
        }
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED ->
                startCamera()
            shouldShowRequestPermissionRationale() -> {
                showInContextUI()
            }
            else ->
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onStart() {
        super.onStart()
        if (model.serialNumber.value.isNullOrBlank())
            model.serialNumber(sharedPreferences?.getString(KEY_SERIAL, null))
        if (model.pairingKey.value.isNullOrBlank())
            model.pairingKey(sharedPreferences?.getString(KEY_PK, null))
        if (model.serverAddress.value.isNullOrBlank())
            model.serverAddress(sharedPreferences?.getString(KEY_SERVER, null))
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    fun onClickScan(view: View) {
        when (view) {
            binding.buttonScan -> {
                if (binding.etScan.text.toString() != model.serialNumber.value) {
                    model.serialNumber(binding.etScan.text.toString())
                    model.pairingKey(null)
                }
                sharedPreferences?.edit()?.apply {
                    putString(KEY_SERIAL, model.serialNumber.value)
                    putString(KEY_PK, model.pairingKey.value)
                    apply()
                }
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra(KEY_SERIAL, model.serialNumber.value)
                    putExtra(KEY_PK, model.pairingKey.value)
                    putExtra(KEY_SERVER, model.serverAddress.value)
                }
                startActivity(intent)
                finish()
            }
        }
    }

    private fun shouldShowRequestPermissionRationale(): Boolean = false

    private fun showInContextUI() {
        val dialog: AlertDialog = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(R.string.camera)
                setMessage(R.string.permission_message_qr)
                setPositiveButton(R.string.request) { _, _ ->
                    requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
                setNegativeButton(R.string.refuse) { _, _ ->
                    finish()
                }
            }
            builder.create()
        }
        dialog.show()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder()
                .build()
            var lastDisplayValue: String? = null
            val imageAnalysis = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor) { imageProxy ->
                        @ExperimentalGetImage
                        val mediaImage = imageProxy.image
                        @ExperimentalGetImage
                        if (mediaImage != null) {
                            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                            val options = BarcodeScannerOptions.Builder()
                                .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                                .build()
                            val scanner = BarcodeScanning.getClient(options)
                            scanner.process(image)
                                .addOnSuccessListener { barcodes ->
                                    for (barcode in barcodes) {
                                        if (barcode.valueType == Barcode.TYPE_TEXT) {
                                            val displayValue = barcode.displayValue
                                            if (displayValue != null) {
                                                if (displayValue != lastDisplayValue) {
                                                    lastDisplayValue = displayValue
                                                    try {
                                                        val element = Json.parseToJsonElement(displayValue).jsonArray[0].jsonObject
                                                        Log.d(TAG, element.toString())
                                                        model.serialNumber(element["sn"]?.jsonPrimitive?.content)
                                                        model.pairingKey(element["sn"]?.jsonPrimitive?.content)
                                                    } catch (e: IllegalArgumentException) {
                                                        Log.e(TAG, e.toString())
                                                        Snackbar.make(
                                                            binding.clScan,
                                                            R.string.inappropriate_qr_code,
                                                            Snackbar.LENGTH_LONG
                                                        ).show()
                                                    }
                                                }
                                            }
                                        } else Snackbar.make(binding.clScan, R.string.inappropriate_qr_code, Snackbar.LENGTH_LONG).show()
                                    }
                                }
                                .addOnFailureListener { e ->
                                    Log.e(TAG, "Error image analyze: $e")
                                }
                                .addOnCompleteListener {
                                    // mediaImage.close()
                                    imageProxy.close()
                                }
                        }
                    }
                }
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture, imageAnalysis)
            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }
}
