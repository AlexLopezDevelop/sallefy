package tk.alexlopez.sallefy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import tk.alexlopez.sallefy.R;

public class LoginActivity extends AppCompatActivity {

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
        // TODO: Create login
    }
}
