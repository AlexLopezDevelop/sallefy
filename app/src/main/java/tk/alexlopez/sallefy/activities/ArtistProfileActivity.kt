package tk.alexlopez.sallefy.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tk.alexlopez.sallefy.R
import tk.alexlopez.sallefy.models.User


class ArtistProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist_profile)

        val intent = intent
        val id = intent.getIntExtra("artistId", -1)
        val test = id
        finish()
    }


}