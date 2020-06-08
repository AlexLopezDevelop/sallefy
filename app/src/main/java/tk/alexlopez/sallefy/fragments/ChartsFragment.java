package tk.alexlopez.sallefy.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.activities.charts.TopTracksActivity;
import tk.alexlopez.sallefy.activities.charts.TopUserTracksActivity;
import tk.alexlopez.sallefy.activities.charts.TrackEvolutionActivity;
import tk.alexlopez.sallefy.activities.charts.TracksMoreFollowedActivity;

public class ChartsFragment extends Fragment {

    public static final String TAG = ChartsFragment.class.getName();

    public static ChartsFragment getInstance() {
        return new ChartsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_charts, container, false);
        initView(v);
        return v;
    }

    private void initView(View view) {

        Button btTopLikedTracks = view.findViewById(R.id.buttonTopLikedTracks);
        btTopLikedTracks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TopTracksActivity.class);
                startActivity(intent);
            }
        });

        Button btMostPlayedSongs = view.findViewById(R.id.buttonMostPlayedSongs);
        btMostPlayedSongs.setOnClickListener(v ->  startActivity(new Intent(getActivity(), TopUserTracksActivity.class)));

        Button btMostFollowedTracks = view.findViewById(R.id.buttonMostFollowedTracks);
        btMostFollowedTracks.setOnClickListener(v -> startActivity(new Intent(getActivity(), TracksMoreFollowedActivity.class)));

        Button btTrackEvolution = view.findViewById(R.id.buttonTrackEvolution);
        btTrackEvolution.setOnClickListener(v -> startActivity(new Intent(getActivity(), TrackEvolutionActivity.class)));


    }
}