package tk.alexlopez.sallefy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tk.alexlopez.sallefy.adapters.PlaylistsAdapter;
import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.network.callback.PlaylistCallback;
import tk.alexlopez.sallefy.network.manager.PlaylistManager;

public class PlaylistsActivity extends AppCompatActivity implements PlaylistCallback {

    private RecyclerView mRecyclerView;
    private ArrayList<Playlist> mPlaylists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);

        initViews();
        getData();
    }

    private void initViews() {
        mRecyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        PlaylistsAdapter adapter = new PlaylistsAdapter(this, null);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }

    private void getData() {
        PlaylistManager.getInstance(this).getAllPlaylists(this);
        mPlaylists = new ArrayList<>();
    }

    @Override
    public void onPlaylistReceived(List<Playlist> playlists) {
        mPlaylists = (ArrayList) playlists;
        PlaylistsAdapter adapter = new PlaylistsAdapter(this, mPlaylists);
        mRecyclerView.setAdapter(adapter);
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
