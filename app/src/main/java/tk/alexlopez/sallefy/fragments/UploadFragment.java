package tk.alexlopez.sallefy.fragments;
import android.app.AlertDialog;
import android.content.Context;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.models.Genre;
import tk.alexlopez.sallefy.models.Playback;
import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.models.Track;
import tk.alexlopez.sallefy.models.User;
import tk.alexlopez.sallefy.network.callback.GenreCallback;
import tk.alexlopez.sallefy.network.callback.TrackCallback;
import tk.alexlopez.sallefy.network.manager.CloudinaryManager;
import tk.alexlopez.sallefy.network.manager.GenreManager;
import tk.alexlopez.sallefy.utils.Constants;

import static android.app.Activity.RESULT_OK;


public class UploadFragment extends Fragment implements GenreCallback, TrackCallback {

    public static final String TAG = UploadFragment.class.getName();
    private EditText etTitle;
    private Spinner mSpinner;
    private TextView mFilename;

    private ArrayList<Genre> mGenresObjs;
    private Uri mFileUri;
    private Context mContext;

    public static UploadFragment getInstance() {
        return new UploadFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initViews();
        //getData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_upload_song, container, false);
        initViews(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void initViews(View view) {
        etTitle = (EditText) view.findViewById(R.id.upload_song_title);
        mFilename = (TextView) view.findViewById(R.id.upload_song_file_name); // findViewById(R.id.upload_song_file_name);
        mSpinner = (Spinner) view.findViewById(R.id.upload_song_spinner); // findViewById(R.id.upload_song_spinner);

        Button btnFind = (Button) view.findViewById(R.id.upload_song_find_file);  // findViewById(R.id.upload_song_find_file);
        btnFind.setOnClickListener(v -> getAudioFromStorage());

        Button btnFindImg = (Button) view.findViewById(R.id.upload_song_find_img); // findViewById(R.id.upload_song_find_img);
        btnFindImg.setOnClickListener(v -> getImageFromStorage());

        Button btnCancel = (Button) view.findViewById(R.id.upload_song_cancel); // findViewById(R.id.upload_song_cancel);
        btnCancel.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

        Button btnAccept = (Button) view.findViewById(R.id.upload_song_done); // findViewById(R.id.upload_song_done);
        btnAccept.setOnClickListener(v -> {
            if (checkParameters()) {
                etTitle.setFocusable(false);
                uploadToCloudinary();
            }
        });


    }

    private void getData() {
        GenreManager.getInstance(getContext()).getAllGenres(this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        uploadDialog("Upload song to Sallefy","The song has been uploaded correctly");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.STORAGE.SONG_SELECTED && resultCode == RESULT_OK) {
            mFileUri = data.getData();
            mFilename.setText(mFileUri.toString());

        }
    }
    private void getImageFromStorage() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/png");
        startActivityForResult(Intent.createChooser(intent, "Choose a image"), Constants.STORAGE.IMG_SELECTED);
    }


    /**********************************************************************************************
     *   *   *   *   *   *   *   *   GenreCallback   *   *   *   *   *   *   *   *   *
     **********************************************************************************************/

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onGenresReceive(ArrayList<Genre> genres) {
        mGenresObjs = genres;
        ArrayList<String> mGenres = (ArrayList<String>) genres.stream().map(Genre -> Genre.getName()).collect(Collectors.toList());

        //ArrayAdapter adapter = new ArrayAdapter (this, R.layout.support_simple_spinner_dropdown_item, mGenres);
        mSpinner.setAdapter(new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, mGenres));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onTracksByGenre(ArrayList<Track> tracks) {
        ArrayList<String> mTracks = (ArrayList<String>) tracks.stream().map(Track -> Track.getName()).collect(Collectors.toList());
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
    @Override
    public void onNoLikedTrack(Boolean response) {
    }
    @Override
    public void onLikedTrack(Boolean response) { }

    @Override
    public void onPlaylistUpdated(Boolean response) {

    }

    @Override
    public void onPlaylistsReceived(List<Playlist> playlists) {

    }

    @Override
    public void onUserInfoReceived(User body) {

    }

    @Override
    public void onPlaybackReceived(List<Playback> body) {

    }
}

