package tk.alexlopez.sallefy.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_track_options.*
import tk.alexlopez.sallefy.R
import tk.alexlopez.sallefy.models.Track

class TrackOptionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_options)
        initViews()
        data
    }

    private fun initViews() {
        like_track_option.setOnClickListener{

        }

        add_song_to_playlist_option.setOnClickListener {

        }

        show_artist_option.setOnClickListener {

        }

        delete_song_option.setOnClickListener {
            
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
}