package tk.alexlopez.sallefy.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_tracks_list.*
import tk.alexlopez.sallefy.R
import tk.alexlopez.sallefy.adapters.MyPlaylistsAdapter
import tk.alexlopez.sallefy.models.Playlist
import tk.alexlopez.sallefy.models.Track
import tk.alexlopez.sallefy.network.callback.TrackCallback
import tk.alexlopez.sallefy.network.manager.TrackManager

class MyPlaylistsActivity : AppCompatActivity(), TrackCallback{

    private lateinit var mPlaylists: ArrayList<Playlist>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_playlists)
        initViews()
    }

    fun initViews() {
        val manager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter = MyPlaylistsAdapter(this, null)
        recycler_view.layoutManager = manager
        recycler_view.adapter = adapter

        TrackManager.getInstance(this).getAllMyPlaylists(this)
    }

    override fun onPersonalTracksReceived(tracks: MutableList<Track>?) {

    }

    override fun onPlaylistsReceived(playlists: List<Playlist>?) {
        mPlaylists = playlists as ArrayList<Playlist>
        val adapter = MyPlaylistsAdapter(this, mPlaylists)
        recycler_view?.adapter = adapter
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

    override fun onTrackSelected(track: Track?) {

    }

    override fun onTrackSelected(index: Int) {

    }

    override fun onPlaylistUpdated(response: Boolean?) {

    }

    override fun onTracksReceived(tracks: MutableList<Track>?) {

    }
}