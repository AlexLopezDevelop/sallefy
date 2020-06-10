package tk.alexlopez.sallefy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.Query;
import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.adapters.TrackListAdapter;
import tk.alexlopez.sallefy.models.ObjectBox;
import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.models.SavedTracks;
import tk.alexlopez.sallefy.models.SavedTracks_;
import tk.alexlopez.sallefy.models.Track;
import tk.alexlopez.sallefy.network.callback.TrackCallback;
import tk.alexlopez.sallefy.network.manager.TrackManager;

public class DownloadSongActivity extends AppCompatActivity  {

    private RecyclerView mRecyclerView;
    private ArrayList<Track> mTracks;
    private TextView mPlaylistsTitle;
    private Box<SavedTracks> tracksBox;
    private Query<SavedTracks> tracksQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks_list);

        tracksBox = ObjectBox.get().boxFor(SavedTracks.class);
        tracksQuery = tracksBox.query().order(SavedTracks_.id).build();
        updateSongs();
    }
    private void updateSongs(){
        List<SavedTracks> downloadTracks = tracksQuery.find();

    }
}
