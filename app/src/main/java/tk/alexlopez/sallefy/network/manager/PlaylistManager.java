package tk.alexlopez.sallefy.network.manager;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
import tk.alexlopez.sallefy.utils.Constants;
import tk.alexlopez.sallefy.utils.Session;



public class PlaylistManager {

    private static final String TAG = "PlaylistManager";

    private static PlaylistManager sPlaylistManager;
    private Retrofit mRetrofit;
    private Context mContext;
    private PlaylistService mService;
    private UserToken userToken;

    private PlaylistService mPlaylistService;

    //private UserService mService;
    // private UserTokenService mTokenService;


    public static PlaylistManager getInstance(Context context) {
        if (sPlaylistManager == null) {
            sPlaylistManager = new PlaylistManager(context);
        }
        return sPlaylistManager;
    }

    private PlaylistManager(Context cntxt) {
        mContext = cntxt;
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.NETWORK.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mPlaylistService = mRetrofit.create(PlaylistService.class);
    }

    /********************   CREATE PLAYLIST    ********************/
    public synchronized void createPlaylist (String name, final PlaylistCallback playlistCallback) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();

        Call<Playlist> call = mPlaylistService.createPlaylist(new Playlist(name),"Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {

                int code = response.code();
                if (response.isSuccessful()) {
                    playlistCallback.onPlaylistcreated();
                    Toast.makeText (mContext.getApplicationContext(), "¡Playlist creada!" , Toast.LENGTH_SHORT) .show ();

                } else {
                    Log.d("ERROR:  " ,response.errorBody().toString());
                    Toast.makeText (mContext.getApplicationContext(), "¡Error al crear la playlist!" , Toast.LENGTH_SHORT) .show ();

                    //userCallback.onRegisterFailure(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                Toast.makeText (mContext.getApplicationContext(), "¡Error al crear la playlist!" , Toast.LENGTH_SHORT) .show ();
            }
        });
    }
    /********************   ADD SONG TO PLAYLIST    ********************/
    public synchronized void updatePlaylist (Playlist playlist, final PlaylistCallback playlistCallback) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();

        Call<Playlist> call = mPlaylistService.updatePlaylist(playlist,"Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {


                int code = response.code();
                if (response.isSuccessful()) {
                    playlistCallback.onPlaylistcreated();
                    Toast.makeText (mContext.getApplicationContext(), "¡Cancion añadida!" , Toast.LENGTH_SHORT) .show ();

                } else {
                    Log.d("ERROR:  " ,response.errorBody().toString());

                }
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {


            }
        });
    }
    public synchronized void getAllPlaylists ( final PlaylistCallback playlistCallback) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();

        Call<List<Playlist>> call = mPlaylistService.getAllPlaylists("Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {

                int code = response.code();
                if (response.isSuccessful()) {
                    playlistCallback.onPlaylistReceived(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    //userCallback.onRegisterFailure(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {


            }
        });

    }

    public synchronized void getMyPlaylists ( final PlaylistCallback playlistCallback) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();

        Call<List<Playlist>> call = mPlaylistService.getAllPlaylists("Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {

                int code = response.code();
                if (response.isSuccessful()) {
                    playlistCallback.onPlaylistReceived(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    //userCallback.onRegisterFailure(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {


            }
        });

    }

    public synchronized void getPlaylistById(Integer id, final PlaylistCallback playlistCallback) {
        Call<Playlist> call = mService.getPlaylist(id, "Bearer " + userToken.getIdToken());
        call.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                int code = response.code();
                if (response.isSuccessful()) {
                    playlistCallback.onPlaylistById(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    playlistCallback.onFailure(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                playlistCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });
    }
}
