package tk.alexlopez.sallefy.network.service;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.models.Track;
import tk.alexlopez.sallefy.models.TrackLike;

public interface TrackService {

    @GET("me/tracks")
    Call<List<Track>> getOwnTracks(@Header("Authorization") String token);

    @GET("users/{login}/tracks")
    Call<List<Track>> getUserTracks(@Path("login") String login, @Header("Authorization") String token);

    @POST("tracks")
    Call<ResponseBody> createTrack(@Body Track track, @Header("Authorization") String token);

    @GET("playlists/{id}/")
    Call<Playlist> getAllTracksByPlaylistId(@Path("id") int id, @Header("Authorization") String token);

    @PUT("tracks/{id}/like")
    Observable<TrackLike> userLikeTrack(@Path("id") int id, @Header("Authorization") String token);

    @GET("tracks/{id}/like")
    Observable<TrackLike> getTrackLike(@Path("id") int idTrack, @Header("Authorization") String authToken);

    @PUT("playlists")
    Call<Playlist> updatePlaylist(@Header("Authorization") String authToken, @Body Playlist playlist);

    @GET("playlists")
    Call<List<Playlist>> getAllPlaylists(@Header("Authorization") String authToken);

    @GET("me/playlists")
    Call<List<Playlist>> getAllMyPlaylists(@Header("Authorization") String authToken);

    @GET("tracks")
    Call<List<Track>> getAllTracks(@Header("Authorization") String token);

    @GET("tracks")
    Call<List<Track>> getTopTracks(@Header("Authorization") String token, @Query("liked") boolean liked, @Query("size") int size);

    @GET("playlists")
    Call<List<Track>> getMoreTracksFollowed(@Header("Authorization") String token, @Query("popular") boolean popular, @Query("size") int size);
}
