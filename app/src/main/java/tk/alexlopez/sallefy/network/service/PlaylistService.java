package tk.alexlopez.sallefy.network.service;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import tk.alexlopez.sallefy.models.Playlist;


public interface PlaylistService {

    @POST("playlists")
    Call<Playlist> createPlaylist(@Body String name, @Header("Authorization") String token);

}
