package tk.alexlopez.sallefy.activities.access;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import tk.alexlopez.sallefy.R;

public class RecoveryPass  extends AppCompatActivity {

    private EditText etEmail;
    private Button send;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_pass);
        initViews();
    }

    protected void onClick(View view){

    }

    private void sendMail() {


    }
    private void initViews() {
        send = findViewById(R.id.recovery_button);
        etEmail = findViewById(R.id.mail_user);
        final String[] mail = {""};
        System.out.println(mail);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail[0] = etEmail.getText().toString();
            }
        });
    }

}