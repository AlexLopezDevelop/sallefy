package tk.alexlopez.sallefy.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import tk.alexlopez.sallefy.R;

public class AddSongPlayList extends AppCompatActivity {

    private TextView song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song_playlist);

    }


}
