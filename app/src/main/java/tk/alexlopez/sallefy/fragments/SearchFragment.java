package tk.alexlopez.sallefy.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.adapters.TrackListAdapter;
import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.models.Search;
import tk.alexlopez.sallefy.network.callback.PlaylistCallback;
import tk.alexlopez.sallefy.network.callback.SearchCallback;
import tk.alexlopez.sallefy.network.manager.SearchManager;


public class SearchFragment extends Fragment implements PlaylistCallback, SearchCallback {

    public static final String TAG = SearchFragment.class.getName();
    private EditText input_search;
    private Search mSearch;
    private RecyclerView mRecyclerView;

    public static SearchFragment getInstance() {
        return new SearchFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        initViews(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void initViews(View v) {

        mRecyclerView = (RecyclerView) v.findViewById(R.id.searchPlaylist);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        TrackListAdapter adapter = new TrackListAdapter(getContext(), null, -1);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);

        input_search = (EditText) v.findViewById(R.id.input_search);
        input_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = input_search.getText().toString();
                Log.d(TAG, "HAY CAMBIOOOOOOOOOOOOOOOOOOOOO " + input_search.getText());
                getData(keyword);
                // you can call or do what you want with your EditText here

                // yourEditText...
            }
        });
    }

    private void getData(String keyword) {
        SearchManager.getInstance(getContext()).searchByKeyword(keyword, this);
        mSearch = new Search();

    }

    @Override
    public void onPlaylistcreated() {

    }

    @Override
    public void onPlaylistReceived(List<Playlist> tracks) {

    }

    @Override
    public void onTracksReceived(Search search) {
        mSearch = search;
        TrackListAdapter adapter = new TrackListAdapter(getContext(), mSearch.getTracks(), -1);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }



}
