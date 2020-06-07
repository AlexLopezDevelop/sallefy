package tk.alexlopez.sallefy.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_artist_profile.*
import kotlinx.android.synthetic.main.activity_track_options.*
import tk.alexlopez.sallefy.R
import tk.alexlopez.sallefy.models.User


class ArtistProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist_profile)

        val intent = intent
        val user = intent.getParcelableExtra<User>("user")


        if (user.imageUrl != null) {
            Glide.with(this)
                    .asBitmap()
                    .load(user.imageUrl)
                    .into(artist_image!!)
        }

        artist_first_name.text = user.firstName
        artist_last_name.text = user.lastName
        artist_email.text = user.email

        Log.d("ArtistProfileActivity", "id: ${user.id}")
    }


}