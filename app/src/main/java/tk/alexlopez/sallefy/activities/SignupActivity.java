package tk.alexlopez.sallefy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.models.User;
import tk.alexlopez.sallefy.models.UserRegister;
import tk.alexlopez.sallefy.models.UserToken;
import tk.alexlopez.sallefy.network.callback.UserCallback;
import tk.alexlopez.sallefy.network.manager.UserManager;
import tk.alexlopez.sallefy.utils.Session;

public class SignupActivity extends AppCompatActivity implements UserCallback {

    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private Button btSignUp;
    private TextView tvBackLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initViews();
    }

    private void initViews() {

        // Component association
        etUsername = findViewById(R.id.sign_up_username);
        etEmail = findViewById(R.id.recovery_email);
        etPassword = findViewById(R.id.sign_up_password);
        btSignUp = findViewById(R.id.sign_up);
        tvBackLogin = findViewById(R.id.sign_up_login);

        //onClick Listeners

        // back to login activity
        tvBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String email = etEmail.getText().toString();
                Session.getInstance(getApplicationContext()).setUserRegister(new UserRegister(email, username, password));
                UserManager.getInstance(getApplicationContext()).registerAttempt(email, username, password, SignupActivity.this);
            }
        });
    }

    private void doLogin(String username, String userpassword) {
        UserManager.getInstance(getApplicationContext())
                .loginAttempt(username, userpassword, SignupActivity.this);
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
        Session.getInstance(getApplicationContext())
                .setUserRegister(null);
    }

    @Override
    public void onRegisterSuccess() {
        UserRegister userData = Session.getInstance(getApplicationContext()).getUserRegister();
        doLogin(userData.getLogin(), userData.getPassword());
    }

    @Override
    public void onRegisterFailure(Throwable throwable) {
        Session.getInstance(getApplicationContext())
                .setUserRegister(null);
        Toast.makeText(getApplicationContext(), "Register failed", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onUserInfoReceived(User userData) {

    }

    @Override
    public void onFailure(Throwable throwable) {

    }
}
