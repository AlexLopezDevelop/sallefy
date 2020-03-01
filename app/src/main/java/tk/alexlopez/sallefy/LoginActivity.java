package tk.alexlopez.sallefy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.VideoView;

public class LoginActivity extends AppCompatActivity {

    VideoView mVideoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mVideoLogin = findViewById(R.id.videoLogin);

        String videoLoginPath = "android.resource://"+getPackageName()+"/"+R.raw.login_video;
        mVideoLogin.setVideoPath(videoLoginPath);
        mVideoLogin.start();
    }
}
