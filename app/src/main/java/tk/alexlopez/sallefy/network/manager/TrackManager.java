package tk.alexlopez.sallefy.network.manager;

import android.content.Context;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tk.alexlopez.sallefy.models.Playback;
import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.models.Track;
import tk.alexlopez.sallefy.models.TrackLike;
import tk.alexlopez.sallefy.models.UserToken;
import tk.alexlopez.sallefy.network.callback.TrackCallback;
import tk.alexlopez.sallefy.network.service.TrackService;
import tk.alexlopez.sallefy.utils.AuthenticationHeader;
import tk.alexlopez.sallefy.utils.Constants;
import tk.alexlopez.sallefy.utils.Session;

public class TrackManager {

    private static final String TAG = "TrackManager";
    private Context mContext;
    private static TrackManager sTrackManager;
    private Retrofit mRetrofit;
    private TrackService mTrackService;
    private AuthenticationHeader authHeader = AuthenticationHeader.Companion.getInstance();


    public static TrackManager getInstance(Context context) {
        if (sTrackManager == null) {
            sTrackManager = new TrackManager(context);
        }

        return sTrackManager;
    }

    public TrackManager(Context context) {
        mContext = context;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.NETWORK.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mTrackService = mRetrofit.create(TrackService.class);
    }

    public synchronized void getAllTracks(final TrackCallback trackCallback) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();

        Call<List<Track>> call = mTrackService.getAllTracks(authHeader.getToken());
        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    trackCallback.onTracksReceived(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    trackCallback.onNoTracks(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                trackCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });
    }

    public synchronized void getAllMyPlaylists(final TrackCallback trackCallback) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();

        Call<List<Playlist>> call = mTrackService.getAllMyPlaylists(authHeader.getToken());
        call.enqueue(new Callback<List<Playlist>>() {

            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    trackCallback.onPlaylistsReceived(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    trackCallback.onNoTracks(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                trackCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });
    }

    public synchronized void getAllTracksByPlaylistId(int playlistId, final TrackCallback trackCallback) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();

        Call<Playlist> call = mTrackService.getAllTracksByPlaylistId(playlistId, authHeader.getToken());
        call.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    trackCallback.onTracksReceivedByPlaylistId(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    trackCallback.onNoTracks(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }


            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                trackCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });
    }

    public synchronized void getAllPlaylists(final TrackCallback trackCallback) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();
        Call<List<Playlist>> call = mTrackService.getAllPlaylists(authHeader.getToken());
        call.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                trackCallback.onPlaylistsReceived(response.body());
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                trackCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });
    }

    public synchronized void updatePlaylist(final TrackCallback trackCallback, Playlist playlist) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();
        Call<Playlist> call = mTrackService.updatePlaylist("Bearer " + userToken.getIdToken(), playlist);
        call.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    trackCallback.onPlaylistUpdated(true);
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    trackCallback.onNoTracks(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                trackCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });
    }

    public synchronized void getUserTracks(String login, final TrackCallback trackCallback) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();

        Call<List<Track>> call = mTrackService.getUserTracks(login, authHeader.getToken());
        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    trackCallback.onUserTracksReceived(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    trackCallback.onNoTracks(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                trackCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });
    }

    public Observable<TrackLike> userLikeTrack(int idTrack) {

        return mTrackService.userLikeTrack(idTrack, getAuthToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public synchronized void getOwnTracks(final TrackCallback trackCallback) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();
        Call<List<Track>> call = mTrackService.getOwnTracks(authHeader.getToken());
        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {

                int code = response.code();
                if (response.isSuccessful()) {
                    trackCallback.onPersonalTracksReceived((ArrayList<Track>) response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    trackCallback.onNoTracks(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                trackCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });
    }

    public synchronized void createTrack(Track track, final TrackCallback trackCallback) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();

        Call<ResponseBody> call = mTrackService.createTrack(track, authHeader.getToken());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int code = response.code();
                if (response.isSuccessful()) {
                    trackCallback.onCreateTrack();
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    trackCallback.onFailure(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                trackCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });
    }

    public Observable<TrackLike> getTrackLike(int idTrack) {
        return mTrackService.getTrackLike(idTrack, getAuthToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    private String getAuthToken() {
        UserToken userToken = Session.getInstance(mContext).getUserToken();
        String idToken = String.format("Bearer %s", userToken.getIdToken());

        return idToken;
    }

    public synchronized void getTopTracks(final TrackCallback trackCallback, boolean liked, int size) {
        if (size < 0) {
            return;
        }

        Call<List<Track>> call = mTrackService.getTopTracks(authHeader.getToken(), liked, size);

        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    trackCallback.onTracksReceived(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    trackCallback.onNoTracks(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                trackCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });
    }

    public void getMoreTracksFollowed(final TrackCallback trackCallback, boolean popular, int size) {
        if (size < 0) {
            return;
        }

        Call<List<Track>> call = mTrackService.getMoreTracksFollowed(authHeader.getToken(), popular, size);

        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    trackCallback.onTracksReceived(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    trackCallback.onNoTracks(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                trackCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });
    }

    public void getPlaybackByTrackId(final TrackCallback trackCallback, int trackId) {
        Call<List<Playback>> call = mTrackService.getPlaybackByTrackId(authHeader.getToken(), trackId);
        call.enqueue(new Callback<List<Playback>>() {
            @Override
            public void onResponse(Call<List<Playback>> call, Response<List<Playback>> response) {
                int code = response.code();

                if (response.isSuccessful()) {
                    trackCallback.onPlaybackReceived(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                    trackCallback.onNoTracks(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Playback>> call, Throwable t) {
                Log.d(TAG, "Error Failure: " + t.getStackTrace());
                trackCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });
    }

    public Observable<Boolean> delete(int playlistId, @Nullable Integer trackId) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {

            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {

                mTrackService.getAllTracksByPlaylistIdStream(playlistId, authHeader.getToken())
                        .map(playlist -> {
                            List<Track> tracks = playlist.getTracks();
                            if (tracks != null && tracks.size() > 0) {
                                for (int i = 0; i < tracks.size(); i++) {
                                    Track track = tracks.get(i);
                                    if (track.getId().equals(trackId)) {
                                        tracks.remove(i);
                                        playlist.setTracks(tracks);

                                        return playlist;
                                    }
                                }
                            }
                            throw new Exception("Not found");
                        }).subscribe(modifiedPlaylist -> {

                    mTrackService.updatePlaylistStream(authHeader.getToken(), modifiedPlaylist)
                            .subscribe(updatedPlaylist -> {

                                emitter.onNext(updatedPlaylist != null);
                                emitter.onComplete();
                            }, err -> {
                                emitter.onError(err);
                            });

                }, err -> {
                    emitter.onError(err);
                });
            }
        });

    }
}
