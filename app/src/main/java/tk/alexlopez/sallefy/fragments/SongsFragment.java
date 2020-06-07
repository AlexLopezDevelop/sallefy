package tk.alexlopez.sallefy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.Query;
import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.models.ObjectBox;
import tk.alexlopez.sallefy.models.SavedTracks;
import tk.alexlopez.sallefy.models.SavedTracks_;
import tk.alexlopez.sallefy.models.Track;

public class SongsFragment extends Fragment{

    public static final String TAG = SongsFragment.class.getName();
    private TextView tvList;
    private Box<SavedTracks> tracksBox;
    private Query<SavedTracks> tracksQuery;

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
        View v =inflater.inflate(R.layout.fragment_songs, container, false);
        initViews(v);
        return v;
    }

    private void updateList(String list) {
        tvList.setText(list);
    }

    private void initViews(View v) {
        tvList = (TextView) v.findViewById(R.id.tracks_string_list);
        StringBuilder res = new StringBuilder();

        tracksBox = ObjectBox.get().boxFor(SavedTracks.class);
        tracksQuery = tracksBox.query().order(SavedTracks_.id).build();
        List<SavedTracks> downloadTracks = tracksQuery.find();

        for (SavedTracks t: downloadTracks) {

            res.append(t.getName() + "\n");
        }
        updateList(res.toString());
    }




}