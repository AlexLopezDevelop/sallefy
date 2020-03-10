package tk.alexlopez.sallefy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.network.callback.PlaylistCallback;
import tk.alexlopez.sallefy.network.manager.PlaylistManager;

public class CreatePlaylistActivity extends AppCompatActivity implements PlaylistCallback {

    private EditText etPlaylistName;
    private Button btCreatePlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_playlist);

        initViews();
    }

    private void initViews() {

        etPlaylistName = findViewById(R.id.playlist_name);
        btCreatePlaylist = findViewById(R.id.create_playlist_button);


        btCreatePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playlistName = etPlaylistName.getText().toString();

                Playlist playlist = new Playlist(playlistName);

                PlaylistManager.getInstance(getApplicationContext()).createPlaylist(playlist, this);
            }
        });

    }

    @Override
    public void onPlaylistReceived(List<Playlist> playlists) {
        Toast.makeText(getApplicationContext(), "OKEY", Toast.LENGTH_LONG);
    }

    @Override
    public void onNoPlaylists(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Call failed!", Toast.LENGTH_LONG);
    }

    @Override
    public void onPersonalPlaylists(List<Playlist> playlists) {

    }

    @Override
    public void onUserPlaylistsReceived(List<Playlist> playlists) {

    }

    @Override
    public void onFailure(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG);
    }
}
