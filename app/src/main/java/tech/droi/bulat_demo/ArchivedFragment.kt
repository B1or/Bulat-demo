package tech.droi.bulat_demo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.float
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import tech.droi.bulat_demo.MainActivity.Companion.TAG
import tech.droi.bulat_demo.databinding.FragmentArchivedBinding
import tech.droi.bulat_demo.databinding.HeaderArchivedBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class ArchivedFragment : Fragment() {
    private val model: SharedViewModel by activityViewModels()
    private var _binding: FragmentArchivedBinding? = null
    private lateinit var headerBinding: HeaderArchivedBinding
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentArchivedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pbLoadArchived.visibility = View.GONE
        binding.dpDateArchived.visibility = View.VISIBLE
        binding.bHoursArchived.visibility = View.VISIBLE
        binding.bDaysArchived.visibility = View.VISIBLE
        headerBinding = HeaderArchivedBinding.inflate(layoutInflater)
        val viewHeaderGrid: View = headerBinding.root
        binding.lvGridArchived.addHeaderView(viewHeaderGrid)
        binding.bHoursArchived.setOnClickListener {
            binding.dpDateArchived.visibility = View.GONE
            binding.bHoursArchived.visibility = View.GONE
            binding.bDaysArchived.visibility = View.GONE
            binding.bCalendarArchived.visibility = View.GONE
            binding.pbLoadArchived.visibility = View.VISIBLE
            headerBinding.tvNumberItemArchived.setText(R.string.time)
            headerBinding.tvT1HeaderArchived.visibility = View.INVISIBLE
            headerBinding.tvT2HeaderArchived.visibility = View.INVISIBLE
            headerBinding.tvT3HeaderArchived.visibility = View.INVISIBLE
            headerBinding.tvT4HeaderArchived.visibility = View.INVISIBLE
            loadData(true)
        }
        binding.bDaysArchived.setOnClickListener {
            binding.dpDateArchived.visibility = View.GONE
            binding.bHoursArchived.visibility = View.GONE
            binding.bDaysArchived.visibility = View.GONE
            binding.bCalendarArchived.visibility = View.GONE
            binding.pbLoadArchived.visibility = View.VISIBLE
            headerBinding.tvNumberItemArchived.setText(R.string.date)
            headerBinding.tvT1HeaderArchived.visibility = View.VISIBLE
            headerBinding.tvT2HeaderArchived.visibility = View.VISIBLE
            headerBinding.tvT3HeaderArchived.visibility = View.VISIBLE
            headerBinding.tvT4HeaderArchived.visibility = View.VISIBLE
            loadData(false)
        }
        binding.bCalendarArchived.setOnClickListener {
            binding.bCalendarArchived.visibility = View.GONE
            binding.bGrArchived.visibility = View.GONE
            binding.lvGridArchived.visibility = View.GONE
            binding.bcGraphArchived.visibility = View.GONE
            binding.dpDateArchived.visibility = View.VISIBLE
            binding.bHoursArchived.visibility = View.VISIBLE
            binding.bDaysArchived.visibility = View.VISIBLE
            binding.bGrArchived.text = getString(R.string.graph)
        }
        binding.bGrArchived.setOnClickListener {
            if ((it as Button).text == getString(R.string.graph)) {
                binding.lvGridArchived.visibility = View.GONE
                binding.bcGraphArchived.visibility = View.VISIBLE
                it.text = getString(R.string.table)
            } else {
                binding.bcGraphArchived.visibility = View.GONE
                binding.lvGridArchived.visibility = View.VISIBLE
                it.text = getString(R.string.graph)
            }
        }
    }

    private fun loadData(hours: Boolean) {
        val arrayArchived = arrayListOf<Archived>()
        val currentMonthCalendar = binding.dpDateArchived.month + 1
        val currentYearCalendar = binding.dpDateArchived.year
        val currentDayCalendar = binding.dpDateArchived.dayOfMonth
        val currentDateCalendar: LocalDate = LocalDate.of(currentYearCalendar, currentMonthCalendar, currentDayCalendar)
        val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val currentDateString: String = currentDateCalendar.format(format)
        val deviceId: String = model.serial.value.toString()
        Log.d(TAG, "currentMonthCalendar: $currentMonthCalendar  currentYearCalendar: $currentYearCalendar  currentDayCalendar: $currentDayCalendar  deviceId: $deviceId")
        val queue = Volley.newRequestQueue(requireContext())
        val url = if (hours)
            "http://178.20.233.77/app/get/hours.php?device_id=$deviceId&day=$currentDateString"
        else
            "http://178.20.233.77/app/get/days.php?device_id=$deviceId&year=$currentYearCalendar&month=${"%02d".format(currentMonthCalendar)}"
        Log.d(TAG, "http://178.20.233.77/app/get/hours.php?device_id=$deviceId&day=$currentDateString")
        Log.d(TAG, "http://178.20.233.77/app/get/days.php?device_id=$deviceId&year=$currentYearCalendar&month=${"%02d".format(currentMonthCalendar)}")
        var flagRequest1 = false
        var flagRequest2 = false
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                //<editor-fold desc="расшифровка JSON">
                Log.d(TAG, "response 1: $response")
                if (!response.isNullOrBlank()) {
                    val arrayJson = Json.parseToJsonElement(response).jsonArray
                    if (!arrayJson.isEmpty()) {
                        arrayJson.forEach {
                            val stringJson = Json.decodeFromJsonElement<String>(it)
                            val elementJson = Json.parseToJsonElement(stringJson).jsonObject
                            Log.d(TAG, "elementJson: $elementJson")
                            val dt = elementJson["dt"]?.jsonPrimitive?.content
                            Log.d(TAG, "dt: $dt")
                            val t1: Float = if (hours)
                                elementJson["A+"]?.jsonPrimitive?.float ?: 0f
                            else
                                elementJson["A+T1"]?.jsonPrimitive?.float ?: 0f
                            Log.d(TAG, "t1: $t1")
                            val t2: Float = if (hours)
                                0f
                            else
                                elementJson["A+T2"]?.jsonPrimitive?.float ?: 0f
                            val t3: Float = if (hours)
                                0f
                            else
                                elementJson["A+T3"]?.jsonPrimitive?.float ?: 0f
                            val t4: Float = if (hours)
                                0f
                            else
                                elementJson["A+T4"]?.jsonPrimitive?.float ?: 0f
                            val t0 = t1 + t2 + t3 + t4
//                        if (dt != null && t0 != null && t1 != null && t2 != null && t3 != null) {
                            if (dt != null) {
                                val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                val localDateTime = if (hours)
                                    LocalDateTime.parse(dt, pattern)
                                else
                                    LocalDateTime.parse("$dt 00:00:00", pattern)
                                arrayArchived.add(Archived(localDateTime, t0, t1, t2, t3, t4))
                            }
                        }
                        if (flagRequest2)
                            collectorRequest(arrayArchived, hours, currentMonthCalendar)
                        else
                            flagRequest1 = true
                    } else {
                        Toast.makeText(activity, resources.getString(R.string.no_data), Toast.LENGTH_LONG).show()
                        collectorRequest(arrayArchived, hours, currentMonthCalendar)
                    }
                } else {
                    Toast.makeText(activity, resources.getString(R.string.error_read), Toast.LENGTH_LONG).show()
                    collectorRequest(arrayArchived, hours, currentMonthCalendar)
                }
            },
            {
                Toast.makeText(activity, resources.getString(R.string.error_read), Toast.LENGTH_LONG).show()
                Log.w(TAG, "Error read from internet")
            })
        queue.add(stringRequest)
        var nextDateCalendar: LocalDate = LocalDate.of(currentYearCalendar, currentMonthCalendar, currentDayCalendar)
        nextDateCalendar = if (hours)
            nextDateCalendar.plusDays(1)
        else
            nextDateCalendar.plusMonths(1)
        val nextDateString: String = nextDateCalendar.format(format)
        val nextUrl = if (hours)
            "http://178.20.233.77/app/get/hours.php?device_id=$deviceId&day=$nextDateString"
        else
            "http://178.20.233.77/app/get/days.php?device_id=$deviceId&year=${nextDateCalendar.year}&month=${nextDateCalendar.format(DateTimeFormatter.ofPattern("MM"))}"
        Log.d(TAG, "http://178.20.233.77/app/get/days.php?device_id=$deviceId&year=${nextDateCalendar.year}&month=${nextDateCalendar.format(DateTimeFormatter.ofPattern("MM"))}")
        val nextStringRequest = StringRequest(Request.Method.GET, nextUrl,
            { response ->
                //<editor-fold desc="расшифровка JSON">
                if (!response.isNullOrBlank()) {
                    val arrayJson = Json.parseToJsonElement(response).jsonArray
                    Log.d(TAG, "response 2: $response")
                    if (!arrayJson.isEmpty()) {
                        val stringJson = Json.decodeFromJsonElement<String>(arrayJson.first())
                        val elementJson = Json.parseToJsonElement(stringJson).jsonObject
                        Log.d(TAG, "elementJson: $elementJson")
                        val dt = elementJson["dt"]?.jsonPrimitive?.content
                        Log.d(TAG, "dt: $dt")
                        val t1: Float = if (hours)
                            elementJson["A+"]?.jsonPrimitive?.float ?: 0f
                        else
                            elementJson["A+T1"]?.jsonPrimitive?.float ?: 0f
                        Log.d(TAG, "t1: $t1")
                        val t2: Float = if (hours)
                            0f
                        else
                            elementJson["A+T2"]?.jsonPrimitive?.float ?: 0f
                        val t3: Float = if (hours)
                            0f
                        else
                            elementJson["A+T3"]?.jsonPrimitive?.float ?: 0f
                        val t4: Float = if (hours)
                            0f
                        else
                            elementJson["A+T4"]?.jsonPrimitive?.float ?: 0f
                        val t0 = t1 + t2 + t3 + t4
                        if (dt != null) {
                            val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            val localDateTime = if (hours)
                                LocalDateTime.parse(dt, pattern)
                            else
                                LocalDateTime.parse("$dt 00:00:00", pattern)
                            arrayArchived.add(Archived(localDateTime, t0, t1, t2, t3, t4))
                        }
                    }
                }
                if (flagRequest1)
                    collectorRequest(arrayArchived, hours, currentMonthCalendar)
                else
                    flagRequest2 = true
            },
            {
                Log.w(TAG, "Error read from internet")
                Toast.makeText(activity, resources.getString(R.string.error_read), Toast.LENGTH_LONG).show()
            })
        queue.add(nextStringRequest)
        //</editor-fold>
    }

    private fun collectorRequest(arrayArchived: ArrayList<Archived>, hours: Boolean, currentMonthCalendar: Int) {
        val sortedArrayArchived = arrayArchived.sortedBy { it.dt }
        Log.d(TAG, "sortedArrayArchived: $sortedArrayArchived")
        val arrayGridArchived = if (hours) processHoursData(sortedArrayArchived) else processDaysData(sortedArrayArchived)   // обработка данных
        Log.d(TAG, "arrayGridArchived: $arrayGridArchived")
        //<editor-fold desc="подготовка данных для графика">
        val entries: ArrayList<BarEntry> = arrayListOf()
        arrayGridArchived.forEach {
            Log.d(TAG, "StringGridArchived: $it")
            val x = it.dt.toFloat()
            val y0: Float = it.t0 ?: 0f
            val y1: Float = it.t1 ?: 0f
            val y2: Float = it.t2 ?: 0f
            val y3: Float = it.t3 ?: 0f
            val y4: Float = it.t4 ?: 0f
            val arrayY = if (hours) floatArrayOf(y0) else floatArrayOf(y1, y2, y3, y4)
            entries.add(BarEntry(x, arrayY))
        }
        val dataSet = BarDataSet(entries, getString(R.string.graph))
        dataSet.colors = if (hours) listOf(getColor(requireContext(), R.color.graph1)) else listOf(getColor(requireContext(), R.color.graph1), getColor(requireContext(), R.color.graph2), getColor(requireContext(), R.color.graph3), getColor(requireContext(), R.color.graph4))
        dataSet.stackLabels = if (hours) arrayOf(getString(R.string.tariff_0)) else arrayOf(getString(R.string.tariff_1), getString(R.string.tariff_2), getString(R.string.tariff_3), getString(R.string.tariff_4))
        dataSet.setDrawValues(false)
        val barData = BarData(dataSet)
        binding.bcGraphArchived.data = barData
        binding.bcGraphArchived.description.text = getString(R.string.graph_description)
        //</editor-fold>
        binding.lvGridArchived.adapter = ArchivedAdapter(arrayGridArchived, if (hours) null else currentMonthCalendar)
        binding.pbLoadArchived.visibility = View.GONE
        binding.bCalendarArchived.visibility = View.VISIBLE
        binding.bGrArchived.visibility = View.VISIBLE
        binding.lvGridArchived.visibility = View.VISIBLE
        val day = binding.dpDateArchived.dayOfMonth
        val month = binding.dpDateArchived.month + 1
        val year = binding.dpDateArchived.year
        // binding.bCalendarArchived.text = (if (hours) String.format("%02d.", day) else "") + String.format("%02d.%02d", month, year)
        if (hours)
            binding.bCalendarArchived.text = String.format(getString(R.string.archived_button_label_date), day, month, year)
        else
            binding.bCalendarArchived.text = String.format(getString(R.string.archived_button_label_month), month, year)
        // TODO
    }

    private fun processHoursData(rawData: List<Archived>): ArrayList<GridArchived> {
        val arrayGridArchived = arrayListOf<GridArchived>()
        val dateCalendar = LocalDate.of(binding.dpDateArchived.year, binding.dpDateArchived.month + 1, binding.dpDateArchived.dayOfMonth)
        val t0: Array<Float?> = Array(24) { null }
        var lastHour: Int? = null
        var lastT = 0f
        for (hour in 0..23) {
            val element = rawData.find { it.dt.toLocalDate() == dateCalendar && it.dt.hour == hour }
            if (element != null) {
                if (lastHour != null)
                    t0[lastHour] = element.t0 - lastT
                lastHour = hour
                lastT = element.t0
            }
        }
        val element = rawData.find { it.dt.toLocalDate() == dateCalendar.plusDays(1) }
        if (element != null && lastHour != null)
            t0[lastHour] = element.t0 - lastT
        for (hour in 0..23) {
            if (t0[hour] != null)
                arrayGridArchived.add(GridArchived(hour, t0[hour], null, null, null, null))
        }
        return arrayGridArchived
    }

    private fun processDaysData(rawData: List<Archived>): ArrayList<GridArchived> {
        val arrayGridArchived = arrayListOf<GridArchived>()
        val dateCalendar = LocalDate.of(binding.dpDateArchived.year, binding.dpDateArchived.month + 1, binding.dpDateArchived.dayOfMonth)
        val monthCalendar = YearMonth.of(binding.dpDateArchived.year, binding.dpDateArchived.month + 1)
        val t0: Array<Float?> = Array(monthCalendar.atEndOfMonth().dayOfMonth + 1) { null }
        val t1: Array<Float?> = Array(monthCalendar.atEndOfMonth().dayOfMonth + 1) { null }
        val t2: Array<Float?> = Array(monthCalendar.atEndOfMonth().dayOfMonth + 1) { null }
        val t3: Array<Float?> = Array(monthCalendar.atEndOfMonth().dayOfMonth + 1) { null }
        val t4: Array<Float?> = Array(monthCalendar.atEndOfMonth().dayOfMonth + 1) { null }
        var lastDay: Int? = null
        var lastT0 = 0f
        var lastT1 = 0f
        var lastT2 = 0f
        var lastT3 = 0f
        var lastT4 = 0f
        for (day in 1..monthCalendar.atEndOfMonth().dayOfMonth) {
            val element = rawData.find { it.dt.toLocalDate() == dateCalendar.withDayOfMonth(day) }
            if (element != null) {
                if (lastDay != null) {
                    t0[lastDay] = element.t0 - lastT0
                    t1[lastDay] = element.t1 - lastT1
                    t2[lastDay] = element.t2 - lastT2
                    t3[lastDay] = element.t3 - lastT3
                    t4[lastDay] = element.t4 - lastT4
                }
                lastDay = day
                lastT0 = element.t0
                lastT1 = element.t1
                lastT2 = element.t2
                lastT3 = element.t3
                lastT4 = element.t4
            }
        }
        val nextMonthCalendar = dateCalendar.plusMonths(1)
        val element = rawData.find { it.dt.toLocalDate().year == nextMonthCalendar.year && it.dt.toLocalDate().month == nextMonthCalendar.month }
        if (element != null && lastDay != null) {
            t0[lastDay] = element.t0 - lastT0
            t1[lastDay] = element.t1 - lastT1
            t2[lastDay] = element.t2 - lastT2
            t3[lastDay] = element.t3 - lastT3
            t4[lastDay] = element.t4 - lastT4
        }
        for (day in 1..monthCalendar.atEndOfMonth().dayOfMonth) {
            arrayGridArchived.add(GridArchived(day, t0[day], t1[day], t2[day], t3[day], t4[day]))
        }
        return arrayGridArchived
    }
}