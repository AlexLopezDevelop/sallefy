package tk.alexlopez.sallefy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.models.Track;
import tk.alexlopez.sallefy.network.callback.TrackCallback;
import tk.alexlopez.sallefy.network.manager.TrackManager;

public class TrackListActivity extends AppCompatActivity implements TrackCallback {

    private TextView tvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_list);

        initViews();
        getData();
    }

    private void initViews() {
        tvList = findViewById(R.id.tracks_string_list);
    }

    private void getData() {
        TrackManager.getInstance(this).getAllTracks(this);
    }

    private void updateList(String list) {
        tvList.setText(list);
    }

    @Override
    public void onTracksReceived(List<Track> tracks) {
        StringBuilder res = new StringBuilder();
        for (Track t: tracks) {
            res.append(t.getName() + " - " + t.getUser().getLogin() + "\n");
        }
        updateList(res.toString());
    }

    @Override
    public void onNoTracks(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Call failed!", Toast.LENGTH_LONG);
    }

    @Override
    public void onPersonalTracksReceived(List<Track> tracks) {

    }

    @Override
    public void onUserTracksReceived(List<Track> tracks) {

    }

    @Override
    public void onFailure(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG);
    }
}
