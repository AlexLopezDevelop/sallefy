package tk.alexlopez.sallefy.network.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import tk.alexlopez.sallefy.models.UserLogin;
import tk.alexlopez.sallefy.models.UserToken;

public interface UserTokenService {

    @POST("authenticate")
    Call<UserToken> loginUser(@Body UserLogin login);

}
