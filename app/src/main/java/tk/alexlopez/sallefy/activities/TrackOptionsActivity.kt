package tk.alexlopez.sallefy.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tapadoo.alerter.Alerter
import kotlinx.android.synthetic.main.activity_track_options.*
import tk.alexlopez.sallefy.R
import tk.alexlopez.sallefy.databinding.ActivityTrackOptionsBinding
import tk.alexlopez.sallefy.models.Playlist
import tk.alexlopez.sallefy.models.Track
import tk.alexlopez.sallefy.network.callback.TrackCallback
import tk.alexlopez.sallefy.network.manager.TrackManager
import java.lang.Exception

class TrackOptionsActivity : AppCompatActivity(), TrackCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        data
    }

    private fun initViews() {

        val binding = DataBindingUtil.setContentView<ActivityTrackOptionsBinding>(this, R.layout.activity_track_options)
        val model = ViewModelProvider.NewInstanceFactory().create(TrackOptionsViewModel::class.java)
        binding.model = model

        model.errorMessage.observe(this, Observer {
            val message = getString(it)
            Alerter.create(this)
                    .setTitle("Error")
                    .setText(message)
                    .show()

        })
        binding.lifecycleOwner = this

        model.load(TrackManager.getInstance(application), 8)

        model.userLikeTrack.observe(this, Observer {

        })

        add_song_to_playlist_option.setOnClickListener {
            val intent = Intent(applicationContext, MyPlaylistsActivity::class.java)
            startActivityForResult(intent, 1)
        }

        show_artist_option.setOnClickListener {

            val test = this.intent
            val bundle = test.extras
            bundle?.let { b ->
                (b.getSerializable("trackData") as Track?)?.let { track ->

                    val intent = Intent(this, TracksListActivity::class.java)
                    intent.putExtra("artistId", track.user.id)
                    this.startActivity(intent)
                }
            }

            val intent = Intent(applicationContext, ArtistProfileActivity::class.java)
            startActivity(intent)
        }

        share_layout.setOnClickListener() {
            fun onClick(v: View?) {
                //
            }
        }

    }

    private var playlistId: Int = 0

    private val data: Unit
        get() {
            val intent = this.intent
            val bundle = intent.extras
            bundle?.let { b ->
                (b.getSerializable("trackData") as Track?)?.let { track ->

                    if (track.thumbnail != null) {
                        Glide.with(this)
                                .asBitmap()
                                .load(track.thumbnail)
                                .into(track_cover!!)
                    }
                    track_title?.text = track.name
                    track_author?.text = track.userLogin

                }
            }

        }


    private val track: Track
        get() {
            var result = Track()
            try {
                intent.extras?.let { e ->
                    (e.getSerializable("trackData") as Track?)?.let { t -> result = t }
                }
            } catch (ex: Exception) {
                Log.e("TrackOptionsActivity", ex.toString())
            }
            return result
        }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === 1) {
            if (resultCode === Activity.RESULT_OK) {
                //val value = data?.extras?.getString("idPlaylist")
                data?.let {
                    it.extras?.let { e ->
                        playlistId = e.getInt("idPlaylist")
                        Log.d("E", playlistId.toString())
                        TrackManager.getInstance(this).getAllMyPlaylists(this)
                    }
                }

            }
            if (resultCode === Activity.RESULT_CANCELED) {
                // Playlist no selected
            }
        }
    }

    override fun onPersonalTracksReceived(tracks: MutableList<Track>?) {
        TODO("Not yet implemented")
    }

    override fun onUserTracksReceived(tracks: MutableList<Track>?) {
        TODO("Not yet implemented")
    }

    override fun onPlaylistsReceived(playlists: List<Playlist>) {
        try {
            if (playlistId > 0 && existsPlaylist(playlists)) {
                val playlist = playlists.first { it.id == playlistId }
                if (!existsPlaylist(playlist) && track.id > 0) {
                    playlist.tracks.add(track)
                    TrackManager.getInstance(this).updatePlaylist(this, playlist)
                }
            }
        } catch (ex: Exception) {

        }
    }

    private fun existsPlaylist(playlists: List<Playlist>) =
            playlists != null && playlists.filter { it.id == playlistId }.count() > 0

    private fun existsPlaylist(playlist: Playlist) =
            playlist != null
                    && playlist.tracks != null
                    && playlist.tracks.indexOfFirst { it.id == playlistId } > -1

    override fun onTracksReceivedByPlaylistId(playlist: Playlist?) {
        TODO("Not yet implemented")
    }

    override fun onCreateTrack() {
        TODO("Not yet implemented")
    }

    override fun onNoLikedTrack(response: Boolean?) {
        TODO("Not yet implemented")
    }

    override fun onFailure(throwable: Throwable?) {
        TODO("Not yet implemented")
    }

    override fun onNoTracks(throwable: Throwable?) {
        TODO("Not yet implemented")
    }

    override fun onLikedTrack(response: Boolean?) {
        TODO("Not yet implemented")
    }

    override fun onTrackSelected(track: Track?) {
        TODO("Not yet implemented")
    }

    override fun onTrackSelected(index: Int) {
        TODO("Not yet implemented")
    }

    override fun onPlaylistUpdated(response: Boolean?) {
        var message = "Vaya paso algo heavy"
        try {
            response?.let {
                if (it) {
                    message = "Se actualizo correctamente"
                }
            }
        } catch (ex: Exception) {
            message = ex.toString()
        }
        Alerter.create(this)
                .setTitle("Playlist")
                .setText(message)
                .show()
    }

    override fun onTracksReceived(tracks: MutableList<Track>?) {
        TODO("Not yet implemented")
    }
}
