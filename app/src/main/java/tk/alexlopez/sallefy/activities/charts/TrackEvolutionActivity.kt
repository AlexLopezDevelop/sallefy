package tk.alexlopez.sallefy.activities.charts

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.activity_track_evolution.*
import tk.alexlopez.sallefy.R
import tk.alexlopez.sallefy.models.Playback
import tk.alexlopez.sallefy.models.Playlist
import tk.alexlopez.sallefy.models.Track
import tk.alexlopez.sallefy.models.User
import tk.alexlopez.sallefy.network.callback.TrackCallback
import tk.alexlopez.sallefy.network.manager.TrackManager

class TrackEvolutionActivity : AppCompatActivity(), TrackCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_evolution)

        TrackManager.getInstance(this).getPlaybackByTrackId(this, 8)
    }

    private fun buildChart(playbacks: List<Playback>) {
        val visitors = arrayListOf<BarEntry>()
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

        val barDataSet = BarDataSet(visitors, "Reproductions per month")
        barDataSet.colors = colors
        barDataSet.valueTextColor = Color.BLACK

        // Set chart
        barDataSet.valueTextSize = 16f
        val barData = BarData(barDataSet)
        barChart.setFitBars(true)
        barChart.data = barData
        barChart.description.text = "Reproductions per month"
        barChart.animateY(2000)
        barChart.invalidate()
    }

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