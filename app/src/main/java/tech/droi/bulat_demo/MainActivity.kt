package tech.droi.bulat_demo

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import tech.droi.bulat_demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val viewModel: SharedViewModel by viewModels()
    private var sharedPreferences: SharedPreferences? = null
    private var serialNumber: String? = null
    private var serverAddress: String? = null
    private lateinit var menuNav: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //<editor-fold desc="Navigation drawer">
        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        menuNav = navView.menu

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_passport,
                R.id.nav_measured,
                R.id.nav_consumed_current,
                R.id.nav_archived
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        //</editor-fold>
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_quit -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onStart() {
        super.onStart()
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        viewModel.application(packageInfo.versionName)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        sharedPreferences!!.registerOnSharedPreferenceChangeListener(this)
        serialNumber = sharedPreferences!!.getString(KEY_SERIAL, null)
        serialNumber?.let { viewModel.serial(it) }
        serverAddress = sharedPreferences!!.getString(KEY_SERVER, null)
        getFromServer1()
    }

    override fun onStop() {
        super.onStop()
        sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        Log.d(TAG, "SharedPreferences saved $sharedPreferences $key")
    }

    private fun getFromServer1() {
        val deviceId: String = serialNumber?: ""
        val url1 = "${serverAddress?.trim()}/app/get/passport.php?device_id=${deviceId.trim()}"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET, url1,
            { response ->
                if (!response.isNullOrBlank()) {
                    Log.d(TAG, "response: $response")
                    try {
                        val elementJson = Json.parseToJsonElement(response).jsonObject
                        elementJson["fw_ver"]?.jsonPrimitive?.content?.let { viewModel.firmware(it) }
                        viewModel.manufacturer(resources.getString(R.string.manufacturer))
                        viewModel.model(resources.getString(R.string.model))
                        val url2 = "${serverAddress?.trim()}/app/get/now.php?device_id=${deviceId.trim()}"
                        getFromServer2(url2)
                    } catch (e: IllegalArgumentException) {
                        Log.e(TAG, "error parsing JSON: $e")
                        getError(resources.getString(R.string.error_parsing))
                    }
                } else {
                    getError(resources.getString(R.string.error_response))
                }
            },
            {
                getError(resources.getString(R.string.error_read))
            })
        queue.add(stringRequest)
    }

    private fun getFromServer2(url: String) {
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                if (!response.isNullOrBlank()) {
                    try {
                        val elementJson = Json.parseToJsonElement(response).jsonObject
                        elementJson["A+T1"]?.jsonPrimitive?.content?.let { viewModel.activeForwardT1(it.toFloat()) }
                        elementJson["R+T1"]?.jsonPrimitive?.content?.let { viewModel.reactiveForwardT1(it.toFloat()) }
                        elementJson["A-T1"]?.jsonPrimitive?.content?.let { viewModel.activeReverseT1(it.toFloat()) }
                        elementJson["R-T1"]?.jsonPrimitive?.content?.let { viewModel.reactiveReverseT1(it.toFloat()) }
                        elementJson["A+T2"]?.jsonPrimitive?.content?.let { viewModel.activeForwardT2(it.toFloat()) }
                        elementJson["R+T2"]?.jsonPrimitive?.content?.let { viewModel.reactiveForwardT2(it.toFloat()) }
                        elementJson["A-T2"]?.jsonPrimitive?.content?.let { viewModel.activeReverseT2(it.toFloat()) }
                        elementJson["R-T2"]?.jsonPrimitive?.content?.let { viewModel.reactiveReverseT2(it.toFloat()) }
                        elementJson["A+T3"]?.jsonPrimitive?.content?.let { viewModel.activeForwardT3(it.toFloat()) }
                        elementJson["R+T3"]?.jsonPrimitive?.content?.let { viewModel.reactiveForwardT3(it.toFloat()) }
                        elementJson["A-T3"]?.jsonPrimitive?.content?.let { viewModel.activeReverseT3(it.toFloat()) }
                        elementJson["R-T3"]?.jsonPrimitive?.content?.let { viewModel.reactiveReverseT3(it.toFloat()) }
                        elementJson["A+T4"]?.jsonPrimitive?.content?.let { viewModel.activeForwardT4(it.toFloat()) }
                        elementJson["R+T4"]?.jsonPrimitive?.content?.let { viewModel.reactiveForwardT4(it.toFloat()) }
                        elementJson["A-T4"]?.jsonPrimitive?.content?.let { viewModel.activeReverseT4(it.toFloat()) }
                        elementJson["R-T4"]?.jsonPrimitive?.content?.let { viewModel.reactiveReverseT4(it.toFloat()) }
                        val af1 = viewModel.activeForwardT1.value?:0f
                        val af2 = viewModel.activeForwardT2.value?:0f
                        val af3 = viewModel.activeForwardT3.value?:0f
                        val af4 = viewModel.activeForwardT4.value?:0f
                        viewModel.activeForwardSum(af1 + af2 + af3 + af4)
                        val rf1 = viewModel.reactiveForwardT1.value?:0f
                        val rf2 = viewModel.reactiveForwardT2.value?:0f
                        val rf3 = viewModel.reactiveForwardT3.value?:0f
                        val rf4 = viewModel.reactiveForwardT4.value?:0f
                        viewModel.reactiveForwardSum(rf1 + rf2 + rf3 + rf4)
                        val ar1 = viewModel.activeReverseT1.value?:0f
                        val ar2 = viewModel.activeReverseT2.value?:0f
                        val ar3 = viewModel.activeReverseT3.value?:0f
                        val ar4 = viewModel.activeReverseT4.value?:0f
                        viewModel.activeReverseSum(ar1 + ar2 + ar3 + ar4)
                        val rr1 = viewModel.reactiveReverseT1.value?:0f
                        val rr2 = viewModel.reactiveReverseT2.value?:0f
                        val rr3 = viewModel.reactiveReverseT3.value?:0f
                        val rr4 = viewModel.reactiveReverseT4.value?:0f
                        viewModel.reactiveForwardSum(rr1 + rr2 + rr3 + rr4)
                        enableItemsMenu()
                    } catch (e: IllegalArgumentException) {
                        Log.e(TAG, "error parsing JSON: $e")
                        getError(resources.getString(R.string.error_parsing))
                    }
                } else
                    getError(resources.getString(R.string.error_response))
            },
            {
                getError(resources.getString(R.string.error_read))
            })
        queue.add(stringRequest)
    }

    private fun getError(errorString: String) {
        Toast.makeText(this, resources.getString(R.string.error_internet_) + " " + errorString, Toast.LENGTH_LONG).show()
        viewModel.manufacturer("")
        viewModel.model("")
        viewModel.firmware("")
        disableItemsMenu()
    }

    private fun enableItemsMenu() {
        val navItem1: MenuItem = menuNav.findItem(R.id.nav_measured)
        navItem1.isEnabled = true
        val navItem2: MenuItem = menuNav.findItem(R.id.nav_consumed_current)
        navItem2.isEnabled = true
        val navItem3: MenuItem = menuNav.findItem(R.id.nav_archived)
        navItem3.isEnabled = true
    }

    private fun disableItemsMenu() {
        val navItem1: MenuItem = menuNav.findItem(R.id.nav_measured)
        navItem1.isEnabled = false
        val navItem2: MenuItem = menuNav.findItem(R.id.nav_consumed_current)
        navItem2.isEnabled = false
        val navItem3: MenuItem = menuNav.findItem(R.id.nav_archived)
        navItem3.isEnabled = false
    }

    companion object {
        const val TAG = "BulatDebug"
        const val KEY_VISIBLE = "visible"
        const val KEY_SERIAL = "serial"
        const val KEY_PK = "pk"
        const val KEY_SERVER = "server"

        const val FORMAT = "%08.1f"
        const val FORMAT2 = "%8.3f"
        const val FORMAT3 = "%6.3f"
    }
}
