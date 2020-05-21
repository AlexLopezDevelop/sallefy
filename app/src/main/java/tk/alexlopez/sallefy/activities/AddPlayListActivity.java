package tk.alexlopez.sallefy.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.awt.font.TextAttribute;
import java.util.List;

import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.network.callback.PlaylistCallback;

public class AddPlayListActivity extends AppCompatActivity implements PlaylistCallback {

    private TextAttribute playlist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addplaylist);

    }


    @Override
    public void onPlaylistcreated() {

    }

    @Override
    public void onPlaylistReceived(List<Playlist> tracks) {




    }

    @Override
    public void onPlaylistById(Playlist playlist) {

    }


    @Override
    public void onFailure(Throwable throwable) {

    }
}
