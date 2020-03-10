package tk.alexlopez.sallefy.network.service;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.models.Track;


public interface PlaylistService {

    @POST("playlists")
    Call<Playlist> createPlaylist(@Body Playlist playlist, @Header("Authorization") String token);

    @GET("playlists")
    Call<List<Playlist>> getAllPlaylists(@Header("Authorization") String token);

}
