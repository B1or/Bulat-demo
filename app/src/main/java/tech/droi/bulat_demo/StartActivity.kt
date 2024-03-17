package tech.droi.bulat_demo

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.View
import androidx.activity.viewModels
import androidx.preference.PreferenceManager
import tech.droi.bulat_demo.MainActivity.Companion.KEY_SERIAL
import tech.droi.bulat_demo.MainActivity.Companion.KEY_VISIBLE
import tech.droi.bulat_demo.MainActivity.Companion.KEY_SERVER
import tech.droi.bulat_demo.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    private val model: StartViewModel by viewModels()
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        model.slide.observe(this) {
            when (it) {
                1 -> {
                    binding.tvSlide.text = getString(R.string.slide_1).toSpanned()
                    binding.bNext.setText(R.string.next)
                }
                2 -> {
                    binding.tvSlide.text = getString(R.string.slide_2).toSpanned()
                    binding.bNext.setText(R.string.next)
                }
                3 -> {
                    binding.tvSlide.text = getString(R.string.slide_3).toSpanned()
                    binding.bNext.setText(R.string.scan)
                }
            }
        }
        if (!sharedPreferences!!.getBoolean(KEY_VISIBLE, true)) {
            val serialNumber: String? = sharedPreferences!!.getString(KEY_SERIAL, null)
            var serverAddress: String? = sharedPreferences!!.getString(KEY_SERVER, null)
            if (serverAddress.isNullOrBlank()) {
                serverAddress = "http://178.20.233.77"
                sharedPreferences?.edit()?.apply {
                    putString(KEY_SERVER, serverAddress)
                    apply()
                }
            }
            if (serialNumber.isNullOrBlank())
                startQr()
            else
                startMain(serialNumber, serverAddress)
        }
    }

    fun onClick(view: View) {
        if (view == binding.bNext) {
            when (model.slide.value) {
                1 -> {
                    model.slide(2)
                }
                2 -> {
                    model.slide(3)
                }
                3 -> {
                    sharedPreferences?.edit()?.apply {
                        putBoolean(KEY_VISIBLE, false)
                        apply()
                    }
                    startQr()
                }
            }
        }
    }

    private fun startQr() {
        val intent = Intent(this, ScanActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startMain(serialNumber: String, serverAddress: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(KEY_SERIAL, serialNumber)
            putExtra(KEY_SERVER, serverAddress)
        }
        startActivity(intent)
        finish()
    }

    private fun String.toSpanned(): Spanned {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    }
}
