package tk.alexlopez.sallefy.activities.access;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import tk.alexlopez.sallefy.R;

public class RecoveryPass  extends AppCompatActivity {

    private EditText etEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_pass);
        initViews();
    }

    private void sendMail(String mail) {
        // api and rend link re
    }

    private void initViews() {
        Button send = findViewById(R.id.recovery_button);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEmail = findViewById(R.id.mail_user);
                //Log.v("EditText value=", etEmail.getText().toString());

                if (etEmail.getText().toString().matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")){
                    sendMail(etEmail.getText().toString());

                }else{
                    dialog();
                }
            }
        });
    }

    protected void dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mail incorrect");
        builder.setMessage("Write a valid email");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", (dialog, which) -> {
        });
        builder.show();
    }

}