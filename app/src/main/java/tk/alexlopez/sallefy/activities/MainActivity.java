package tk.alexlopez.sallefy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.adapters.PlaylistAdapter;
import tk.alexlopez.sallefy.adapters.TrackListAdapter;
import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.models.Track;
import tk.alexlopez.sallefy.network.callback.PlaylistCallback;
import tk.alexlopez.sallefy.network.callback.TrackCallback;
import tk.alexlopez.sallefy.network.manager.PlaylistManager;
import tk.alexlopez.sallefy.network.manager.TrackManager;

public class MainActivity extends AppCompatActivity implements PlaylistCallback {
    private RecyclerView mRecyclerView;
    private ArrayList<Playlist> mPlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Comment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        getData();
    }
    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.popularPlaylists);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        TrackListAdapter adapter = new TrackListAdapter(this, null);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }
    private void getData() {
        PlaylistManager.getInstance(this).getAllPlaylists(this);
        mPlaylist = new ArrayList<>();
    }
    @Override
    public void onPlaylistReceived(List<Playlist> playlists) {
        mPlaylist = (ArrayList) playlists;
        PlaylistAdapter adapter = new PlaylistAdapter(this, mPlaylist);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }

    @Override
    public void onPlaylistcreated() {

    }
}
