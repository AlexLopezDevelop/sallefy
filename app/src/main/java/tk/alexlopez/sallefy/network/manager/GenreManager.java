package tk.alexlopez.sallefy.network.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tk.alexlopez.sallefy.fragments.UploadFragment;
import tk.alexlopez.sallefy.models.Genre;
import tk.alexlopez.sallefy.models.Track;
import tk.alexlopez.sallefy.models.UserToken;
import tk.alexlopez.sallefy.network.callback.GenreCallback;
import tk.alexlopez.sallefy.network.service.GenreService;
import tk.alexlopez.sallefy.utils.AuthenticationHeader;
import tk.alexlopez.sallefy.utils.Constants;
import tk.alexlopez.sallefy.utils.Session;


public class GenreManager {

    private static final String TAG = "genreManager";
    @SuppressLint("StaticFieldLeak")
    private static GenreManager sGenreManager;
    private Context mContext;
    private GenreService mService;
    private AuthenticationHeader authHeader = AuthenticationHeader.Companion.getInstance();

    public static GenreManager getInstance(Context context) {
        if (sGenreManager == null) {
            sGenreManager = new GenreManager(context);
        }
        return sGenreManager;
    }

    private GenreManager(Context cntxt) {
        mContext = cntxt;
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.NETWORK.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = mRetrofit.create(GenreService.class);
    }

    public synchronized void getAllGenres(final GenreCallback genreCallback) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();

        Call<List<Genre>> call = mService.getAllGenres(authHeader.getToken());
        call.enqueue(new Callback<List<Genre>>() {
            @Override
            public void onResponse(@NotNull Call<List<Genre>> call, @NotNull Response<List<Genre>> response) {
                int code = response.code();
                ArrayList<Genre> data = (ArrayList<Genre>) response.body();

                if (response.isSuccessful()) {
                    genreCallback.onGenresReceive(data);

                } else {
                    Log.d(TAG, "Error: " + code);
                    genreCallback.onFailure(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Genre>> call, @NotNull Throwable t) {
                Log.d(TAG, "Error: " + t);
                genreCallback.onFailure(new Throwable("ERROR " + t.getMessage() ));
            }
        });
    }

    public synchronized void getTracksByGenre(int genreId, final GenreCallback genreCallback) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();

        Call<List<Track>> call = mService.getTracksByGenre( genreId, authHeader.getToken());
        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(@NotNull Call<List<Track>> call, @NotNull Response<List<Track>> response) {
                int code = response.code();
                ArrayList<Track> data = (ArrayList<Track>) response.body();

                if (response.isSuccessful()) {
                    genreCallback.onTracksByGenre(data);

                } else {
                    Log.d(TAG, "Error: " + code);
                    genreCallback.onFailure(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Track>> call, @NotNull Throwable t) {
                Log.d(TAG, "Error: " + t);
                genreCallback.onFailure(new Throwable("ERROR " + t.getMessage() ));
            }
        });

    }

}