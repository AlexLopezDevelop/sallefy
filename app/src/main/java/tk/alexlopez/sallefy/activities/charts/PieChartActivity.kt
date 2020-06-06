package tk.alexlopez.sallefy.activities.charts

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.data.*
import kotlinx.android.synthetic.main.activity_bar_chart.*
import kotlinx.android.synthetic.main.activity_pie_chart.*
import tk.alexlopez.sallefy.R

class PieChartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pie_chart)

        fakeData()
    }

    fun fakeData() {
       /* val visitors = arrayListOf<PieEntry>()
        visitors.add(PieEntry(508f, "2016"))
        visitors.add(PieEntry(600f, "2017"))
        visitors.add(PieEntry(750f, "2018"))
        visitors.add(PieEntry(600f, "2019"))
        visitors.add(PieEntry(670f, "2020"))

        // Set colors
        val colors = ArrayList<Int>()
        colors.add(Color.rgb(64, 89, 128))
        colors.add(android.graphics.Color.rgb(149, 165, 124))
        colors.add(android.graphics.Color.rgb(217, 184, 162))
        colors.add(Color.rgb(191, 134, 134))
        colors.add(android.graphics.Color.rgb(179, 48, 80))

        val pieDataSet = PieDataSet(visitors, "Visitors")
        pieDataSet.colors = colors
        pieDataSet.valueTextColor = Color.BLACK

        // Set chart
        pieDataSet.valueTextSize = 16f
        val pieData = PieData(pieDataSet)
        pieChart.data = pieData
        pieChart.description.text = "Bar Chart Example"
        pieChart.centerText = "Visitors"
        pieChart.animate()*/
    }
}