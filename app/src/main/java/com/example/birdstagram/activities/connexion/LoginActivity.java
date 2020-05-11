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
import com.example.birdstagram.activities.MapActivity;
import com.example.birdstagram.activities.inscription.SignUpActivity;
import com.example.birdstagram.data.tools.User;
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
        User user = new User();
        Cursor res = myDb.getConnectionUser(email.getText().toString(), password.getText().toString());
        if(res.getCount() == 0){
            Toast.makeText(getApplicationContext(), "Veuillez vérifier vos identifiants", Toast.LENGTH_LONG).show();
        }
        else{
            while(res.moveToNext()){
                String userID = res.getString(0);
                String userPseudo = res.getString(1);
                String userName = res.getString(2);
                String userSurname = res.getString(3);
                String userAge = res.getString(4);
                String userMail = res.getString(5);
                String userPassword = res.getString(6);
                user = new User(Integer.parseInt(userID), userPseudo, userName, userSurname, Integer.parseInt(userAge), userMail, userPassword);
            }
            Intent intent = new Intent(getApplicationContext(), MapActivity.class);
            intent.putExtra("User Session", user);
            startActivity(intent);

            /*StringBuffer buffer = new StringBuffer();
            buffer.append("Id :" + user.getId() + "\n" );
            buffer.append("Pseudo :" + user.getPseudo() + "\n"  );
            buffer.append("Name :" + user.getName() + "\n"  );
            buffer.append("Surname :" + user.getSurname() + "\n"  );
            buffer.append("Age :" + user.getAge() + "\n"  );
            buffer.append("Mail :" + user.getMail() + "\n"  );
            buffer.append("Password :" + user.getPassword() + "\n"  );
            showMessage("Database", buffer.toString());*/
        }

    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
