package tk.alexlopez.sallefy.activities;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.models.Genre;
import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.models.Track;
import tk.alexlopez.sallefy.network.callback.GenreCallback;
import tk.alexlopez.sallefy.network.callback.TrackCallback;
import tk.alexlopez.sallefy.network.manager.CloudinaryManager;
import tk.alexlopez.sallefy.network.manager.GenreManager;
import tk.alexlopez.sallefy.utils.Constants;


public class UploadActivity extends Activity implements GenreCallback, TrackCallback {

    private EditText etTitle;
    private Spinner mSpinner;
    private TextView mFilename;
    private Button btnFind, btnCancel, btnAccept;

    private ArrayList<String> mGenres;
    private ArrayList<Genre> mGenresObjs;
    private Uri mFileUri;
    private ArrayList<Track> mTracksObjs;
    private ArrayList<String> mTracks;

    private Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_upload_song);
        mContext = getApplicationContext();
        initViews();
        getData();
    }

    private void initViews() {
        etTitle = (EditText) findViewById(R.id.upload_song_title);
        mFilename = (TextView) findViewById(R.id.upload_song_file_name);

        mSpinner = (Spinner) findViewById(R.id.upload_song_spinner);

        btnFind = (Button) findViewById(R.id.upload_song_find_file);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAudioFromStorage();
            }
        });

        btnCancel = (Button) findViewById(R.id.upload_song_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAccept = (Button) findViewById(R.id.upload_song_done);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkParameters()) {
                    etTitle.setFocusable(false);
                    //showStateDialog(false);
                    uploadToCloudinary();
                }
            }
        });

    }

    private void getData() {
        GenreManager.getInstance(this).getAllGenres(this);
    }

    private boolean checkParameters() {
        if (!etTitle.getText().toString().equals("")) {
            if (mFileUri != null) {
                return true;
            }
        }
        uploadDialog("Error","Parameter missing");
        return false;
    }

    /*private void showStateDialog(boolean completed) {
    }*/
    protected void uploadDialog (String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", (dialog, which) -> {
        });
        builder.show();
    }
    private void getAudioFromStorage() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/mpeg");
        startActivityForResult(Intent.createChooser(intent, "Choose a song"), Constants.STORAGE.SONG_SELECTED);
    }

    private void uploadToCloudinary() {
        Genre genre = new Genre();
        for (Genre g : mGenresObjs) {
            if (g.getName().equals(mSpinner.getSelectedItem().toString())) {
                genre = g;
            }
        }
        CloudinaryManager.getInstance(this, this).uploadAudioFile(mFileUri, etTitle.getText().toString(), genre);
        uploadDialog("Upload song to Cloudinary","The song has been uploaded correctly");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.STORAGE.SONG_SELECTED && resultCode == RESULT_OK) {
            mFileUri = data.getData();
            mFilename.setText(mFileUri.toString());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   GenreCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onGenresReceive(ArrayList<Genre> genres) {
        mGenresObjs = genres;
        mGenres = (ArrayList<String>) genres.stream().map(Genre -> Genre.getName()).collect(Collectors.toList());
        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, mGenres);
        mSpinner.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onTracksByGenre(ArrayList<Track> tracks) {
        mTracksObjs = tracks;
        mTracks = (ArrayList<String>) tracks.stream().map(Track->Track.getName()).collect(Collectors.toList());
        // ikhasa ad hayagh recyclerview diis
    }

    @Override
    public void onFailure(Throwable throwable) { }

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   TrackCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @Override
    public void onTracksReceived(List<Track> tracks) { }

    @Override
    public void onNoTracks(Throwable throwable) { }

    @Override
    public void onPersonalTracksReceived(List<Track> tracks) { }

    @Override
    public void onUserTracksReceived(List<Track> tracks) { }

    @Override
    public void onCreateTrack() { }

    @Override
    public void onTrackSelected(Track track) { }

    @Override
    public void onTrackSelected(int index) { }

    @Override
    public void onTracksReceivedByPlaylistId(Playlist playlist) { }

}

