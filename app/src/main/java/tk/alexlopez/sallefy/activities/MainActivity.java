package tk.alexlopez.sallefy.activities;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import tk.alexlopez.sallefy.R;
import tk.alexlopez.sallefy.utils.Constants;


public class MainActivity extends AppCompatActivity {



    private Button btnStaticUrl;
    private Button btnLogin;
    private Button btnList;
    private Button btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        requestPermissions(); // Microphone permission for Audio Visualizer
        enableInitialButtons();
    }

    private void initViews() {
        btnStaticUrl = findViewById(R.id.btn_static_url);
        btnStaticUrl.setEnabled(false);
        btnStaticUrl.setOnClickListener(v -> {
        });

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setEnabled(false);
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivityForResult(intent, Constants.NETWORK.LOGIN_OK);
        });

        btnList = findViewById(R.id.btn_backend);
        btnList.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), DynamicPlaybackActivity.class);
            startActivity(intent);
        });

        btnUpload = findViewById(R.id.btn_upload);
        btnUpload.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
            startActivity(intent);
        });
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.MODIFY_AUDIO_SETTINGS}, Constants.PERMISSIONS.MICROPHONE);

        }
    }


    private void enableInitialButtons() {
        btnLogin.setEnabled(true);
        btnStaticUrl.setEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void enableNetworkButtons() {
        btnUpload.setEnabled(true);
        btnList.setEnabled(true);
        btnLogin.setEnabled(false);
    }

    private void enableAllButtons() {
        btnUpload.setEnabled(true);
        btnList.setEnabled(true);
        btnLogin.setEnabled(true);
        btnStaticUrl.setEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.NETWORK.LOGIN_OK) {
            enableNetworkButtons();
            if (resultCode == RESULT_OK) {
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

