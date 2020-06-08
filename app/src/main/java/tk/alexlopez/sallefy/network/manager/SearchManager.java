package tk.alexlopez.sallefy.network.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextWatcher;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tk.alexlopez.sallefy.fragments.SearchFragment;
import tk.alexlopez.sallefy.models.Playlist;
import tk.alexlopez.sallefy.models.Search;
import tk.alexlopez.sallefy.models.UserToken;
import tk.alexlopez.sallefy.network.callback.PlaylistCallback;
import tk.alexlopez.sallefy.network.callback.SearchCallback;
import tk.alexlopez.sallefy.network.service.PlaylistService;
import tk.alexlopez.sallefy.network.service.SearchService;
import tk.alexlopez.sallefy.utils.AuthenticationHeader;
import tk.alexlopez.sallefy.utils.Constants;
import tk.alexlopez.sallefy.utils.Session;

public class SearchManager {
    private static final String TAG = "PlaylistManager";

    @SuppressLint("StaticFieldLeak")
    private static SearchManager sSearchManager;
    private Context mContext;
    private SearchService mSearchService;
    private AuthenticationHeader authHeader = AuthenticationHeader.Companion.getInstance();


    public static SearchManager getInstance(Context context) {
        if (sSearchManager == null) {
            sSearchManager = new SearchManager(context);
        }
        return sSearchManager;
    }

    private SearchManager(Context cntxt) {
        mContext = cntxt;
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.NETWORK.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mSearchService = mRetrofit.create(SearchService.class);
    }
    public synchronized void searchByKeyword ( String keyword, final SearchCallback searchCallback) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();

        Call<Search> call = mSearchService.searchByKeyword( keyword,authHeader.getToken());

        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(@NotNull Call<Search> call, @NotNull Response<Search> response) {

                int code = response.code();
                if (response.isSuccessful()) {
                    searchCallback.onTracksReceived(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                }
            }
            @Override
            public void onFailure(@NotNull Call<Search> call, @NotNull Throwable t) {
                Log.d(TAG, "OnFailure");
            }
        });

    }
}
