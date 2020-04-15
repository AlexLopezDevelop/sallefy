package tk.alexlopez.sallefy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.models.Track;
import tk.alexlopez.sallefy.network.manager.TrackManager;

public class TrackOptionsActivity extends AppCompatActivity {

    ImageView ivTrackCover;
    TextView tvTrackCover;
    TextView tvTrackAuthor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_options);

        initViews();
        getData();
    }

    private void initViews() {
        ivTrackCover = findViewById(R.id.track_cover);
        tvTrackCover = findViewById(R.id.track_title);
        tvTrackAuthor = findViewById(R.id.track_author);
    }

    private void getData() {

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Track track = (Track) bundle.getSerializable("trackData");

        if (track.getThumbnail() != null) {
            Glide.with(this)
                    .asBitmap()
                    //.placeholder(R.drawable.ic_audiotrack) TODO: Change default image
                    .load(track.getThumbnail())
                    .into(ivTrackCover);
        }

        tvTrackCover.setText(track.getName());
        tvTrackAuthor.setText(track.getUserLogin());
    }
}
