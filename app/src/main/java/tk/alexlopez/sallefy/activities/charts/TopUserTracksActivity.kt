package tk.alexlopez.sallefy.activities.charts

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.tapadoo.alerter.Alerter
import kotlinx.android.synthetic.main.activity_top_tracks.*
import kotlinx.android.synthetic.main.activity_top_user_tracks.*
import kotlinx.android.synthetic.main.activity_top_user_tracks.pieChart

import tk.alexlopez.sallefy.R
import tk.alexlopez.sallefy.models.Playlist
import tk.alexlopez.sallefy.models.Track
import tk.alexlopez.sallefy.network.callback.TrackCallback
import tk.alexlopez.sallefy.network.manager.TrackManager

class TopUserTracksActivity : AppCompatActivity(), TrackCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_user_tracks)

        TrackManager.getInstance(this).getOwnTracks(this)
    }

    private fun buildChart(tracks: List<Track>) {
        val visitors = arrayListOf<PieEntry>()
        val colors = arrayListOf<Int>()
        var rank = 0f
        val colorTemplates = ColorTemplate.COLORFUL_COLORS + ColorTemplate.MATERIAL_COLORS + ColorTemplate.PASTEL_COLORS + ColorTemplate.COLOR_SKIP + ColorTemplate.LIBERTY_COLORS + ColorTemplate.VORDIPLOM_COLORS
        val totalPastelColors = colorTemplates.count() - 1

        val topTracksPlayed = tracks.sortedBy { it.plays }

        topTracksPlayed.forEachIndexed { idx, track ->
            rank = track.plays.toFloat()
            visitors.add(PieEntry(rank, track.name))

            val randIdxColor = (0..totalPastelColors).random()
            val color = colorTemplates[randIdxColor]
            colors.add(color)
        }


        val pieDataSet = PieDataSet(visitors, "Visitors")
        pieDataSet.colors = colors
        pieDataSet.valueTextColor = Color.BLACK

        // Set chart
        pieDataSet.valueTextSize = 16f
        val pieData = PieData(pieDataSet)
        pieChart.data = pieData
        pieChart.description.text = "Bar Chart Example"
        pieChart.centerText = "Most played"
        pieChart.animate()
        pieChart.invalidate()
    }

    override fun onPersonalTracksReceived(tracks: List<Track>) {
        buildChart(tracks)
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
        Alerter.create(this)
                .setTitle("Error")
                .setBackgroundColorRes(R.color.colorError)
                .setText(throwable.toString())
                .show()
    }


    override fun onLikedTrack(response: Boolean?) {

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