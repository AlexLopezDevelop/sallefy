package tk.alexlopez.sallefy.activities.charts

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_bar_chart.*
import tk.alexlopez.sallefy.R

class BarChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_chart)

        fakeData()
    }

    fun fakeData() {
        /*val visitors = arrayListOf<BarEntry>()
        visitors.add(BarEntry(2014f, 420f))
        visitors.add(BarEntry(2015f, 475f))
        visitors.add(BarEntry(2016f, 508f))
        visitors.add(BarEntry(2017f, 660f))
        visitors.add(BarEntry(2018f, 550f))
        visitors.add(BarEntry(2019f, 630f))
        visitors.add(BarEntry(2020f, 470f))

        // Set colors
        val colors = ArrayList<Int>()
        colors.add(Color.rgb(64, 89, 128))
        colors.add(android.graphics.Color.rgb(149, 165, 124))
        colors.add(android.graphics.Color.rgb(217, 184, 162))
        colors.add(Color.rgb(191, 134, 134))
        colors.add(android.graphics.Color.rgb(179, 48, 80))

        val barDataSet = BarDataSet(visitors, "Visitors")
        barDataSet.colors = colors
        barDataSet.valueTextColor = Color.BLACK

        // Set chart
        barDataSet.valueTextSize = 16f
        val barData = BarData(barDataSet)
        barChart.setFitBars(true)
        barChart.data = barData
        barChart.description.text = "Bar Chart Example"
        barChart.animateY(2000)*/
    }
}