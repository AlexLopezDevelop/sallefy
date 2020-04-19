package tk.alexlopez.sallefy.network.manager;

import android.content.Context;
import android.text.TextWatcher;
import android.util.Log;

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
import tk.alexlopez.sallefy.utils.Constants;
import tk.alexlopez.sallefy.utils.Session;

public class SearchManager {
    private static final String TAG = "PlaylistManager";

    private static SearchManager sSearchManager;
    private Retrofit mRetrofit;
    private Context mContext;

    private SearchService mSearchService;


    public static SearchManager getInstance(Context context) {
        if (sSearchManager == null) {
            sSearchManager = new SearchManager(context);
        }
        return sSearchManager;
    }

    private SearchManager(Context cntxt) {
        mContext = cntxt;
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.NETWORK.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mSearchService = mRetrofit.create(SearchService.class);
    }
    public synchronized void searchByKeyword ( String keyword, final SearchCallback searchCallback) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();

        Call<Search> call = mSearchService.searchByKeyword( keyword,"Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {

                int code = response.code();
                if (response.isSuccessful()) {
                    searchCallback.onTracksReceived(response.body());
                } else {
                    Log.d(TAG, "Error Not Successful: " + code);
                }
            }
            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                Log.d(TAG, "OnFailure");
            }
        });

    }
}
