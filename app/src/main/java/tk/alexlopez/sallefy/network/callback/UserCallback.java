package tk.alexlopez.sallefy.network.callback;

import tk.alexlopez.sallefy.models.User;
import tk.alexlopez.sallefy.models.UserToken;

public interface UserCallback extends FailureCallback {
    void onLoginSuccess(UserToken userToken);
    void onLoginFailure(Throwable throwable);
    void onRegisterSuccess();
    void onRegisterFailure(Throwable throwable);
    void onUserInfoReceived(User userData);
}
