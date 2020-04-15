package tk.alexlopez.sallefy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.network.manager.TrackManager;

public class TrackOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_options);

        getData();
    }

    private void getData() {

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);

        if (id != -1) {
            //TrackManager.getInstance(this).getAllTracksByPlaylistId(id, this);
        } else {
            //TrackManager.getInstance(this).getAllTracks(this);
            // TODO: Error
        }

    }
}
