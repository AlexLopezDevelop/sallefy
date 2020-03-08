package tk.alexlopez.sallefy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tk.alexlopez.sallefy.CreatePlaylistActivity;
import tk.alexlopez.sallefy.R;

public class MainActivity extends AppCompatActivity {

    private Button mTrackList;
    private Button mAdvancedList;
    private Button mCreatePlayList;

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


    }
}
