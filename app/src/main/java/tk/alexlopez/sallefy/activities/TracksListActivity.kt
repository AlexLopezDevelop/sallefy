package tk.alexlopez.sallefy.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_tracks_list.*
import tk.alexlopez.sallefy.R
import tk.alexlopez.sallefy.adapters.TrackListAdapter
import tk.alexlopez.sallefy.models.Playlist
import tk.alexlopez.sallefy.models.Track
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
        val adapter = TrackListAdapter(this, null)
        recycler_view.layoutManager = manager
        recycler_view.adapter = adapter
    }

    //TrackManager.getInstance(this).getAllTracks(this);
    // TODO: Error
    private val data: Unit
        private get() {
            val intent = intent
            val id = intent.getIntExtra("id", -1)
            if (id != -1) {
                TrackManager.getInstance(this).getAllTracksByPlaylistId(id, this)
            } else {
                //TrackManager.getInstance(this).getAllTracks(this);
                // TODO: Error
            }
            mTracks = ArrayList()
        }

    override fun onTracksReceived(tracks: List<Track>) {
        mTracks = tracks as ArrayList<Track>
        val adapter = TrackListAdapter(this, mTracks)
        recycler_view?.adapter = adapter
    }

    override fun onTracksReceivedByPlaylistId(playlist: Playlist) {
        // set title
        playlist_title?.text = playlist.name
        mTracks = playlist.tracks as ArrayList<Track>
        val adapter = TrackListAdapter(this, mTracks)
        recycler_view?.adapter = adapter
    }

    override fun onNoTracks(throwable: Throwable) {}
    override fun onLikedTrack(response: Boolean?) {
        TODO("Not yet implemented")
    }

    override fun onPersonalTracksReceived(tracks: List<Track>) {}
    override fun onPlaylistsReceived(playlists: MutableList<Playlist>?) {
        TODO("Not yet implemented")
    }

    override fun onUserTracksReceived(tracks: List<Track>) {}
    override fun onCreateTrack() {}
    override fun onNoLikedTrack(response: Boolean?) {
        TODO("Not yet implemented")
    }

    override fun onTrackSelected(track: Track) {}
    override fun onTrackSelected(index: Int) {}
    override fun onPlaylistUpdated(response: Boolean?) {
        TODO("Not yet implemented")
    }

    override fun onFailure(throwable: Throwable) {}
}

