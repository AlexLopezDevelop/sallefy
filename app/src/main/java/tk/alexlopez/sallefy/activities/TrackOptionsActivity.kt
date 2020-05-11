
package tk.alexlopez.sallefy.activities
import android.os.Bundle
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

class TrackOptionsActivity : AppCompatActivity(), TrackCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        data
    }

    private fun initViews() {

        val binding = DataBindingUtil.setContentView<ActivityTrackOptionsBinding>(this, R.layout.activity_track_options)
        val model= ViewModelProvider.NewInstanceFactory().create(TrackOptionsViewModel::class.java)
        binding.model = model
        model.errorMessage.observe(this,  Observer {
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

        }

        show_artist_option.setOnClickListener {

        }

        share_layout.setOnClickListener() {
            fun onClick(v: View?) {
                //
            }
        }

    }

    //.placeholder(R.drawable.ic_audiotrack) TODO: Change default image
    private val data: Unit
        get() {
            val intent = this.intent
            val bundle = intent.extras
            bundle?.let { b ->
                (b.getSerializable("trackData") as Track?)?.let { track ->

                    if (track.thumbnail != null) {
                        Glide.with(this)
                                .asBitmap() //.placeholder(R.drawable.ic_audiotrack) TODO: Change default image
                                .load(track.thumbnail)
                                .into(track_cover!!)
                    }
                    track_title?.text = track.name
                    track_author?.text = track.userLogin

                }
            }
            //TODO: you MUST be return DATA
        }

    override fun onPersonalTracksReceived(tracks: MutableList<Track>?) {
        TODO("Not yet implemented")
    }

    override fun onUserTracksReceived(tracks: MutableList<Track>?) {
        TODO("Not yet implemented")
    }

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

    override fun onTracksReceived(tracks: MutableList<Track>?) {
        TODO("Not yet implemented")
    }
}
