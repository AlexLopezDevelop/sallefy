package tk.alexlopez.sallefy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.Query;
import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.adapters.DownloadsAdapter;
import tk.alexlopez.sallefy.adapters.TrackListAdapter;
import tk.alexlopez.sallefy.models.ObjectBox;
import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.models.SavedTracks;
import tk.alexlopez.sallefy.models.SavedTracks_;
import tk.alexlopez.sallefy.models.Track;

public class SongsFragment extends Fragment{

    public static final String TAG = SongsFragment.class.getName();
    private RecyclerView mRecyclerView;
    private ArrayList<SavedTracks> mPlaylist;

    public static SongsFragment getInstance() {
        return new SongsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_songs, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {

        Box<SavedTracks> tracksBox = ObjectBox.get().boxFor(SavedTracks.class);
        Query<SavedTracks> tracksQuery = tracksBox.query().order(SavedTracks_.id).build();
        List<SavedTracks> downloadTracks = tracksQuery.find();

        mPlaylist = (ArrayList<SavedTracks>) downloadTracks;

        mRecyclerView = (RecyclerView) v.findViewById(R.id.downloadSongs);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        DownloadsAdapter adapter = new DownloadsAdapter(getContext(), mPlaylist);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);

       // mountRecyclerView(downloadTracks);
    }

    private void mountRecyclerView(List<SavedTracks> tracks){

        mPlaylist = (ArrayList<SavedTracks>) tracks;
        DownloadsAdapter adapter = new DownloadsAdapter(getContext(), mPlaylist);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }


}