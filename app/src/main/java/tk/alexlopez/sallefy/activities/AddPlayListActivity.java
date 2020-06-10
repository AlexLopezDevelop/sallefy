package tk.alexlopez.sallefy.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.awt.font.TextAttribute;
import java.util.List;
import java.util.zip.Inflater;

import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.fragments.HomeFragment;
import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.network.callback.PlaylistCallback;
import tk.alexlopez.sallefy.network.manager.PlaylistManager;

public class AddPlayListActivity extends AppCompatActivity implements PlaylistCallback {

    private EditText editText;
    private Button btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addplaylist);
        initViews();
    }


    protected void initViews(){

        btnAdd = findViewById(R.id.add_playlist_button);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText = findViewById(R.id.editText);
                String text = editText.getText().toString();
                createNewPlaylist(text);
               // PlaylistManager.getInstance(getApplicationContext()).createPlaylist(text,);
            }
        });
    }
    public void createNewPlaylist(String name){
         PlaylistManager.getInstance(getApplicationContext()).createPlaylist(name,this);
    }
    @Override
    public void onPlaylistCreated() {
        Toast.makeText (getApplicationContext(), "¡Playlist creada!" , Toast.LENGTH_SHORT) .show ();
    }

    @Override
    public void onPlaylistReceived(List<Playlist> tracks) {

    }
}
