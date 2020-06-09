package tk.alexlopez.sallefy.activities.charts

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.android.synthetic.main.activity_track_evolution.*
import tk.alexlopez.sallefy.R
import tk.alexlopez.sallefy.models.Playback
import tk.alexlopez.sallefy.models.Playlist
import tk.alexlopez.sallefy.models.Track
import tk.alexlopez.sallefy.models.User
import tk.alexlopez.sallefy.network.callback.TrackCallback
import tk.alexlopez.sallefy.network.manager.TrackManager
import java.time.LocalDateTime


class TrackEvolutionActivity : AppCompatActivity(), TrackCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_evolution)

        TrackManager.getInstance(this).getPlaybackByTrackId(this, 8)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun buildChart(playbacks: List<Playback>) {


        val visitors = playbacks.groupBy { item ->
            LocalDateTime.parse(item.time).monthValue
        }.map {
            val total = it.value.sumBy { e -> e.track.likes }
            it.key to total
        }.map { it ->
            Log.e("pene", "Moth: ${it.first}; Total: ${it.second}")
            Entry(it.first.toFloat(), it.second.toFloat())
        }

        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        val months = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dic")
        val formatter: ValueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                return months[value.toInt()]
            }
        }
        xAxis.granularity = 1f
        xAxis.valueFormatter = formatter
        val yAxisRight = lineChart.axisRight
        yAxisRight.isEnabled = false
        val yAxisLeft = lineChart.axisLeft
        yAxisLeft.granularity = 1f

        // Set colors
        val colors = ArrayList<Int>()
        colors.add(Color.rgb(64, 89, 128))
        colors.add(android.graphics.Color.rgb(149, 165, 124))
        colors.add(android.graphics.Color.rgb(217, 184, 162))
        colors.add(Color.rgb(191, 134, 134))
        colors.add(android.graphics.Color.rgb(179, 48, 80))

        val lineDataSet = LineDataSet(visitors, "Reproductions per month")
        lineDataSet.colors = colors
        lineDataSet.valueTextColor = Color.BLACK

        // Set chart
        lineDataSet.valueTextSize = 16f
        val lineData = LineData(lineDataSet)
        lineChart.data = lineData
        lineChart.description.text = "Reproductions per month"
        lineChart.animateY(2000)
        lineChart.invalidate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPlaybackReceived(playbacks: List<Playback>) {
        buildChart(playbacks)
    }

    override fun onPersonalTracksReceived(tracks: MutableList<Track>?) {

    }

    override fun onPlaylistsReceived(playlists: MutableList<Playlist>?) {

    }

    override fun onUserTracksReceived(tracks: MutableList<Track>?) {

    }

    override fun onTracksReceivedByPlaylistId(playlist: Playlist?) {

    }

    override fun onCreateTrack() {

    }

    override fun onNoLikedTrack(response: Boolean?) {

    }

    override fun onFailure(throwable: Throwable?) {

    }

    override fun onNoTracks(throwable: Throwable?) {

    }

    override fun onLikedTrack(response: Boolean?) {

    }

    override fun onUserInfoReceived(body: User?) {

    }

    override fun onTrackSelected(track: Track?) {

    }

    override fun onTrackSelected(index: Int) {

    }

    override fun onPlaylistUpdated(response: Boolean?) {

    }

    override fun onTracksReceived(tracks: MutableList<Track>?) {

    }
}