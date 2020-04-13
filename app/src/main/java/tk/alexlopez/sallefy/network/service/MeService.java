package tk.alexlopez.sallefy.network.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import tk.alexlopez.sallefy.models.Playlist;


public interface MeService {

    @GET("me/playlists")
    Call<List<Playlist>> getMyPlaylist(@Header("Authorization") String token);

}
