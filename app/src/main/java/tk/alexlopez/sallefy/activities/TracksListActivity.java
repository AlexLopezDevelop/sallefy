package tk.alexlopez.sallefy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.adapters.TrackListAdapter;
import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.models.Track;
import tk.alexlopez.sallefy.network.callback.TrackCallback;
import tk.alexlopez.sallefy.network.manager.TrackManager;

public class TracksListActivity extends AppCompatActivity implements TrackCallback {

    private RecyclerView mRecyclerView;
    private ArrayList<Track> mTracks;
    private TextView mPlaylistsTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks_list);

        iniViews();
        getData();
    }

    private void iniViews() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mPlaylistsTitle = findViewById(R.id.playlist_title);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        TrackListAdapter adapter = new TrackListAdapter(this, null);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }

    private void getData() {

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);

        if (id != -1) {
            TrackManager.getInstance(this).getAllTracksByPlaylistId(id, this);
        } else {
            //TrackManager.getInstance(this).getAllTracks(this);
            // TODO: Error
        }

        mTracks = new ArrayList<>();

    }

    @Override
    public void onTracksReceived(List<Track> tracks) {
        mTracks = (ArrayList) tracks;
        TrackListAdapter adapter = new TrackListAdapter(this, mTracks);
        mRecyclerView.setAdapter(adapter);
    }

    public void onTracksReceivedByPlaylistId(Playlist playlist) {
        mTracks = (ArrayList) playlist.getTracks();
        TrackListAdapter adapter = new TrackListAdapter(this, mTracks);
        mRecyclerView.setAdapter(adapter);
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

    @Override
    public void onFailure(Throwable throwable) {

    }
}
