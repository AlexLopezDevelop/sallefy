package tk.alexlopez.sallefy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.models.User;
import tk.alexlopez.sallefy.models.UserToken;
import tk.alexlopez.sallefy.network.callback.UserCallback;
import tk.alexlopez.sallefy.network.manager.UserManager;
import tk.alexlopez.sallefy.utils.Session;

public class LoginActivity extends AppCompatActivity implements UserCallback {

    private VideoView vvLogin;
    private EditText etUsername;
    private EditText etPassword;
    private TextView tvForgotPassword;
    private Button btLogin;
    private TextView tvSignUp;
    private ImageView ivBackground;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // Set background image
        ivBackground = findViewById(R.id.login_background);
        ivBackground .setVisibility(View.VISIBLE);
    }

    private void initViews() {

        // Component association
        vvLogin = findViewById(R.id.videoLogin);
        etUsername = findViewById(R.id.login_username);
        etPassword = findViewById(R.id.login_password);
        tvForgotPassword = findViewById(R.id.login_forgot_password);
        btLogin = findViewById(R.id.login_button);
        tvSignUp = findViewById(R.id.login_sign_up);

        //onClick Listeners

        // Go to registration page
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        // Start login
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });


        // Video background configuration
        String videoLoginPath = "android.resource://"+getPackageName()+"/"+R.raw.login_video;
        vvLogin.setVideoPath(videoLoginPath);
        vvLogin.start();

    }

    private void doLogin(String username, String userpassword) {
        UserManager.getInstance(getApplicationContext())
                .loginAttempt(username, userpassword, LoginActivity.this);
    }

    @Override
    public void onLoginSuccess(UserToken userToken) {
        Session.getInstance(getApplicationContext())
                .setUserToken(userToken);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginFailure(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRegisterSuccess() {

    }

    @Override
    public void onRegisterFailure(Throwable throwable) {

    }

    @Override
    public void onUserInfoReceived(User userData) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }

}
