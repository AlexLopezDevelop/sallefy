package tk.alexlopez.sallefy.activities.charts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_charts.*
import tk.alexlopez.sallefy.R

class ChartsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charts)

        initViews()
    }

    fun initViews() {
        buttonBarChart.setOnClickListener{
            startActivity(Intent(this, TopTracksActivity::class.java))
        }

        buttonPieChart.setOnClickListener{
            startActivity(Intent(this, TopUserTracksActivity::class.java))
        }

        buttonRadarChart.setOnClickListener{
            startActivity(Intent(this, TracksMoreFollowedActivity::class.java))
        }
    }
}