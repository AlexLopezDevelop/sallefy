package tk.alexlopez.sallefy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.adapters.PlaylistAdapter;
import tk.alexlopez.sallefy.adapters.TrackListAdapter;
import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.network.callback.MeCallback;
import tk.alexlopez.sallefy.network.callback.PlaylistCallback;
import tk.alexlopez.sallefy.network.manager.MeManager;
import tk.alexlopez.sallefy.network.manager.PlaylistManager;

public class HomeFragment extends Fragment implements PlaylistCallback, MeCallback {

    public static final String TAG = HomeFragment.class.getName();
    private RecyclerView mRecyclerView;
    private RecyclerView mMyPlaylists;
    private RecyclerView mListasSeguidas;
    private PlaylistAdapter mAdapter;
    private ArrayList<Playlist> mPlaylist;


    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        initViews(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void initViews(View v) {

        mRecyclerView = (RecyclerView) v.findViewById(R.id.popularPlaylists);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        TrackListAdapter adapter = new TrackListAdapter(getContext(), null);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);

        mMyPlaylists = (RecyclerView) v.findViewById(R.id.MyPlaylists);
        LinearLayoutManager manager_playlist = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        TrackListAdapter adapter_playlist = new TrackListAdapter(getContext(), null);
        mMyPlaylists.setLayoutManager(manager_playlist);
        mMyPlaylists.setAdapter(adapter_playlist);

        mListasSeguidas = (RecyclerView) v.findViewById(R.id.listas_seguidas);
        LinearLayoutManager manager_listas_seguidas = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        TrackListAdapter adapter_listas_seguidas = new TrackListAdapter(getContext(), null);
        mListasSeguidas.setLayoutManager(manager_listas_seguidas);
        mListasSeguidas.setAdapter(adapter_listas_seguidas);
    }

    private void getData() {
        PlaylistManager.getInstance(getContext()).getAllPlaylists(this);
        MeManager.getInstance(getContext()).getMyPlaylists(this);
        MeManager.getInstance(getContext()).getMyFollowingPlaylist(this);

        mPlaylist = new ArrayList<>();
    }


    @Override
    public void onPlaylistcreated() {

    }

    @Override
    public void onPlaylistReceived(List<Playlist> playlists) {
        mPlaylist = (ArrayList) playlists;
        PlaylistAdapter adapter = new PlaylistAdapter(getContext(), mPlaylist);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }
    @Override
    public void myPlaylistReceived(List<Playlist> playlists) {
        mPlaylist = (ArrayList) playlists;
        PlaylistAdapter my_adapter = new PlaylistAdapter(getContext(), mPlaylist);
        mMyPlaylists.setAdapter(my_adapter);
        mMyPlaylists.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }
    @Override
    public void myFollowingPlaylistReceived(List<Playlist> playlists) {
        mPlaylist = (ArrayList) playlists;
        PlaylistAdapter adapter_list = new PlaylistAdapter(getContext(), mPlaylist);
        mListasSeguidas.setAdapter(adapter_list);
        mListasSeguidas.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }
}

