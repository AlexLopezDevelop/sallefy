package tk.alexlopez.sallefy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

public class music_player extends AppCompatActivity {

    ConstraintLayout clMainLayout;
    AnimationDrawable adAnimationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        init();
        layoutAnimation();
    }

    private void init() {
        clMainLayout = findViewById(R.id.music_player);
    }

    private void layoutAnimation() {
        adAnimationDrawable = (AnimationDrawable) clMainLayout.getBackground();
        adAnimationDrawable.setEnterFadeDuration(4500);
        adAnimationDrawable.setExitFadeDuration(4500);
        adAnimationDrawable.start();
    }
}
