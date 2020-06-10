package tk.alexlopez.sallefy.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_tracks_list.*
import tk.alexlopez.sallefy.R
import tk.alexlopez.sallefy.adapters.TrackListAdapter
import tk.alexlopez.sallefy.models.Playback
import tk.alexlopez.sallefy.models.Playlist
import tk.alexlopez.sallefy.models.Track
import tk.alexlopez.sallefy.models.User
import tk.alexlopez.sallefy.network.callback.TrackCallback
import tk.alexlopez.sallefy.network.manager.TrackManager
import java.util.*

class TracksListActivity : AppCompatActivity(), TrackCallback {

    private lateinit var mTracks: ArrayList<Track>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracks_list)
        iniViews()
        data
    }

    private fun iniViews() {
        val manager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter = TrackListAdapter(this, null, playListId)
        recycler_view.layoutManager = manager
        recycler_view.adapter = adapter
    }

    private val playListId: Int
        get() = intent.getIntExtra("id", -1)



    private val data: Unit
        get() {
            val intent = intent
            val id = intent.getIntExtra("id", -1)
            if (id != -1) {
                TrackManager.getInstance(this).getAllTracksByPlaylistId(id, this)
            }
            mTracks = ArrayList()
        }

    override fun onTracksReceived(tracks: List<Track>) {
        mTracks = tracks as ArrayList<Track>
        val adapter = TrackListAdapter(this, mTracks, playListId)
        recycler_view?.adapter = adapter
    }

    override fun onTracksReceivedByPlaylistId(playlist: Playlist) {
        // set title
        playlist_title?.text = playlist.name
        mTracks = playlist.tracks as ArrayList<Track>
        val adapter = TrackListAdapter(this, mTracks, playListId)
        recycler_view?.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        data
    }

    override fun onNoTracks(throwable: Throwable) {}
    override fun onLikedTrack(response: Boolean?) {

    }

    override fun onUserInfoReceived(body: User?) {

    }

    override fun onPlaybackReceived(body: MutableList<Playback>?) {

    }

    override fun onPersonalTracksReceived(tracks: List<Track>) {}
    override fun onPlaylistsReceived(playlists: MutableList<Playlist>?) {

    }

    override fun onUserTracksReceived(tracks: List<Track>) {}
    override fun onCreateTrack() {}
    override fun onNoLikedTrack(response: Boolean?) {

    }

    override fun onTrackSelected(track: Track) {}
    override fun onTrackSelected(index: Int) {}
    override fun onPlaylistUpdated(response: Boolean?) {

    }

    override fun onFailure(throwable: Throwable) {}
}

