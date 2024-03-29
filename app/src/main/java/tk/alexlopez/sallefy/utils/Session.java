package tk.alexlopez.sallefy.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import java.util.ArrayList;

import tk.alexlopez.sallefy.activities.MainActivity;
import tk.alexlopez.sallefy.fragments.UploadFragment;
import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.models.Track;
import tk.alexlopez.sallefy.models.User;
import tk.alexlopez.sallefy.models.UserRegister;
import tk.alexlopez.sallefy.models.UserToken;

public class Session {

    @SuppressLint("StaticFieldLeak")
    public static Session sSession;
    private static Object mutex = new Object();

    private Context mContext;

    private UserRegister mUserRegister;
    private User mUser;
    private UserToken mUserToken;

    private boolean audioEnabled;

    private Track mTrack;
    private Playlist mPlaylist;
    private ArrayList<Track> mTracks;
    private int mIndex;
    private boolean isPlaying;

    public static Session getInstance(Context context) {
        Session result = sSession;
        if (result == null) {
            synchronized (mutex) {
                result = sSession;
                if (result == null)
                    sSession = result = new Session();
            }
        }
        return result;
    }

    public static Session instance() {
        Session result = sSession;
        if (result == null) {
            synchronized (mutex) {
                result = sSession;
                if (result == null)
                    sSession = result = new Session();
            }
        }
        return result;
    }

    private Session() {}

    public Session (Context context) {
        this.mContext = context;
        this.mUserRegister = null;
        this.mUserToken = null;
        this.isPlaying = false;
        this.audioEnabled = false;
    }

    public void resetValues() {
        mUserRegister = null;
        mUserToken = null;
    }

    public UserRegister getUserRegister() {
        return mUserRegister;
    }

    public void setUserRegister(UserRegister userRegister) {
        mUserRegister = userRegister;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public UserToken getUserToken() {
        return mUserToken;
    }

    public void setUserToken(UserToken userToken) {
        this.mUserToken = userToken;
    }

    public void setAudioEnabled(boolean b) {
    }
}
