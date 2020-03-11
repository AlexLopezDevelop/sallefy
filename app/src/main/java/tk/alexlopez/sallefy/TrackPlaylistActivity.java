package tk.alexlopez.sallefy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.network.callback.PlaylistCallback;
import tk.alexlopez.sallefy.network.manager.PlaylistManager;

public class TrackPlaylistActivity extends AppCompatActivity implements PlaylistCallback {

    private EditText etPlaylistId;
    private EditText etTrackId;
    private Button btAddTrackToPlaylist;

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

                Playlist playlist = new Playlist("paco");

                int playlistId = Integer.parseInt(etPlaylistId.getText().toString());
                int trackId = Integer.parseInt(etTrackId.getText().toString());

                PlaylistManager.getInstance(getApplicationContext()).getPlaylistById(playlistId, TrackPlaylistActivity.this);

                //PlaylistManager.getInstance(getApplicationContext()).addTrackToPlaylist(playlist, TrackPlaylistActivity.this);
            }
        });
    }

    @Override
    public void onPlaylistsReceived(List<Playlist> playlists) {

    }

    @Override
    public void onPlaylistReceived(Playlist playlist) {

    }

    @Override
    public void onNoPlaylists(Throwable throwable) {

    }

    @Override
    public void onPersonalPlaylists(List<Playlist> playlists) {

    }

    @Override
    public void onUserPlaylistsReceived(List<Playlist> playlists) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }
}
