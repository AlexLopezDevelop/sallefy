package tk.alexlopez.sallefy.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gauravk.audiovisualizer.visualizer.BarVisualizer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;
import io.objectbox.query.Query;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tk.alexlopez.sallefy.BuildConfig;
import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.adapters.TrackListAdapter;
import tk.alexlopez.sallefy.models.MyObjectBox;
import tk.alexlopez.sallefy.models.ObjectBox;
import tk.alexlopez.sallefy.models.Playback;
import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.models.SavedTracks;
import tk.alexlopez.sallefy.models.SavedTracks_;
import tk.alexlopez.sallefy.models.Track;
import tk.alexlopez.sallefy.models.User;
import tk.alexlopez.sallefy.network.callback.TrackCallback;
import tk.alexlopez.sallefy.network.manager.TrackManager;

public class PlayTrackActivity extends Activity implements TrackCallback {
    private static final String TAG = "DynamicPlaybackActivity";
    private static final String PLAY_VIEW = "PlayIcon";
    private static final String STOP_VIEW = "StopIcon";


    private TextView timeTotal;
    private TextView timeCurrent;
    private TextView tvTitle;
    private TextView tvAuthor;
    private ImageView ivPhoto;

    private ImageButton btnDownload;
    private ImageButton btnBackward;
    private ImageButton btnPlayStop;
    private ImageButton btnForward;
    private SeekBar mSeekBar;

    private ConstraintLayout clMainLayout;
    private AnimationDrawable adAnimationDrawable;

    private Handler mHandler;
    private Runnable mRunnable;

    private BarVisualizer mVisualizer;
    private int mDuration;

    private RecyclerView mRecyclerView;

    private MediaPlayer mPlayer;
    private ArrayList<Track> mTracks;
    private int currentTrack = 0;
    private Track cTrack;
    private Box<SavedTracks> tracksBox;
    private Query<SavedTracks> tracksQuery;

    List<SavedTracks> listBox = new ArrayList<SavedTracks>();
    private Object SavedTracks_;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        mDuration = 0;
        initViews();
        getData();
        searchDownload();
        tracksBox = ObjectBox.get().boxFor(SavedTracks.class);
       // tracksQuery = tracksBox.query().order(SavedTracks_.id_song).build();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVisualizer != null)
            mVisualizer.release();
    }
    public  String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }
    private void initViews() {

        layoutAnimation();

        mPlayer = new MediaPlayer();

        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mSeekBar.setMax(mPlayer.getDuration());
                mDuration =  mPlayer.getDuration();

                timeTotal.setText(milliSecondsToTimer(mDuration));

                playAudio();
            }
        });

        mHandler = new Handler();

        tvAuthor = findViewById(R.id.dynamic_artist);
        tvTitle = findViewById(R.id.dynamic_title);
        timeTotal = findViewById(R.id.textTotalTime);
        timeCurrent = findViewById(R.id.textCurrentTime);
        ivPhoto = findViewById(R.id.roundedImageView2);

        btnDownload = (ImageButton)findViewById(R.id.dynamic_download_btn);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    downloadSong(cTrack.getUrl());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnBackward = (ImageButton)findViewById(R.id.dynamic_backward_btn);
        btnBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTrack = ((currentTrack-1)%(mTracks.size()));
                currentTrack = currentTrack < 0 ? (mTracks.size()-1):currentTrack;
                updateTrack(mTracks.get(currentTrack));
            }
        });
        btnForward = (ImageButton)findViewById(R.id.dynamic_forward_btn);
        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTrack = ((currentTrack+1)%(mTracks.size()));
                currentTrack = currentTrack >= mTracks.size() ? 0:currentTrack;

                updateTrack(mTracks.get(currentTrack));
            }
        });

        btnPlayStop = (ImageButton)findViewById(R.id.dynamic_play_btn);
        btnPlayStop.setTag(PLAY_VIEW);
        btnPlayStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (btnPlayStop.getTag().equals(PLAY_VIEW)) {
                    playAudio();

                } else {
                    pauseAudio();
                }
            }
        });
        mSeekBar = (SeekBar) findViewById(R.id.dynamic_seekBar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mPlayer.seekTo(progress);
                }
                if (mDuration > 0) {
                    int newProgress = ((progress*100)/mDuration);
                    System.out.println("New progress: " + newProgress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void layoutAnimation() {

        clMainLayout = findViewById(R.id.music_player);

        adAnimationDrawable = (AnimationDrawable) clMainLayout.getBackground();
        adAnimationDrawable.setEnterFadeDuration(4500);
        adAnimationDrawable.setExitFadeDuration(4500);
    }


    private void playAudio() {
        adAnimationDrawable.start();
        mPlayer.start();
        updateSeekBar();
        btnPlayStop.setImageResource(R.drawable.ic_pause);
        btnPlayStop.setTag(STOP_VIEW);
    }
    private void pauseAudio() {
        adAnimationDrawable.stop();
        mPlayer.pause();
        btnPlayStop.setImageResource(R.drawable.ic_play);
        btnPlayStop.setTag(PLAY_VIEW);
    }
    private void prepareMediaPlayer(final String url) {
        Thread connection = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mPlayer.setDataSource(url);
                    mPlayer.prepare();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"Error, couldn't play the music\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        connection.start();
    }
    private void downloadSong(String URL) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(cTrack.getUrl())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
//                    response.body();
                    //final byte[] input = response.body().bytes();

                    Log.i(TAG, getFilesDir().toString());
                    File file = new File(getFilesDir(),"mydir");
                    if(!file.exists()){
                        file.mkdir();
                    }
                    try{
                        String filePath = cTrack.getId()+"track.mpeg";
                        File gpxfile = new File(file, filePath);
                        FileWriter writer = new FileWriter(gpxfile);
                        writer.write(String.valueOf(response.body()));
                        writer.flush();
                        writer.close();

                        SavedTracks save = new SavedTracks(cTrack.getId(),cTrack.getName(),filePath, cTrack.getThumbnail());
                        tracksBox.put(save);
                        btnDownload.setVisibility(View.INVISIBLE);


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    public void updateSeekBar() {
        mSeekBar.setProgress(mPlayer.getCurrentPosition());
        timeCurrent.setText(milliSecondsToTimer(mPlayer.getCurrentPosition()));

        if(mPlayer.isPlaying()) {
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    updateSeekBar();
                }
            };
            mHandler.postDelayed(mRunnable, 1000);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // Esto es lo que hace mi botón al pulsar ir a atrás
            mPlayer.pause();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public void updateTrack(Track track) {
        //updateSessionMusicData(offset);
        tvAuthor.setText(track.getUserLogin());
        tvTitle.setText(track.getName());
        if (track.getThumbnail() != null) {
            Glide.with(this)
                    .asBitmap() //.placeholder(R.drawable.ic_audiotrack) TODO: Change default image
                    .load(track.getThumbnail())
                    .into(ivPhoto);
        }else{
            Glide.with(this)
                    .asBitmap() //.placeholder(R.drawable.ic_audiotrack) TODO: Change default image
                    .load(track.getThumbnail())
                    .into(ivPhoto);
        }
        try {
            mPlayer.reset();
            mPlayer.setDataSource(track.getUrl());
            //mediaPlayer.pause();
            mPlayer.prepare();

        } catch(Exception e) {

        }
    }


    private void getData() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Track track = (Track) bundle.getSerializable("trackData");
        cTrack = track;
        mTracks = (ArrayList<Track>) bundle.getSerializable("playlist");
        updateTrack(track);

    }
    private void searchDownload(){
        int id= cTrack.getId();
        String filename = id + "track.txt";
        File filepath = new File(getFilesDir(),"mydir");

        File file = new File(filepath,filename);
        if(file.exists()){
            btnDownload.setVisibility(View.INVISIBLE);
        }
    }
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

    @Override
    public void onNoLikedTrack(Boolean response) {

    }

    @Override
    public void onLikedTrack(Boolean response) {

    }

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

    @Override
    public void onFailure(Throwable throwable) {

    }

}
