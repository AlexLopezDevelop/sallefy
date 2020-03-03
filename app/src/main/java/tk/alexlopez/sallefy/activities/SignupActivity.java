package tk.alexlopez.sallefy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import tk.alexlopez.sallefy.R;

public class SignupActivity extends AppCompatActivity {

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
        etEmail = findViewById(R.id.sign_up_email);
        etPassword = findViewById(R.id.sign_up_password);
        btSignUp = findViewById(R.id.sign_up);
        tvBackLogin = findViewById(R.id.sign_up_login);

        //onClick Listeners
        tvBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
