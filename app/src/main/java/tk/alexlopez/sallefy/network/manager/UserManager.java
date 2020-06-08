package tk.alexlopez.sallefy.network.manager;

import android.content.Context;
import android.util.Log;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
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
import tk.alexlopez.sallefy.activities.charts.TopUserTracksActivity;
import tk.alexlopez.sallefy.models.User;
import tk.alexlopez.sallefy.models.UserLogin;
import tk.alexlopez.sallefy.models.UserRegister;
import tk.alexlopez.sallefy.models.UserToken;
import tk.alexlopez.sallefy.network.callback.TrackCallback;
import tk.alexlopez.sallefy.network.callback.UserCallback;
import tk.alexlopez.sallefy.network.service.UserService;
import tk.alexlopez.sallefy.network.service.UserTokenService;
import tk.alexlopez.sallefy.utils.AuthenticationHeader;
import tk.alexlopez.sallefy.utils.Constants;
import tk.alexlopez.sallefy.utils.Session;

public class UserManager {

    private static final String TAG = "UserManager";

    private static UserManager sUserManager;
    private Retrofit mRetrofit;
    private Context mContext;
    private UserService mService;
    private UserTokenService mTokenService;
    private AuthenticationHeader authHeader = AuthenticationHeader.Companion.getInstance();

    public static UserManager getInstance(Context context) {
        if (sUserManager == null) {
            sUserManager = new UserManager(context);
        }
        return sUserManager;
    }

    private UserManager(Context cntxt) {
        mContext = cntxt;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.NETWORK.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = mRetrofit.create(UserService.class);
        mTokenService = mRetrofit.create(UserTokenService.class);
    }


    /********************   LOGIN    ********************/
    public Observable<UserToken> loginAttempt(String username, String password) {
        return mTokenService.loginUser(
                new UserLogin(username, password, true)
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /********************   USER INFO    ********************/
    public synchronized void getUserData(String login, final UserCallback userCallback) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();
        Call<User> call = mService.getUserById(login, authHeader.getToken());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                int code = response.code();
                if (response.isSuccessful()) {
                    userCallback.onUserInfoReceived(response.body());
                } else {
                    Log.d(TAG, "Error NOT SUCCESSFUL: " + response.toString());
                    userCallback.onFailure(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "Error: " + t.getMessage());
                userCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });
    }


    /********************   REGISTRATION    ********************/
    public synchronized void registerAttempt(String email, String username, String password, final UserCallback userCallback) {

        Call<ResponseBody> call = mService.registerUser(new UserRegister(email, username, password));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                int code = response.code();
                if (response.isSuccessful()) {
                    userCallback.onRegisterSuccess();
                } else {
                    userCallback.onRegisterFailure(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                userCallback.onFailure(t);
            }
        });
    }

    public void getAccount(final TrackCallback trackCallback) {
        UserToken userToken = Session.getInstance(mContext).getUserToken();
        Call<User> call = mService.getUser(authHeader.getToken());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                int code = response.code();
                if (response.isSuccessful()) {
                    trackCallback.onUserInfoReceived(response.body());
                } else {
                    Log.d(TAG, "Error NOT SUCCESSFUL: " + response.toString());
                    trackCallback.onFailure(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "Error: " + t.getMessage());
                trackCallback.onFailure(new Throwable("ERROR " + t.getStackTrace()));
            }
        });
    }


    /********************   GETTERS / SETTERS    ********************/


}
