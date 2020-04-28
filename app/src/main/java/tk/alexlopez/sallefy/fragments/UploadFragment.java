/* package tk.alexlopez.sallefy.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
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

public class UploadFragment extends Fragment implements GenreCallback, TrackCallback {
    private static final int RESULT_OK = -1;

    public static SearchFragment getInstance() {
        return new SearchFragment();
    }

    private EditText etTitle;
    private Spinner mSpinner;
    private TextView mFilename;
    private Button btnFind, btnCancel, btnAccept;

    private ArrayList<String> mGenres;

    private ArrayList<Genre> mGenresObjs;
    private Uri mFileUri;
    private ArrayList<Track> mTracksObjs;
    private ArrayList<String> mTracks;
    private RecyclerView mRecyclerView;
    private Context mContext;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_upload_song, container, false);
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


    @SuppressLint("WrongViewCast")
    private void initViews(View v) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.upload_song_done);

        etTitle = (EditText) v.findViewById(R.id.upload_song_title);
        mFilename = (TextView) v.findViewById(R.id.upload_song_file_name);

        mSpinner = (Spinner) v.findViewById(R.id.upload_song_spinner);

        btnFind = (Button) v.findViewById(R.id.upload_song_find_file);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAudioFromStorage();
            }
        });

        btnCancel = (Button) v.findViewById(R.id.upload_song_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnAccept = (Button) v.findViewById(R.id.upload_song_done);
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

    protected void getData() {
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

    private void showStateDialog(boolean completed) {
    }
    private void uploadDialog (String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.STORAGE.SONG_SELECTED && resultCode == RESULT_OK) {
            mFileUri = data.getData();
            mFilename.setText(mFileUri.toString());
        }
    }
/*

    /**********************************************************************************************
     *   *   *   *   *   *   *   *   GenreCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/
/*
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onGenresReceive(ArrayList<Genre> genres) {
        View v;
        mGenresObjs = genres;    // v.findViewById(R.id.upload_song_done);
        mGenres = (ArrayList<String>) genres.stream().map(Genre -> Genre.getName()).collect(Collectors.toList());
        ArrayAdapter<String> adapter = new ArrayAdapter(this,  R.layout.support_simple_spinner_dropdown_item, mGenres);
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
    public void onFailure(Throwable throwable) {

    }
*/
    /**********************************************************************************************
     *   *   *   *   *   *   *   *   TrackCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/
/*
    @Override
    public void onTracksReceived(List<Track> tracks) {

    }

    @Override
    public void onNoTracks(Throwable throwable) {

    }

    @Override
    public void onPersonalTracksReceived(List<Track> tracks) {

    }

    @Override
    public void onUserTracksReceived(List<Track> tracks) {

    }

    @Override
    public void onCreateTrack() {

    }

    @Override
    public void onTrackSelected(Track track) {

    }

    @Override
    public void onTrackSelected(int index) {

    }

    @Override
    public void onTracksReceivedByPlaylistId(Playlist playlist) {

    }
}
*/