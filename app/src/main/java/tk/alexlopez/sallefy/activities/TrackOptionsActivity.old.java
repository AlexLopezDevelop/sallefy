/*package tk.alexlopez.sallefy.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.models.Track;
import tk.alexlopez.sallefy.network.manager.TrackManager;

public class TrackOptionsActivity extends AppCompatActivity {

    private ImageView ivTrackCover;
    private TextView  tvTrackCover;
    private TextView  tvTrackAuthor;

    private String url;
    private Intent sendIntent = new Intent();
    private LinearLayout shareOpcion;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
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
        // compartir en redes social diag
        shareOpcion = findViewById(R.id.share_layout);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
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

        url = track.getUrl();
        shareSong(url);


        System.out.println(shareOpcion);
    }

    public void shareSong(String url) {

        shareOpcion = findViewById(R.id.share_layout);

        shareOpcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendIntent.setAction(Intent.ACTION_SEND);

                sendIntent.putExtra(Intent.EXTRA_TEXT, url);

                sendIntent.setType("urll");

                Intent shareIntent = Intent.createChooser(sendIntent, null);

                startActivity(shareIntent);
            }
        });
    }


}
*/