package tk.alexlopez.sallefy.utils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import tk.alexlopez.sallefy.R;

public class User_option extends AppCompatActivity {

    private Intent sendIntent = new Intent();
    private LinearLayout linearLayout;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareContent();
    }

    public void shareContent() {

        linearLayout = findViewById(R.id.share_layout);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendIntent.setType("text/plain");

                sendIntent.setAction(Intent.ACTION_SEND);

                sendIntent.putExtra(Intent.EXTRA_TEXT, "ehyyj");

                sendIntent.setType("urll");

                Intent shareIntent = Intent.createChooser(sendIntent, null);

                startActivity(shareIntent);
            }
        });
    }
}
