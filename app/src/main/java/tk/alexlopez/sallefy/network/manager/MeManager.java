package tk.alexlopez.sallefy.network.manager;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.models.UserToken;
import tk.alexlopez.sallefy.network.callback.MeCallback;
import tk.alexlopez.sallefy.network.service.MeService;
import tk.alexlopez.sallefy.utils.AuthenticationHeader;
import tk.alexlopez.sallefy.utils.Constants;
import tk.alexlopez.sallefy.utils.Session;

public class MeManager {

    private static final String TAG = "PlaylistManager";

    @SuppressLint("StaticFieldLeak")
    private static MeManager sMeManager;
    private Context mContext;
    private MeService mMeService;
    private AuthenticationHeader authHeader = AuthenticationHeader.Companion.getInstance();

    public static MeManager getInstance(Context context) {
        if (sMeManager == null) {
            sMeManager = new MeManager(context);
        }
        return sMeManager;
    }

    private MeManager(Context cntxt) {
        mContext = cntxt;
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.NETWORK.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mMeService = mRetrofit.create(MeService.class);
    }

    public synchronized void getMyPlaylists ( final MeCallback meCallback) {

        UserToken userToken = Session.getInstance(mContext).getUserToken();

        Call<List<Playlist>> call = mMeService.getMyPlaylist(authHeader.getToken());

        call.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(@NotNull Call<List<Playlist>> call, @NotNull Response<List<Playlist>> response) {

                int code = response.code();
                if (response.isSuccessful()) {
                    meCallback.myPlaylistReceived(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                }
            }
            @Override
            public void onFailure(@NotNull Call<List<Playlist>> call, @NotNull Throwable t) {
            }
        });
    }
    public synchronized void getMyFollowingPlaylist ( final MeCallback meCallback) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();
        Call<List<Playlist>> call = mMeService.getMyFollowingPlaylist(authHeader.getToken());
        call.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(@NotNull Call<List<Playlist>> call, @NotNull Response<List<Playlist>> response) {
                int code = response.code();
                if (response.isSuccessful()) {
                    meCallback.myFollowingPlaylistReceived(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                }
            }
            @Override
            public void onFailure(@NotNull Call<List<Playlist>> call, @NotNull Throwable t) {
            }
        });
    }
}
