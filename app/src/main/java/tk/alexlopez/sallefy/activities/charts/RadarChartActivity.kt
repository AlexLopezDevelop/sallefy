package tk.alexlopez.sallefy.activities.charts

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.activity_bar_chart.*
import kotlinx.android.synthetic.main.activity_radar_chart.*
import tk.alexlopez.sallefy.R

class RadarChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radar_chart)

        initView()
    }

    fun initView() {
        val visitors = arrayListOf<RadarEntry>()
        visitors.add(RadarEntry(420f))
        visitors.add(RadarEntry(475f))
        visitors.add(RadarEntry(508f))
        visitors.add(RadarEntry(660f))
        visitors.add(RadarEntry(550f))
        visitors.add(RadarEntry(630f))
        visitors.add(RadarEntry(470f))

        // Set colors
        val colors = ArrayList<Int>()
        colors.add(Color.rgb(64, 89, 128))
        colors.add(android.graphics.Color.rgb(149, 165, 124))
        colors.add(android.graphics.Color.rgb(217, 184, 162))
        colors.add(Color.rgb(191, 134, 134))
        colors.add(android.graphics.Color.rgb(179, 48, 80))

        val radarDataSet = RadarDataSet(visitors, "Visitors")
        radarDataSet.colors = colors
        radarDataSet.valueTextColor = Color.BLACK

        // Set chart
        radarDataSet.valueTextSize = 16f
        val radarData = RadarData(radarDataSet)

        val labels = arrayOf("2014", "2015", "2016", "2017", "2018", "2019", "2020")

        val xAxis = radarChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)

        radarChart.description.text = "Bar Chart Example"
        radarChart.data = radarData
        radarChart.animate()
    }
}