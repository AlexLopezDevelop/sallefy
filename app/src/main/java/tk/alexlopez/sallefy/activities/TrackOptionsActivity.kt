package tk.alexlopez.sallefy.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tapadoo.alerter.Alerter
import com.tapadoo.alerter.OnHideAlertListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_track_options.*
import tk.alexlopez.sallefy.R
import tk.alexlopez.sallefy.databinding.ActivityTrackOptionsBinding
import tk.alexlopez.sallefy.models.Playback
import tk.alexlopez.sallefy.models.Playlist
import tk.alexlopez.sallefy.models.Track
import tk.alexlopez.sallefy.models.User
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
            Log.d("LOG", "boolean: $it")
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

                    val intent = Intent(this, ArtistProfileActivity::class.java)
                    intent.putExtra("user", track.user as Parcelable)
                    this.startActivity(intent)
                }
            }
        }
        // compartir laurl ente otras apps com sm
        share_layout.setOnClickListener() {
            val sendIntent: Intent = Intent().apply {
                val complete = "track/23"; // asogargh api ~~ track user playlist
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "http://sallefy.eu-west-3.elasticbeanstalk.com/"+complete)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, "Share")
            startActivity(shareIntent)
        }


        delete_layout.setOnClickListener() {
            val playListId = intent.getIntExtra("playListId", -1)
            TrackManager.getInstance(this).delete(playListId, track.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (!it) {
                            throw Exception("Un error inesperado ocurrio, intentalo mas tarde")
                        }

                        Alerter.create(this)
                                .setTitle("Done")
                                .setBackgroundColorRes(R.color.av_green)
                                .setText("Eliminada correctamente")
                                .setOnHideListener(OnHideAlertListener { finish() })
                                .show()
                    }, { e ->
                        Alerter.create(this)
                                .setTitle("Error")
                                .setBackgroundColorRes(R.color.colorError)
                                .setText(e.toString())
                                .show()
                    })
        }

        detail_cancel_button.setOnClickListener() {
            finish()
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

    override fun onPlaybackReceived(body: MutableList<Playback>?) {

    }

    override fun onPersonalTracksReceived(tracks: MutableList<Track>?) {

    }

    override fun onUserTracksReceived(tracks: MutableList<Track>?) {

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

    }
}
