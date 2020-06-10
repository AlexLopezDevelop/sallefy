package tk.alexlopez.sallefy.network.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tk.alexlopez.sallefy.activities.MainActivity;
import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.models.UserToken;
import tk.alexlopez.sallefy.network.callback.PlaylistCallback;
import tk.alexlopez.sallefy.network.service.PlaylistService;
import tk.alexlopez.sallefy.utils.AuthenticationHeader;
import tk.alexlopez.sallefy.utils.Constants;
import tk.alexlopez.sallefy.utils.Session;


public class PlaylistManager {

    private static final String TAG = "PlaylistManager";

    @SuppressLint("StaticFieldLeak")
    private static PlaylistManager sPlaylistManager;
    private Context mContext;
    private PlaylistService mPlaylistService;
    private AuthenticationHeader authHeader = AuthenticationHeader.Companion.getInstance();

    public static PlaylistManager getInstance(Context context) {
        if (sPlaylistManager == null) {
            sPlaylistManager = new PlaylistManager(context);
        }
        return sPlaylistManager;
    }

    private PlaylistManager(Context cntxt) {
        mContext = cntxt;
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.NETWORK.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mPlaylistService = mRetrofit.create(PlaylistService.class);
    }

    /********************   CREATE PLAYLIST    ********************/
    public synchronized void createPlaylist (String name, final PlaylistCallback playlistCallback) {
        UserToken userToken = Session.getInstance((MainActivity) mContext).getUserToken();

        Call<Playlist> call = mPlaylistService.createPlaylist(new Playlist(name), authHeader.getToken());

        call.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(@NotNull Call<Playlist> call, @NotNull Response<Playlist> response) {

                int code = response.code();
                if (response.isSuccessful()) {
                    playlistCallback.onPlaylistCreated();

                } else {
                    assert response.errorBody() != null;
                    Log.d("ERROR:  " ,response.errorBody().toString());
                    Toast.makeText (mContext.getApplicationContext(), "¡Error al crear la playlist!" , Toast.LENGTH_SHORT) .show ();

                    //userCallback.onRegisterFailure(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<Playlist> call, @NotNull Throwable t) {
                Toast.makeText (mContext.getApplicationContext(), "¡Error al crear la playlist!" , Toast.LENGTH_SHORT) .show ();
            }
        });
    }
    /********************   ADD SONG TO PLAYLIST    ********************/
    public synchronized void updatePlaylist (Playlist playlist, final PlaylistCallback playlistCallback) {
        UserToken userToken = Session.getInstance((MainActivity) mContext).getUserToken();

        Call<Playlist> call = mPlaylistService.updatePlaylist(playlist,authHeader.getToken());

        call.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(@NotNull Call<Playlist> call, @NotNull Response<Playlist> response) {


                int code = response.code();
                if (response.isSuccessful()) {
                    playlistCallback.onPlaylistCreated();
                    Toast.makeText (mContext.getApplicationContext(), "¡Cancion añadida!" , Toast.LENGTH_SHORT) .show ();

                } else {
                    assert response.errorBody() != null;
                    Log.d("ERROR:  " ,response.errorBody().toString());

                }
            }

            @Override
            public void onFailure(@NotNull Call<Playlist> call, @NotNull Throwable t) {


            }
        });
    }
    public synchronized void getAllPlaylists ( final PlaylistCallback playlistCallback) {
        UserToken userToken = Session.getInstance((MainActivity) mContext).getUserToken();

        Call<List<Playlist>> call = mPlaylistService.getAllPlaylists(authHeader.getToken());

        call.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(@NotNull Call<List<Playlist>> call, @NotNull Response<List<Playlist>> response) {

                int code = response.code();
                if (response.isSuccessful()) {
                    playlistCallback.onPlaylistReceived(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Playlist>> call, @NotNull Throwable t) {


            }
        });

    }

    public synchronized void getMyPlaylists ( final PlaylistCallback playlistCallback) {
        UserToken userToken = Session.getInstance((MainActivity) mContext).getUserToken();

        Call<List<Playlist>> call = mPlaylistService.getAllPlaylists(authHeader.getToken());

        call.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(@NotNull Call<List<Playlist>> call, @NotNull Response<List<Playlist>> response) {

                int code = response.code();
                if (response.isSuccessful()) {
                    playlistCallback.onPlaylistReceived(response.body());
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
