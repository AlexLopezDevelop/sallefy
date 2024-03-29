package tk.alexlopez.sallefy.network.service;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tk.alexlopez.sallefy.models.Genre;
import tk.alexlopez.sallefy.models.Track;


public interface GenreService {

    @GET("genres/{id}")
    Call<Genre> getGenreById(@Path("id") Integer id, @Header("Authorization") String token);

    @GET("genres")
    Call<List<Genre>> getAllGenres(@Header("Authorization") String token);

    @GET("genres/{id}/tracks")
    Call<List<Track>> getTracksByGenre(@Path("id") Integer id, @Header("Authorization") String token);
}

