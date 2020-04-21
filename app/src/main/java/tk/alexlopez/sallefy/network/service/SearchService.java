package tk.alexlopez.sallefy.network.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.models.Search;

public interface SearchService {
    @GET("search")
    Call<Search> searchByKeyword(@Query("keyword") String keyword , @Header("Authorization") String token);
}

