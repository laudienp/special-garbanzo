package com.example.birdstagram.activities.inscription;

import com.example.birdstagram.R;
import com.example.birdstagram.activities.MainActivity;
import com.example.birdstagram.data.tools.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.birdstagram.activities.MainActivity.myDb;

public class SignUpActivity extends AppCompatActivity implements View.OnFocusChangeListener {
    private EditText name;
    private EditText surname;
    private EditText age;
    private EditText pseudo;
    private EditText email;
    private EditText password;
    private EditText checkPassword;
    private Button signupButton;

    private TextView pseudoLabel;
    private TextView nameLabel;
    private TextView surnameLabel;
    private TextView ageLabel;
    private TextView emailLabel;
    private TextView passwordLabel;
    private TextView checkPasswprdLabel;

    public SignUpActivity() {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initializeAttributes();
        setAllOnFocusListener();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Drawable boarder = getDrawable(R.drawable.boarder);
        Drawable square = getDrawable(R.drawable.square);
        switch (v.getId()) {
            case R.id.pseudo:
                if (hasFocus) {
                    pseudoLabel.setVisibility(View.VISIBLE);
                    pseudo.setBackground(boarder);
                    pseudo.setHint(" ");
                } else  if (pseudo.getText().length() != 0) {
                    pseudo.setBackground(square);
                } else {
                    pseudoLabel.setVisibility(View.INVISIBLE);
                    pseudo.setBackground(square);
                    pseudo.setHint("Pseudo");
                }
                break;
            case R.id.name:
                if (hasFocus) {
                    nameLabel.setVisibility(View.VISIBLE);
                    name.setBackground(boarder);
                    name.setHint(" ");
                } else  if (name.getText().length() != 0) {
                    name.setBackground(square);
                } else {
                    nameLabel.setVisibility(View.INVISIBLE);
                    name.setBackground(square);
                    name.setHint("Name");
                }
                break;
            case R.id.surname:
                if (hasFocus) {
                    surnameLabel.setVisibility(View.VISIBLE);
                    surname.setBackground(boarder);
                    surname.setHint(" ");
                } else  if (surname.getText().length() != 0) {
                    surname.setBackground(square);
                } else {
                    surnameLabel.setVisibility(View.INVISIBLE);
                    surname.setBackground(square);
                    surname.setHint("Surname");
                }
                break;
            case R.id.age:
                if (hasFocus) {
                    ageLabel.setVisibility(View.VISIBLE);
                    age.setBackground(boarder);
                    age.setHint(" ");
                } else  if (age.getText().length() != 0) {
                    age.setBackground(square);
                } else {
                    ageLabel.setVisibility(View.INVISIBLE);
                    age.setBackground(square);
                    age.setHint("Age");
                }
                break;
            case R.id.email:
                if (hasFocus) {
                    emailLabel.setVisibility(View.VISIBLE);
                    email.setBackground(boarder);
                    email.setHint(" ");
                } else if (email.getText().length() != 0) {
                    email.setBackground(square);
                } else {
                    emailLabel.setVisibility(View.INVISIBLE);
                    email.setBackground(square);
                    email.setHint("Email");

                }

                break;
            case R.id.password:
                if (hasFocus) {
                    passwordLabel.setVisibility(View.VISIBLE);
                    password.setBackground(boarder);
                    password.setHint(" ");

                } else if (password.getText().length() != 0) {
                    password.setBackground(square);

                } else {
                    passwordLabel.setVisibility(View.INVISIBLE);
                    password.setBackground(square);
                    password.setHint("Mot de passe");

                }

                break;
            case R.id.confirmPassword:
                if (hasFocus) {
                    checkPasswprdLabel.setVisibility(View.VISIBLE);
                    checkPassword.setBackground(boarder);
                    checkPassword.setHint(" ");

                } else if (checkPassword.getText().length() != 0) {
                    checkPassword.setBackground(square);
                } else {
                    checkPasswprdLabel.setVisibility(View.INVISIBLE);
                    checkPassword.setBackground(square);
                    checkPassword.setHint("Cofirmer le mot de passe");

                }
                break;
        }
        if (v.getId() == R.id.signup && hasFocus){
            InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    void initializeAttributes() {
        pseudo = findViewById(R.id.pseudo);
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        age = findViewById(R.id.age);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        checkPassword = findViewById(R.id.confirmPassword);
        signupButton = findViewById(R.id.signUpButton);

        nameLabel =  findViewById(R.id.name_textView);
        surnameLabel =  findViewById(R.id.surname_textView);
        ageLabel =  findViewById(R.id.age_textView);
        pseudoLabel = findViewById(R.id.pseudo_textView);
        emailLabel = findViewById(R.id.email_textView);
        passwordLabel = findViewById(R.id.password_textView);
        checkPasswprdLabel = findViewById(R.id.confirmPassword_textView);

        makeLabelsInvisible();
    }

    void makeLabelsInvisible() {
        nameLabel.setVisibility(View.INVISIBLE);
        surnameLabel.setVisibility(View.INVISIBLE);
        ageLabel.setVisibility(View.INVISIBLE);
        pseudoLabel.setVisibility(View.INVISIBLE);
        emailLabel.setVisibility(View.INVISIBLE);
        passwordLabel.setVisibility(View.INVISIBLE);
        checkPasswprdLabel.setVisibility(View.INVISIBLE);
    }

    void setAllOnFocusListener(){
        name.setOnFocusChangeListener(this);
        surname.setOnFocusChangeListener(this);
        age.setOnFocusChangeListener(this);
        pseudo.setOnFocusChangeListener(this);
        email.setOnFocusChangeListener(this);
        password.setOnFocusChangeListener(this);
        checkPassword.setOnFocusChangeListener(this);
        findViewById(R.id.signup).setOnFocusChangeListener(this);
    }

    public void newSubscription(View view){
        if(name.toString() == null || surname.toString() == null || age.toString() == null || pseudo.toString() == null || email.toString() == null || password.toString() == null || checkPassword.toString() == null){
            Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs.", Toast.LENGTH_LONG).show();
        }
        else{
            String userName = name.toString();
            String userSurname = surname.toString();
            String userPseudo = pseudo.toString();
            int userAge = Integer.parseInt(age.toString());
            String userEmail = email.toString();
            String userPassword = password.toString();
            String userCheckPassword = checkPassword.toString();
            if(userPassword.equalsIgnoreCase(userCheckPassword)){
                User newUser = new User(userName, userSurname, userPseudo, userAge, userEmail, userPassword);
                myDb.insertDataUser(newUser);
            }
            else{
                Toast.makeText(getApplicationContext(), "Les mots de passes ne correspondent pas.", Toast.LENGTH_LONG);
            }

        }


    }

}
