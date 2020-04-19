package tk.alexlopez.sallefy.activities;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gauravk.audiovisualizer.visualizer.BarVisualizer;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.models.Track;
import tk.alexlopez.sallefy.network.callback.TrackCallback;
import tk.alexlopez.sallefy.network.manager.TrackManager;
 public class DynamicPlaybackActivity extends Activity implements TrackCallback {

    private static final String TAG = "DynamicPlaybackActivity";
    private static final String PLAY_VIEW = "PlayIcon";
    private static final String STOP_VIEW = "StopIcon";


    private TextView tvTitle;
    private TextView tvAuthor;
    private ImageView ivPhoto;

    private ImageButton btnBackward;
    private ImageButton btnPlayStop;
    private ImageButton btnForward;
    private SeekBar mSeekBar;


    private Handler mHandler;
    private Runnable mRunnable;

    private BarVisualizer mVisualizer;
    private int mDuration;

    private RecyclerView mRecyclerView;

    private MediaPlayer mPlayer;
    private ArrayList<Track> mTracks;
    private int currentTrack = 0;


    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
         mDuration = 0;
        initViews();
        getData();
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

    private void initViews() {

         LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
         mRecyclerView.setLayoutManager(manager);

        mVisualizer = findViewById(R.id.dynamic_barVisualizer);

        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mSeekBar.setMax(mPlayer.getDuration());
                mDuration =  mPlayer.getDuration();
                playAudio();

                int audioSessionId = mPlayer.getAudioSessionId();

            }
        });

        mHandler = new Handler();



         btnBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTrack = ((currentTrack-1)%(mTracks.size()));
                currentTrack = currentTrack < 0 ? (mTracks.size()-1):currentTrack;
                updateTrack(mTracks.get(currentTrack));
            }
        });
         btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTrack = ((currentTrack+1)%(mTracks.size()));
                currentTrack = currentTrack >= mTracks.size() ? 0:currentTrack;

                updateTrack(mTracks.get(currentTrack));
            }
        });

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

    private void playAudio() {
        mPlayer.start();
        updateSeekBar();
         btnPlayStop.setTag(STOP_VIEW);
        //Toast.makeText(getApplicationContext(), "Playing Audio", Toast.LENGTH_SHORT).show();
    }

    private void pauseAudio() {
        mPlayer.pause();
         btnPlayStop.setTag(PLAY_VIEW);
        //Toast.makeText(getApplicationContext(), "Pausing Audio", Toast.LENGTH_SHORT).show();
    }

    private void prepareMediaPlayer(final String url) {
        Thread connection = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mPlayer.setDataSource(url);
                    mPlayer.prepare(); // might take long! (for buffering, etc)
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"Error, couldn't play the music\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        connection.start();
    }

    public void updateSeekBar() {
        mSeekBar.setProgress(mPlayer.getCurrentPosition());

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

    public void updateTrack(Track track) {
        //updateSessionMusicData(offset);
        tvAuthor.setText(track.getUserLogin());
        tvTitle.setText(track.getName());
        try {
            mPlayer.reset();
            mPlayer.setDataSource(track.getUrl());
            //mediaPlayer.pause();
            mPlayer.prepare();
        } catch(Exception e) {
        }
    }

    public void updateSessionMusicData(int offset) {
        /*int oldIndex = Session.getInstance(getApplicationContext()).getIndex();
        int size = Session.getInstance(getApplicationContext()).getTracks().size();
        int newIndex = (oldIndex + offset)%size;
        Session.getInstance(getApplicationContext()).setIndex(newIndex);
        Track newTrack = Session.getInstance(getApplicationContext()).getTracks().get(newIndex);
        Session.getInstance(getApplicationContext()).setTrack(newTrack);*/
    }


    private void getData() {
        TrackManager.getInstance(this).getAllTracks(this);
        mTracks = new ArrayList<>();
    }

    @Override
    public void onTracksReceived(List<Track> tracks) {
        mTracks = (ArrayList) tracks;
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
    public void onFailure(Throwable throwable) {

    }

    @Override
    public void onTrackSelected(Track track) {
        updateTrack(track);
    }

    @Override
    public void onTrackSelected(int index) {
        currentTrack = index;
        updateTrack(mTracks.get(currentTrack));
    }

     @Override
     public void onTracksReceivedByPlaylistId(Playlist playlist) {

     }
 }

