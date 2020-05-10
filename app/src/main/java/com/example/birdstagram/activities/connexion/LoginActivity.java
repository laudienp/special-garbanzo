package com.example.birdstagram.activities.connexion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.birdstagram.R;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button connected_button;
    private Button create_account_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialiseAttributes();
    }

    private void initialiseAttributes(){
        email = findViewById(R.id.email_connexion);
        password = findViewById(R.id.password_connexion);
        connected_button = findViewById(R.id.button_connexion);
        create_account_button = findViewById(R.id.button_create_account);
    }

}
