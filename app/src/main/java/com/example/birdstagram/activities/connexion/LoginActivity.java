package com.example.birdstagram.activities.connexion;

import androidx.appcompat.app.AlertDialog;
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
        Cursor res = myDb.getConnectionUser(email.getText().toString(), password.getText().toString());
        if(res.getCount() == 0){
            Toast.makeText(getApplicationContext(), "Veuillez vérifier vos identifiants", Toast.LENGTH_LONG).show();
        }
        else{
            StringBuffer buffer = new StringBuffer();
            while(res.moveToNext()){
                buffer.append("Id :" + res.getString(0) + "\n" );
                buffer.append("Pseudo :" + res.getString(1) + "\n"  );
                buffer.append("Name :" + res.getString(2) + "\n"  );
                buffer.append("Surname :" + res.getString(3) + "\n"  );
                buffer.append("Age :" + res.getString(4) + "\n"  );
                buffer.append("Mail :" + res.getString(5) + "\n"  );
                buffer.append("Password :" + res.getString(6) + "\n"  );
            }
            showMessage("Database", buffer.toString());
        }

        /*if (res!= null || res.getCount() > 0){
            Toast.makeText(getApplicationContext(), "Aucun Utilisateur trouvé", Toast.LENGTH_LONG).show();
        } else{
            String nom = res.getString(res.getColumnIndex(DatabaseHelper.USER_NAME));
            Toast.makeText(getApplicationContext(), nom, Toast.LENGTH_LONG).show();
        }*/

    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
