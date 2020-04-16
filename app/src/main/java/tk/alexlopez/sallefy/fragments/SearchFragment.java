package tk.alexlopez.sallefy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.adapters.TrackListAdapter;
import tk.alexlopez.sallefy.network.manager.MeManager;
import tk.alexlopez.sallefy.network.manager.PlaylistManager;


public class SearchFragment extends Fragment {

    public static final String TAG = SearchFragment.class.getName();



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
        getData();
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


    }

    private void getData() {

    }





}
