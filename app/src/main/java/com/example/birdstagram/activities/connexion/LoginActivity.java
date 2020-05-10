package com.example.birdstagram.activities.connexion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.birdstagram.R;
import com.example.birdstagram.activities.inscription.SignUpActivity;
import com.example.birdstagram.tools.DatabaseHelper;

import static com.example.birdstagram.activities.MainActivity.myDb;

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

        connected_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserConnection(v);
            }
        });

        create_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initialiseAttributes(){
        email = findViewById(R.id.email_connexion);
        password = findViewById(R.id.password_connexion);
        connected_button = findViewById(R.id.button_connexion);
        create_account_button = findViewById(R.id.button_create_account);
    }

    private void onUserConnection(View view){
        myDb = new DatabaseHelper(this);
        Cursor data = myDb.getConnectionUser(email.getText().toString(), password.getText().toString());

        if (data!= null || data.getCount() > 0){
            Toast.makeText(getApplicationContext(), "Aucun Utilisateur trouvé", Toast.LENGTH_LONG).show();
        } else{
            String nom = data.getString(data.getColumnIndex(DatabaseHelper.USER_NAME));
            Toast.makeText(getApplicationContext(), nom, Toast.LENGTH_LONG).show();
        }

    }

}
