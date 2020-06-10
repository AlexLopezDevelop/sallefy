package tk.alexlopez.sallefy.network.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import tk.alexlopez.sallefy.models.Playlist;


public interface PlaylistService {

    @GET("playlists")
    Call<Playlist> getPlaylist(@Body int id, @Header("Authorization") String token);

    @PUT("playlists")
    Call<Playlist> updatePlaylist(@Body Playlist playlist, @Header("Authorization") String token);

    @POST("playlists")
    Call<Playlist> createPlaylist(@Body Playlist playlist, @Header("Authorization") String token);

    @GET("playlists")
    Call<List<Playlist>> getAllPlaylists(@Header("Authorization") String token);
}
