package tk.alexlopez.sallefy.activities.charts

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.ViewPortHandler
import com.tapadoo.alerter.Alerter
import kotlinx.android.synthetic.main.activity_bar_chart.*
import kotlinx.android.synthetic.main.activity_top_tracks.*
import tk.alexlopez.sallefy.R
import tk.alexlopez.sallefy.models.Playlist
import tk.alexlopez.sallefy.models.Track
import tk.alexlopez.sallefy.network.callback.TrackCallback
import tk.alexlopez.sallefy.network.manager.TrackManager


class TopTracksActivity : AppCompatActivity(), TrackCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_tracks)

        TrackManager.getInstance(this).getTopTracks(this, true, 5)
    }

    fun buildChart(tracks: List<Track>) {
        val visitors = arrayListOf<PieEntry>()
        val colors = arrayListOf<Int>()
        var rank = 0f
        val colorTemplates = ColorTemplate.COLORFUL_COLORS + ColorTemplate.MATERIAL_COLORS + ColorTemplate.PASTEL_COLORS + ColorTemplate.COLOR_SKIP + ColorTemplate.LIBERTY_COLORS + ColorTemplate.VORDIPLOM_COLORS
        val totalPastelColors = colorTemplates.count() - 1

        tracks.forEachIndexed { idx, track ->
            rank = track.likes.toFloat()
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
        pieChart.centerText = "Visitors"
        pieChart.animate()
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

    override fun onTracksReceived(tracks: List<Track>) {
        buildChart(tracks)
    }
}