package tk.alexlopez.sallefy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tk.alexlopez.sallefy.adapters.TrackListAdapter;
import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.models.Track;
import tk.alexlopez.sallefy.network.callback.PlaylistCallback;
import tk.alexlopez.sallefy.network.callback.TrackCallback;
import tk.alexlopez.sallefy.network.manager.PlaylistManager;
import tk.alexlopez.sallefy.network.manager.TrackManager;

public class TrackPlaylistActivity extends AppCompatActivity implements TrackCallback {

    private EditText etPlaylistId;
    private EditText etTrackId;
    private Button btAddTrackToPlaylist;

    private Playlist mPlaylist;
    private Track mTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_playlist);

        initViews();
    }

    private void initViews() {

        etPlaylistId = findViewById(R.id.id_playlist);
        etTrackId = findViewById(R.id.id_track);
        btAddTrackToPlaylist = findViewById(R.id.add_track_to_playlist);

        btAddTrackToPlaylist.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int playlistId = Integer.parseInt(etPlaylistId.getText().toString());


                TrackManager.getInstance(getApplicationContext()).getAllTracksByPlaylistId(playlistId, TrackPlaylistActivity.this);





            }
        });
    }



    @Override
    public void onFailure(Throwable throwable) {

    }

    @Override
    public void onTracksReceived(List<Track> tracks) {

    }

    @Override
    public void onTracksReceivedByPlaylistId(Playlist playlist) {
        mPlaylist = playlist;
        int trackId = Integer.parseInt(etTrackId.getText().toString());
        TrackManager.getInstance(getApplicationContext()).getTrackById(trackId, TrackPlaylistActivity.this);
    }

    @Override
    public void onPlaylistUpdated(Playlist playlist) {
        Toast.makeText(getApplicationContext(), "Playlist updated", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTrackReceived(Track track) {
        mTrack = track;
        if (mPlaylist != null && mTrack != null) {
            TrackManager.getInstance(getApplicationContext()).addTrackToPlaylist(mPlaylist, mTrack, TrackPlaylistActivity.this);
        } else {
            // TODO: Show error
        }
    }

    @Override
    public void onNoTracks(Throwable throwable) {

    }

    @Override
    public void onPersonalTracksReceived(List<Track> tracks) {

    }

    @Override
    public void onUserTracksReceived(List<Track> tracks) {

    }
}
