package tk.alexlopez.sallefy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.TrackPlaylistActivity;

public class MainActivity extends AppCompatActivity {

    private Button mTrackList;
    private Button mAdvancedList;
    private Button mCreatePlayList;
    private Button mAddTrackToPlaylist;
    private Button mPlaylists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {

        mTrackList = findViewById(R.id.show_tracks);
        mAdvancedList = findViewById(R.id.show_advanced_tracks);
        mCreatePlayList = findViewById(R.id.show_create_playlist);
        mAddTrackToPlaylist = findViewById(R.id.show_add_track_playlist);
        mPlaylists = findViewById(R.id.show_playlists);


        mTrackList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TrackListActivity.class);
                startActivity(intent);
            }
        });

        mAdvancedList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdvancedListActivity.class);
                startActivity(intent);
            }
        });

        mCreatePlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreatePlaylistActivity.class);
                startActivity(intent);
            }
        });

        mAddTrackToPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TrackPlaylistActivity.class);
                startActivity(intent);
            }
        });

        mPlaylists.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlaylistsActivity.class);
                startActivity(intent);
            }
        });

    }
}
