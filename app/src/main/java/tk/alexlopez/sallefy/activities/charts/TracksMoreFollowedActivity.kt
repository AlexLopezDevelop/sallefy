package tk.alexlopez.sallefy.activities.charts

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.tapadoo.alerter.Alerter
import kotlinx.android.synthetic.main.activity_top_tracks.*
import tk.alexlopez.sallefy.R
import tk.alexlopez.sallefy.models.Playback
import tk.alexlopez.sallefy.models.Playlist
import tk.alexlopez.sallefy.models.Track
import tk.alexlopez.sallefy.models.User
import tk.alexlopez.sallefy.network.callback.TrackCallback
import tk.alexlopez.sallefy.network.manager.TrackManager

class TracksMoreFollowedActivity : AppCompatActivity(), TrackCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracks_more_followed)

        TrackManager.getInstance(this).getMoreTracksFollowed(this, true, 10)
    }

    private fun buildChart(tracks: List<Track>) {
        val visitors = arrayListOf<PieEntry>()
        val colors = arrayListOf<Int>()
        var rank = 0f
        val colorTemplates = ColorTemplate.COLORFUL_COLORS + ColorTemplate.MATERIAL_COLORS + ColorTemplate.PASTEL_COLORS + ColorTemplate.COLOR_SKIP + ColorTemplate.LIBERTY_COLORS + ColorTemplate.VORDIPLOM_COLORS
        val totalPastelColors = colorTemplates.count() - 1

        tracks.forEachIndexed { idx, track ->
            rank = track.followers.toFloat()
            visitors.add(PieEntry(rank, track.name))

            val randIdxColor = (0..totalPastelColors).random()
            val color = colorTemplates[randIdxColor]
            colors.add(color)
        }


        val pieDataSet = PieDataSet(visitors, "Top Followed")
        pieDataSet.colors = colors
        pieDataSet.valueTextColor = Color.BLACK

        // Set chart
        pieDataSet.valueTextSize = 16f
        val pieData = PieData(pieDataSet)
        pieChart.data = pieData
        pieChart.description.text = "Tracks more followed"
        pieChart.centerText = "Top Followed"
        pieChart.animate()
        pieChart.invalidate()
    }


    override fun onPlaybackReceived(body: MutableList<Playback>?) {

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

    override fun onUserInfoReceived(body: User?) {

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